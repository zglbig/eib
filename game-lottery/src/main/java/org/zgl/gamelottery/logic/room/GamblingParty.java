package org.zgl.gamelottery.logic.room;

import org.zgl.DateUtils;
import org.zgl.dto.clinet.lottery.LotteryHistoryDto;
import org.zgl.gamelottery.logic.card.CardType;
import org.zgl.gamelottery.logic.card.CardTypeManaer;
import org.zgl.gamelottery.logic.player.Player;
import org.zgl.gamelottery.logic.player.PlayerServerModel;
import org.zgl.gamelottery.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.lottery.GameLotteryOperationRoomNotify;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author 猪哥亮
 */
public class GamblingParty {
    /**
     * 目前一住20000
     */
    public static final int BET_COUNT = 20000;
    private Room room;
    private PlayerSet playerSet;
    private CardTypeManaer cardTypeManaer;
    private Map<Integer, LotteryBetModel> betMap;
    /**
     * 下注筹码数量
     */
    private Map<Long, Map<Integer, Integer>> betChip;
    private long allGold;
    /**
     * 当天系统财富收入
     */
    private long todayGetMoney;
    /**
     * 当天系统财富收入计时器
     */
    private int todayTimer;
    /**
     * 上期发放奖金
     */
    private long lastTimeGrantAward;
    private Queue<LotteryHistoryDto> historyDtos;
    /**
     * 当前场次
     */
    private long nowNum;
    private LotteryHistoryDto thisGamblingParCard;
    private Set<Long> nowBetPlayerNum;

    public GamblingParty(Room room) {
        this.room = room;
        this.playerSet = room.getPlayerSet();
        this.cardTypeManaer = room.getCardManager();
        this.room = room;
        this.playerSet = room.getPlayerSet();
        this.betMap = new ConcurrentHashMap<>();
        this.historyDtos = new ConcurrentLinkedQueue<>();
        this.nowBetPlayerNum = new HashSet<>();
        this.thisGamblingParCard = LotteryDealManager.getInstance().shuffleAndDeal();
        this.thisGamblingParCard.setNum(nowNum);
        this.betChip = new HashMap<>();
    }

    public void bet(int betPosition, long num, Player player, int betCount) {
        //开奖你让下注
        allGold += num;

        LotteryBetModel bet = betMap.get(betPosition);
        if (bet == null) {
            bet = new LotteryBetModel(betPosition);
            betMap.put(betPosition, bet);
        }
        bet.bet(player, betCount);
        playerSet.enter(player);
        nowBetPlayerNum.add(player.getUid());
        addBetChip(player.getUid(), betPosition, betCount);
        //通知有人下注
        GameLotteryOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameLotteryOperationRoomNotify.class, playerSet.getPlayerUidListByOnline());
        notify.betNotify(nowBetPlayerNum.size(), allGold);
    }

    public void addBetChip(long uid, int position, int betChipCount) {
        Map<Integer, Integer> uidMap = betChip.getOrDefault(uid, null);
        if (uidMap != null) {
            Integer i = uidMap.getOrDefault(position, null);
            int count = i == null ? 0 : i;
            if (i != null) {
                count += betChipCount;
            }
            uidMap.put(position, count);
        } else {
            uidMap = new HashMap<>();
            uidMap.put(position, betChipCount);
        }
        betChip.put(uid, uidMap);
    }
    /**单双最大注数*/
    private static final int SIZE_BET = 1000;
    public int canBet(long uid, int position) {
        Map<Integer, Integer> uidMap = betChip.getOrDefault(uid, null);
        int betCount = -1;
        if (uidMap != null) {
            Integer i = uidMap.getOrDefault(position, null);
            if (i != null) {
                if (position == 8 || position == 9) {
                    betCount = SIZE_BET - i;
                } else {
                    betCount = 100 - i;
                }
            } else {
                if (position == 8 || position == 9) {
                    betCount = SIZE_BET;
                } else {
                    betCount = 100;
                }
            }
        } else {
            if (position == 8 || position == 9) {
                betCount = SIZE_BET;
            } else {
                betCount = 100;
            }
        }
        return betCount;
    }

    public void settleAccount() {
        if (todayTimer != DateUtils.currentDay()) {
            todayTimer = DateUtils.currentDay();
            todayGetMoney = 0;
        }
        //通知所有人开牌了
        List<Player> ahPlayers = new ArrayList<>();
        lastTimeGrantAward = 0;
        Map<Long, Long> resultMap = new HashMap<>();
        for (LotteryBetModel b : betMap.values()) {
            ahPlayers.addAll(b.getAllPlayer());
            if (b.getPosition() == thisGamblingParCard.getResult()) {
                //赢
                lastTimeGrantAward += b.settleAccount(CardType.getType(thisGamblingParCard.getResult()).ahRate(), resultMap, thisGamblingParCard.getCardIds(),thisGamblingParCard.getResult());
            } else if (b.getPosition() == thisGamblingParCard.getOddEnven()) {
                //赢
                lastTimeGrantAward += b.settleAccount(38000F, resultMap, thisGamblingParCard.getCardIds(),thisGamblingParCard.getResult());
            } else {
                //玩家输了就把所有的钱放到血池中去
                lastTimeGrantAward -= b.getAllGold();
            }
        }
        todayGetMoney += lastTimeGrantAward;
        notifyResult();
        for (Map.Entry<Long, Long> e : resultMap.entrySet()) {
            long getGold = e.getValue();
            Player p = PlayerServerModel.getInstance().getPlayerByPlayerId(e.getKey());
            List<Long> uid = new ArrayList<>(1);
            uid.add(e.getKey());
            GameLotteryOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameLotteryOperationRoomNotify.class,uid);
            notify.settleAccount(getGold);
            StringBuilder sb = new StringBuilder();
            String userName = "大个子";
            if (getGold > Room.notifyLimit) {
                userName = p.getUserName();
                sb.append("恭喜玩家<<").
                        append(userName)
                        .append(">>在<<")
                        .append("天天乐")
                        .append(">>赢得<<")
                        .append(getGold)
                        .append("金币>>!");
//                    AwardNotify.offer(sb.toString());
            }
        }
        historyDtos.offer(thisGamblingParCard);
        if (historyDtos.size() > 14) {
            historyDtos.poll();
        }
    }

    public long getAllGold() {
        return allGold;
    }

    private void shuffle() {
        thisGamblingParCard = LotteryDealManager.getInstance().shuffleAndDeal();
        thisGamblingParCard.setNum(nowNum);
    }

    private void notifyResult() {
        LotteryHistoryDto dto = new LotteryHistoryDto();
        dto.setCardIds(thisGamblingParCard.getCardIds());
        if (lastTimeGrantAward > 0) {
            dto.setLastTimeGrantAward(lastTimeGrantAward);
        }
        dto.setNum(nowNum);
        dto.setOddEnven(thisGamblingParCard.getOddEnven());
        dto.setResult(thisGamblingParCard.getResult());
        GameLotteryOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameLotteryOperationRoomNotify.class,playerSet.getAllPlayerUid());
        notify.result(dto);
    }
    private int timer;

    public int getTimer() {
        return timer;
    }
    public void timer() {
        timer--;
        GameLotteryOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameLotteryOperationRoomNotify.class,playerSet.getAllPlayerUid());
        switch (timer) {
            //通知开始
            case 80:
                notify.start();
                room.setRoomStatus(1);
                break;
            case 30:
                shuffle();
                break;
            case 10:
                //不能下注了
                room.setRoomStatus(2);
                notify.stopBet();
                break;
            case 8:
                settleAccount();
                room.setRoomStatus(3);
                break;
            case 1:
                //通知所有服务器房间开始
                notify.start();
                //通知结束
                end();
                timer = 81;
                nowNum++;
                break;
            default:
                break;
        }
        if (timer <= 0) {
            timer = 80;
        }
    }
    public void end() {
        nowBetPlayerNum.clear();
        betMap.clear();
        playerSet.end();
        allGold = 0;
        betChip.clear();
    }
    public int nowBetPlayerNum(){
        return nowBetPlayerNum.size();
    }
    public long getLastTimeGrantAward(){
        return lastTimeGrantAward;
    }
    public List<LotteryHistoryDto> getHistoryDtos(){
        return new ArrayList<>(historyDtos);
    }
}
