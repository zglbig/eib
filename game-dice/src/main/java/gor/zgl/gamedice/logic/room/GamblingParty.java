package gor.zgl.gamedice.logic.room;

import gor.zgl.gamedice.logic.operation.UserLog;
import gor.zgl.gamedice.logic.player.Player;
import gor.zgl.gamedice.socket.http.TcpProxyOutboundHandler;
import org.zgl.DateUtils;
import org.zgl.datable.DiceDataTable;
import org.zgl.dto.clinet.dice.DiceCountDto;
import org.zgl.dto.clinet.dice.DiceSettleRankingDto;
import org.zgl.dto.clinet.dice.DiceSettleRankingListDto;
import org.zgl.service.client.dice.GameDicePlayerOperationRoomNotify;
import org.zgl.weightRandom.WeightRandom;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public class GamblingParty {
    public static int betLimit = 50000000;
    /**下注位置*/
    private Map<Integer, DiceBetModel> bets;
    /**下注人数*/
    private Set<Long> betPlayerNum;
    /**历史记录*/
    private Queue<DiceCountDto> historyQueue;
    /**是否是围骰*/
    private RoundDiceType isRound;
    /**是否到时间出围骰了 20-30之间出一把*/
    private int isKaiWei;
    private DiceCountDto diceCountDto;
    /**当局点数*/
    private DiceCountType thisTimeCount;
    /**场次*/
    private long battleCount = 0;
    private long systemWeath;
    private int todayTime;
    private long allGold;
    private PlayerSet playerSet;
    private Room room;
    public GamblingParty(Room room) {
        this.room = room;
        this.playerSet = room.getPlayerSet();
        this.bets = new ConcurrentHashMap<>();
        this.historyQueue = new ConcurrentLinkedQueue<>();
        this.betPlayerNum = new HashSet<>();
    }
    public void bet(Player player, long num, int position) {
        long uid = player.getUid();
        betPlayerNum.add(uid);
        allGold += num;
        player.insertNowBetCount(num);
        DiceBetModel d = bets.getOrDefault(position, null);
        if (d == null) {
            d = new DiceBetModel();
            d.setPosition(position);
            d.setPlayerSet(this.playerSet);
            bets.put(position, d);
        }
        d.bet(player, num);
        //通知房间所有人
    }
    public int getBetPlayerNum(){
        return betPlayerNum.size();
    }
    public long getAllGold(){
        return allGold;
    }
    public long clearBet(Player player){
        long money = 0;
        for (DiceBetModel db : bets.values()) {
            money += db.clearPlayerBet(player);
        }
        allGold -= money;
        betPlayerNum.remove(player.getUid());
        player.clearNowBetCount();
        //通知有人清除下注
        GameDicePlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameDicePlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
        notify.playerClearBet(player.getUid(),player.getGold(),allGold,betPlayerNum.size());
        if(money > 0) {
            player.insertGold(money);
            //日志
            UserLog.userLog(player.getUid(),"not card",money,"在骰子场清除下注","not card");
        }
        return money;
    }
    public List<DiceCountDto> getHistory(){
        return new ArrayList<>(historyQueue);
    }

    private void start() {
        //权重随机生成点数
        int id = WeightRandom.awardPosition(DiceManager.getInstance().getDiceDate());
        DiceDataTable diceDataTable = DiceDataTable.get(id);
        resultCount(diceDataTable);
    }
    private void resultCount(DiceDataTable diceDataTable) {
        DiceCountDto splitCount = diceDataTable.getSplitCount();
        int one = splitCount.getOne();
        int two = splitCount.getTwo();
        //围骰
        if (one == two) {
            if (one == 1 || one == 6) {
                isRound = RoundDiceType.get(16);
            } else {
                isRound = RoundDiceType.get(diceDataTable.getCount());
            }
            isKaiWei = 0;
        }
        diceCountDto = new DiceCountDto(one, two, battleCount++);
        thisTimeCount = DiceCountType.getDiceType(diceDataTable.getCount());
    }
    private void settleAccounts() {
        if (todayTime != DateUtils.currentDay()) {
            systemWeath = 0;
            todayTime = DateUtils.currentDay();
        }
        Map<Long, Long> resultMap = new HashMap<>();
        for (DiceBetModel db : bets.values()) {
            boolean isLose = false;
            if (db.getPosition() == thisTimeCount.getCount()) {
                //赢压中的所有点数
                allGold -= db.settleAccounts(thisTimeCount.getRate());
                isLose = true;
            }
            if (db.getPosition() == thisTimeCount.getSize() && isRound == RoundDiceType.NONE) {
                //赢大小 并且不是围 围不算大小
                allGold -= db.settleAccounts(1);
                isLose = true;
            }
            if (isRound != RoundDiceType.NONE) {
                //是围骰
                if (isRound.id() == db.getPosition()) {
                    //赢 围的点数
                    allGold -= db.settleAccounts(isRound.rate());
                    isLose = true;
                }
                if (db.getPosition() == 16) {
                    //所有围骰
                    allGold -= db.settleAccounts(5);
                    isLose = true;
                }
            }
            if (!isLose) {
                db.loseSettle();
            }
            settleResultMap(resultMap, db.getResultMap());
            db.clear();
        }
        systemWeath += allGold;
        isRound = RoundDiceType.NONE;
        //排行
        ArrayList<DiceSettleRankingDto> ll = new ArrayList<>();
        List<Player> allPlayer = playerSet.getAllPlayer();
        for (Player d : allPlayer) {
            if (resultMap.containsKey(d.getUid())) {
                Long money = resultMap.get(d.getUid());
                if (money > 0) {
                    ll.add(new DiceSettleRankingDto(d.getUid(),d.getUserName(),d.getGold(),money));
                }
                List<Long> uids = new ArrayList<>(1);
                uids.add(d.getUid());
                GameDicePlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameDicePlayerOperationRoomNotify.class,uids);
                notify.playerSettle(new DiceSettleRankingDto(d.getUid(),d.getUserName(),d.getGold(),money));
            }
        }

        Collections.sort(ll);
        int size = ll.size() >= 3 ? 3 : ll.size();
        //发送本局结束之后赢钱最多的三个人和四个位置的输赢情况
        GameDicePlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameDicePlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
        notify.settleAccountRanking(diceCountDto.getOne(), diceCountDto.getTwo(),new DiceSettleRankingListDto(ll.subList(0,size)));
        //历史记录
        if (historyQueue.size() >= 10) {
            historyQueue.poll();
        }
        historyQueue.offer(diceCountDto);
        //今天骰子场赚的钱
    }
    private void settleResultMap(Map<Long, Long> src, Map<Long, Long> desc) {
        for (Map.Entry<Long, Long> e : desc.entrySet()) {
            long money = e.getValue();
            long uid = e.getKey();
            if (src.containsKey(uid)) {
                money += src.get(uid);
            }
            src.put(uid, money);
        }
    }
    private int residueTime;

    public int getResidueTime() {
        return residueTime;
    }

    public void timer() {
        //发送本局结束之后赢钱最多的三个人和四个位置的输赢情况
        GameDicePlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameDicePlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
        residueTime++;
        switch (residueTime) {
            case 1:
                room.setRoomStatus(1);
                notify.start();
                //通知开局
                break;
            case 25:
                room.setRoomStatus(2);
                notify.stopBet();
                //通知停止下注 要骰子
                break;
            case 28:
                start();
                settleAccounts();
                //通知结算
                room.setRoomStatus(2);
                break;
            case 35:
                room.setRoomStatus(2);
                notify.end();
                clear();
                //通知本局结束
                break;
            default:
                break;
        }
        if (residueTime > 35) {
            residueTime = 0;
        }
    }

    private void clear() {
        allGold = 0;
        betPlayerNum.clear();
        bets.clear();
        isRound = RoundDiceType.NONE;
    }
}
