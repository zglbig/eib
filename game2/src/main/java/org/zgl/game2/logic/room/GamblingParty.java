package org.zgl.game2.logic.room;
import org.zgl.ArrayUtils;
import org.zgl.DateUtils;
import org.zgl.RandomUtils;
import org.zgl.datable.CardDataTable;
import org.zgl.dto.clinet.commond.GameRankingDto;
import org.zgl.dto.clinet.commond.GameRankingListDto;
import org.zgl.game2.logic.card.CardManager;
import org.zgl.game2.logic.card.CardType;
import org.zgl.game2.logic.card.CardTypeManaer;
import org.zgl.game2.logic.operation.UserLog;
import org.zgl.game2.logic.player.Player;
import org.zgl.game2.socket.http.TcpProxyOutboundHandler;
import org.zgl.game2.utils.LoggerUtils;
import org.zgl.service.client.game2.Game2PlayerOperationRoomNotify;
import org.zgl.type.CardEnum;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

public class GamblingParty {
    private Room room;
    private PlayerSet playerSet;
    private CardManager cardManager;
    private CardTypeManaer cardTypeManaer;
    /**
     * 手牌
     */
    private Map<Integer, HandCard> handCardMap;
    /**
     * 下注位置
     */
    private Map<Integer, Game2Bet> bets;
    /**
     * 历史记录
     */
    private Queue<Game2HistoryDto> historyQueue;
    /**
     * 场次
     */
    private long battleCount;
    private long allGold;
    private long jackpot;
    private long bloodGroove;
    /**
     * 中奖记录
     */
    private Queue<Game2JackpotDto> jackpotDtos;
    /**
     * 万人场今天出金币情况
     */
    private AtomicLong todayMoney;
    /**
     * 万人场金币记时器，如果不是今天就清空并同步到大厅数据
     */
    private int todayTime;
    /**
     * 本局所有下注的钱
     */
    private long allBetGlod;
    /**游戏吃分得最低标准*/
    private long standard;

    public GamblingParty(Room room) {
        this.room = room;
        this.playerSet = room.getPlayerSet();
        this.cardManager = room.getCardManager();
        this.cardTypeManaer = CardTypeManaer.getInstance();
        this.handCardMap = new HashMap<>(5);
        this.bets = new ConcurrentHashMap<>(4);
        this.historyQueue = new ConcurrentLinkedQueue<>();
        this.todayMoney = new AtomicLong(0);
        this.jackpotDtos = new ConcurrentLinkedQueue<>();
        this.standard = 200;
    }
    /**
     * 下注
     *
     * @param betPosition 下注的位置 : 注 位置从1开始 1-4
     * @param num         下注的数量
     * @param player      下注的玩家
     */
    public void bet(int betPosition, long num, Player player) {
        Game2Bet b = bets.getOrDefault(betPosition, null);
        this.allBetGlod += num;
        if (b == null) {
            b = new Game2Bet();
            b.setPosition(betPosition);
            b.setPlayerSet(this.playerSet);
            this.bets.put(betPosition, b);
        }
        b.bet(player, num);
        Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,this.playerSet.getAllPlayerUid());
        notify.playerBetUpdateRoomWeath(player.getUid(),player.getGold(),num,betPosition);
    }
    private boolean startBattle() {
        room.setRoomStatus(2);
        int[] result = {3,3,3,3,3};
        if(bets.size()>0){
            result = getLowerList();
            shuffCard(result);
        }else {
            shuffCard();
        }
        Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
        notify.start1();
        return false;
    }
    public void shuffCard(int[] cardType) {
        if (cardType.length != 5) {
            shuffCard();
        }
        try {
            HandCard bankerHandCard = null;
            Map<Integer, CardDataTable> cards = cardTypeManaer.getAllCardIdMap();
            List<CardDataTable[]> list = new ArrayList<>(5);
            for (int i : cardType) {
                CardDataTable[] cs = cardTypeManaer.getCards(cards, i);
                list.add(cs);
            }
            if (list.size() != 5) {
                throw new RuntimeException("牌型个数不足");
            }
            for (int i = 0; i < list.size(); i++) {
                CardDataTable[] cds = list.get(i);
                Integer[] cardIds = new Integer[3];
                Integer[] cardFaces = new Integer[3];
                if (cds.length != 3) {
                    throw new RuntimeException("牌的个数不足");
                }
                for (int j = 0; j < cds.length; j++) {
                    cardIds[j] = cds[j].getId();
                    cardFaces[j] = cds[j].getFace();
                }
                HandCard handCard = new HandCard();
                handCard.setCardIds(cardIds);
                handCard.setCardFaces(cardFaces);
                if (bankerHandCard == null) {
                    //庄家手牌
                    bankerHandCard = handCard;
                }
                //整理牌型
                cardManager.getCardType(handCard);
                if (i != 0) {
                    //和庄家比输赢了
                    cardManager.compareCard(handCard, bankerHandCard);
                }
                handCard.setPosition(i);
                handCardMap.putIfAbsent(i, handCard);
                if (handCard.getCardType() == 0) {
                    LoggerUtils.getLogicLog().error(handCard.toString());
                }
            }
        } catch (Exception e) {
            shuffCard();
        }
    }
    /**
     * 随机发牌洗牌比牌
     */
    private void shuffCard() {
        List<CardDataTable> cardDataTables = cardTypeManaer.shuff(15, 1);
        HandCard bankerHandCard = null;
        int temp = 0;
        for (int i = 0; i < 5; i++) {
            Integer[] cardIds = new Integer[3];
            Integer[] cardFaces = new Integer[3];
            HandCard handCard = new HandCard();
            for (int j = 0; j < 3; j++) {
                cardIds[j] = cardDataTables.get(temp).getId();
                cardFaces[j] = cardDataTables.get(temp).getFace();
                temp++;
            }
            handCard.setCardIds(cardIds);
            handCard.setCardFaces(cardFaces);
            if (bankerHandCard == null) {
                //庄家手牌
                bankerHandCard = handCard;
            }
            //整理牌型
            cardManager.getCardType(handCard);
            if (i != 0) {
                //和庄家比输赢了
                cardManager.compareCard(handCard, bankerHandCard);
            }
            handCard.setPosition(i);
            handCardMap.putIfAbsent(i, handCard);
            if (handCard.getCardType() == 0) {
                LoggerUtils.getLogicLog().error(handCard.toString());
            }
        }
    }
    public List<Game2JackpotDto> getJackpotListInfo(){
        return new ArrayList<>(jackpotDtos);
    }
    private int[] getLowerList() {
        List<Integer> list = cheatCalculate(standard);
        return SittleAccountCardType.OneLowe(list);
    }
    private List<Integer> cheatCalculate(long standard) {
        //钱堆大到小排序 分配谁输谁赢
        Game2Bet[] betArr = ArrayUtils.arrForList(new ArrayList<>(bets.values()), Game2Bet.class);
        int j, k;
        int flag = betArr.length;
        while (flag > 0) {
            k = flag;
            flag = 0;
            for (j = 1; j < k; j++) {
                Game2Bet h1 = betArr[j - 1];
                Game2Bet h2 = betArr[j];
                if (h1.allMoney() > h2.allMoney()) {
                    Game2Bet temp = h1;
                    betArr[j - 1] = h2;
                    betArr[j] = temp;
                    flag = j;
                }
            }
        }
        return allocationCardType(betArr, standard);
    }
    private List<Integer> allocationCardType(Game2Bet[] betArr, long standard) {
        int length = betArr.length;
        List<Integer> positionList = new ArrayList<>();
        switch (length) {
            case 1:
                if (todayMoney.get() < standard) {
                    positionList.add(betArr[0].getPosition());
                } else {
                    int random = RandomUtils.getRandom(1, 3);
                    if (random < 2) {
                        positionList.add(betArr[0].getPosition());
                    }
                }
                break;
            case 2:
                if (todayMoney.get() < standard) {
                    int y = RandomUtils.getRandom(1, 2);
                    if (y == 1) {
                        setResult(positionList, betArr, 1);
                    } else {
                        setResult(positionList, betArr, -1);
                    }
                } else {
                    int random = RandomUtils.getRandom(1, 3);
                    if (random == 1) {
                        setResult(positionList, betArr, 0);
                    } else if (random == 2) {
                        setResult(positionList, betArr, 1);
                    } else {
                        int x = RandomUtils.getRandom(1, 2);
                        if (x == 1) {
                            //全输
                            setResult(positionList, betArr, -1);
                        }
                        //否则全赢
                    }
                }
                break;
            case 3:
                if (todayMoney.get() > standard) {
                    int randomx = RandomUtils.getRandom(1, 2);
                    if (randomx == 2) {
                        //通赔
                        break;
                    }
                }
                int randomResult = RandomUtils.getRandom(1, 3);
                if (randomResult == 1) {
                    //杀一个 杀两个 杀三个 通赔
                    if (todayMoney.get() < standard) {
                        setResult(positionList, betArr, 1);
                        setResult(positionList, betArr, 2);
                    } else {
                        //随机杀一家
                        int x = RandomUtils.getRandom(0, 2);
                        for (int i = 0; i < betArr.length; i++) {
                            if (i != x) {
                                setResult(positionList, betArr, i);
                            }
                        }
                    }
                } else if (randomResult == 2) {
                    //杀两个
                    if (todayMoney.get() < standard) {
                        //杀最大的两个
                        setResult(positionList, betArr, 2);
                    } else {
                        //随机赢一家
                        int x = RandomUtils.getRandom(0, 2);
                        for (int i = 0; i < betArr.length; i++) {
                            if (i == x) {
                                setResult(positionList, betArr, i);
                            }
                        }
                    }
                } else if (randomResult == 3) {
                    //杀三个
                    setResult(positionList, betArr, -1);
                }
            case 4:
                if (todayMoney.get() > standard) {
                    int randomx = RandomUtils.getRandom(1, 4);
                    switch (randomx) {
                        case 1:
                            //1家赢
                            int randomx1 = RandomUtils.getRandom(0, 3);
                            for (int i = 0; i < betArr.length; i++) {
                                if (i != randomx1) {
                                    positionList.add(betArr[i].getPosition());
                                }
                            }
                            break;
                        case 2:
                            //随机2家输 两家赢
                            Integer[] randomx2 = RandomUtils.randomNotRepeat(0, 3, 2);
                            positionList.add(betArr[randomx2[0]].getPosition());
                            positionList.add(betArr[randomx2[1]].getPosition());
                            break;
                        case 3:
                            //随机3家赢
                            int randomx3 = RandomUtils.getRandom(0, 3);
                            for (int i = 0; i < betArr.length; i++) {
                                if (i == randomx3) {
                                    positionList.add(betArr[i].getPosition());
                                }
                            }
                            break;
                        case 4:
                            break;
                        default:
                            break;
                    }
                } else {
                    int randomx = RandomUtils.getRandom(1, 4);
                    switch (randomx) {
                        case 1:
                            //杀最大家
                            positionList.add(betArr[0].getPosition());
                            break;
                        case 2:
                            //杀最大两家
                            positionList.add(betArr[0].getPosition());
                            positionList.add(betArr[1].getPosition());
                            break;
                        case 3:
                            //杀最大三家
                            positionList.add(betArr[0].getPosition());
                            positionList.add(betArr[1].getPosition());
                            positionList.add(betArr[2].getPosition());
                            break;
                        case 4:
                            //通杀
                            setResult(positionList, betArr, -1);
                            break;
                        default:
                            break;
                    }
                }
                break;
            default:
                break;
        }
        return positionList;
    }
    private void setResult(List<Integer> list, Game2Bet[] b, int winPos) {
        for (int i = 0; i < b.length; i++) {
            if (i != winPos) {
                list.add(b[i].getPosition());
            }
        }
    }
    /**
     * 通知玩家手牌
     */
    public void notifyHandCard() {
        //通知手牌来了嗨
        List<Game2CardDto> cardsDtos = new ArrayList<>(5);
        for (Map.Entry<Integer, HandCard> e : handCardMap.entrySet()) {
            Game2CardDto dto = new Game2CardDto();
            HandCard h = e.getValue();
            dto.setResult(h.isCompareResult());
            dto.setCardType(h.getCardType());
            dto.setPosition(e.getKey());
            dto.setCardIds(ArrayUtils.arrayToList(h.getCardIds()));
            cardsDtos.add(dto);
        }
        room.setRoomStatus(1);
        Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
        notify.showCards(new Game2CardListDto(cardsDtos));
    }

    private void settleAccounts() {
        if (todayTime != DateUtils.currentDay()) {
            todayTime = DateUtils.currentDay();
            todayMoney.set(0);
            jackpot = 0;
            bloodGroove = 0;
        }
        List<Boolean> historyResult = new ArrayList<>(4);
        List<Game2JackpotDto> j = new ArrayList<>();
        //获取本局赢钱排行
        Map<Long,GameRankingDto> ranking = new HashMap<>();
        for (Map.Entry<Integer, HandCard> e : handCardMap.entrySet()) {
            if (e.getKey() == 0) {
                continue;
            }
            int position = e.getKey();
            HandCard card = e.getValue();
            historyResult.add(card.isCompareResult());
            Game2Bet b = bets.getOrDefault(position, null);
            if (b == null) {
                continue;
            }
            if (card.isCompareResult()) {
                if (card.getCardType() == CardType.AAA.id()
                        || card.getCardType() == CardType.LEOPARD.id()
                        || card.getCardType() == CardType.STRAIGHT_FLUSH.id()) {
                    j.addAll(b.jackpot(CardType.getType(card.getCardType()), jackpot));
                    //分奖池然后减掉分的钱
                    jackpot = b.jackpot();
                    if (jackpot < 0) {
                        jackpot = 0;
                    }
                }
            }
            long m = 0;
            if (card.isCompareResult()) {
                //赢位置结算
                m = b.settleAccounts(card.getCardType(),card.getCardIds(),ranking);
            } else if (!card.isCompareResult()) {
                //输位置结算
                m = b.allMoney();
//                m = b.settleAccounts(card.getCardType(),card.getCardIds(),ranking);
            }
            jackpot += Math.abs(m) * ProcedureType.LOSE.id();
            bloodGroove += Math.abs(m) * 0.04;
            m -= m * ProcedureType.WIN.id();
            b.clear();
            allGold += m;
            //奖池历史记录
            int count = j.size() > 10 ? 10 : j.size();
            for (int i = 0; i < count; i++) {
                jackpotDtos.offer(j.get(i));
            }
            if (jackpotDtos.size() > 10) {
                int size = jackpotDtos.size() - 10;
                for (int i = 0; i < size; i++) {
                    jackpotDtos.poll();
                }
            }
        }
        bankerSettle();
        //排行
        List<GameRankingDto> list = new ArrayList<>(ranking.values());
        Collections.sort(list);
        int size = list.size() > 3 ? 3 :list.size();
        list = list.subList(0,size);
        Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
        notify.ranking(new GameRankingListDto(list));
        //设置房间状态
        room.setRoomStatus(1);
        battleCount++;
        //历史记录
        Game2HistoryDto history = new Game2HistoryDto(battleCount, historyResult.get(0), historyResult.get(1), historyResult.get(2), historyResult.get(3));
        historyQueue.offer(history);
        if (historyQueue.size() >= 12) {
            historyQueue.poll();
        }
    }
    private void bankerSettle(){
        //结算庄家
        Player banker = playerSet.getBankerPlayer();
        long alm = allGold;
        if (banker != null) {
            banker.insertGold(alm);
            if (banker.getGold() < 0) {
                banker.setGold(0);
            }
            alm = banker.getGold();
            //日志
            HandCard card1 = handCardMap.get(0);
            UserLog.userLog(room.getScenesId(),banker.getUid(),CardEnum.getEnum(card1.getCardType()).desc(),alm,"在"+room.getRoomName()+"坐庄结算",cardDesc(card1.getCardIds()));
            //有庄家 通知所有人庄家财富变了
        } else {
            todayMoney.addAndGet(alm);
        }
        Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
        notify.bankerSettleAccounts(alm,allGold,jackpot);
    }
    private String cardDesc(Integer[] ids){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0;i<ids.length;i++){
            CardDataTable dataTable = CardDataTable.get(ids[i]);
            sb.append(dataTable.getDesc());
            if(i < ids.length-1){
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    public List<Game2HistoryDto> historyDtos() {
        return new ArrayList<>(historyQueue);
    }
    public long getAllBetGlod() {
        return allBetGlod;
    }
    public void end(){
        allBetGlod = 0;
        bets.clear();
    }
    private int roomTimer;
    public int getResidueTime(){
        return roomTimer;
    }
    /**
     * 房间定时器
     */
    public void timer() {
        try {
            Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
            //开始，设置房间状态为不能下注，通知发牌
            switch (roomTimer) {
                case 1:
                    room.setRoomStatus(1);
                    notify.start0();
                    break;
                case 25:
                    notify.stopBet();
                    break;
                case 29:
                    //洗牌发牌
                    startBattle();
                    break;
                case 33:
                    notifyHandCard();
                    settleAccounts();
                    break;
                case 39:
                    notify.end();
                    break;
                case 42:
                    end();
                    playerSet.addBankerCount();
                    roomTimer = 0;
                    break;
                default:
                    break;
            }
            if (roomTimer >= 42) {
                roomTimer = 0;
            }
            roomTimer++;
        } catch (Exception e) {
            LoggerUtils.getLogicLog().info(roomTimer + "", e);
        }
    }
}
