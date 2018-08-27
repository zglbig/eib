package org.zgl.game.logic.room;

import org.zgl.ArrayUtils;
import org.zgl.datable.CardDataTable;
import org.zgl.dto.clinet.game1.Game1BettleWeatnUpdateDto;
import org.zgl.dto.clinet.game1.Game1BettleWeatnUpdateListDto;
import org.zgl.dto.clinet.game1.Game1SattleEndDto;
import org.zgl.dto.clinet.game1.Game1SattleLoseInfoDto;
import org.zgl.game.logic.card.CardManager;
import org.zgl.game.logic.operation.UserLog;
import org.zgl.game.logic.player.Player;
import org.zgl.game.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.game1.Game1PlayerOperationRoomNotify;
import org.zgl.type.CardEnum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GamblingParty {
    /**看牌后翻的倍数*/
    public static final int MULTIPLE = 2;
    /**有超过两个人准备之后最多五秒后房间开局*/
    private static final long OUT_TIME = 5000;
    /**结束之后延迟3秒才结束牌局*/
    private static final int END_TIME = 3000;
    /**每次考虑下注最长的时间，如果超过视为弃牌*/
    private static final long END_OUT_TIME = 15000;
    /**换牌是使用的集合*/
    private List<CardDataTable> exchangeCard;
    private final Room myRoom;
    private final PlayerSet playerSet;
    private final CardManager cardManager;
    private long startTime;
    private long endTime;
    /**上把赢钱的位置*/
    private int winPosition;
    /**当前下注房间位置*/
    private int nowBottomPos;
    /**当前筹码位置*/
    private int nowBottomChip;
    /**操作轮数*/
    private int operationCont;
    /**操作人数*/
    private int operationPlayerNum;
    /**压注状态，是否已经开启全压模式*/
    private boolean betAllState;
    /**上一个全压的玩家*/
    private Player betAllPlayer;
    /**全压的金币数量是多少*/
    private long betAllGold;
    /**房间结束标记*/
    private boolean roomEnd;
    private Player winPlayer;

    public GamblingParty(Room myRoom) {
        this.myRoom = myRoom;
        this.cardManager = myRoom.getCardManager();
        this.playerSet = myRoom.getPlayerSet();
        this.exchangeCard = new ArrayList<>();
    }
    public void setWinPosition(int winPosition) {
        this.winPosition = winPosition;
    }
    public int getNowBottomPos() {
        return nowBottomPos;
    }
    public void setNowBottomPos(int nowBottomPos) {
        this.nowBottomPos = nowBottomPos;
    }
    public void setEndTime(){
        endTime = System.currentTimeMillis();
    }
    public void setRoomEnd(){
        roomEnd = true;
    }
    /**
     * 开局一条狗，发牌全靠吹
     *
     * @return
     */
    private boolean startBattle() {
        List<Player> players = playerSet.getReadyPlayer(true);
        int chip = 100;
        Iterator<Player> iterator = players.iterator();
        List<Game1BettleWeatnUpdateDto> weatn = new ArrayList<>(players.size());
        while (iterator.hasNext()){
            Player p = iterator.next();
            if(p == null){
                iterator.remove();
                continue;
            }

            p.bet(chip);
            UserLog.userLog(myRoom.getScenesId(),p.getUid(),"not card",chip,"在"+myRoom.getRoomName()+"减底注","not card");
            myRoom.bet(p.getUid(),chip);
            weatn.add(new Game1BettleWeatnUpdateDto(p.getUid(),p.getGold()));
        }
        //发牌洗牌
        myRoom.setRoomStatus(2);
        //发牌洗牌
        shuffCard(players);
        //下个玩家开始计时
        long nextPositionUid = playerSet.getNextPositionUid(winPosition);
        nowBottomPos = playerSet.getPlayerByUid(nextPositionUid).getRoomPosition();
        setStartTime();
        //通知房间开局了
        Game1PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
        notify.battle(chip,myRoom.getAllGold(),nextPositionUid,new Game1BettleWeatnUpdateListDto(weatn));
        return false;
    }
    public int getNowBottomChip() {
        return nowBottomChip;
    }

    public void setNowBottomChip(int nowBottomChip) {
        this.nowBottomChip = nowBottomChip;
    }

    /**
     * 发牌洗牌
     */
    private void shuffCard(List<Player> players) {
        int playerNum = players.size();
        int scenesType = 1;
        if (myRoom.getScenesId() > 5) {
            scenesType = 2;
        }
        exchangeCard = cardManager.getCardDataTables();
        List<CardDataTable> cardDataTables = cardManager.shuff(playerNum * 3, scenesType);
        //移除已经发了的的牌
        shuffExchange(cardDataTables);
        int temp = 0;
        for (int i = 0; i < playerNum; i++) {
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
            players.get(i).setHandCard(handCard);
        }
    }


    /**
     * 发牌的时候要预留剩余的牌提供换牌是使用
     */
    private void shuffExchange(List<CardDataTable> c) {
        for (int i = 0; i < c.size(); i++) {
            CardDataTable cd = c.get(i);
            exchangeCard.remove(cd);
        }
    }

    public boolean isBetAllState() {
        return betAllState;
    }

    public void setBetAllState(boolean betAllState) {
        this.betAllState = betAllState;
    }
    /**
     * 超时下注视为弃牌
     *
     * @return
     */
    public boolean isBottomTimeOut() {
        return timeDifference() > END_OUT_TIME;
    }

    public void setWinPlayer(Player winPlayer) {
        this.winPlayer = winPlayer;
    }
    /**
     * 获取时间差
     *
     * @return
     */
    public long timeDifference() {
        return System.currentTimeMillis() - startTime;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime() {
        this.startTime = System.currentTimeMillis();
    }
    public void end() {
        setStartTime();
        exchangeCard.clear();
        operationCont = 0;
        operationPlayerNum = 0;
        betAllState = false;
        roomEnd = false;
        endTime = 0;
        betAllPlayer = null;
        betAllGold = 0;
        myRoom.setRoomStatus(1);
        winPosition = winPlayer.getRoomPosition();
        nowBottomChip = 0;

        List<Player> pay = playerSet.getAllPlayer();
        for (Player f : pay) {
            //清除比牌次数
            f.endOperation();
        }
    }
    public void timer() {
        if (myRoom.getRoomStatus() != 2) {
            if (playerSet.getChoiceNum(true) >= 2 && System.currentTimeMillis() - startTime >= OUT_TIME) {
                startBattle();
            }
            return;
        }
        //通知所有位置玩家超时 弃牌
        if (isBottomTimeOut()) {
            Player losePlayer = playerSet.getPlayerForPosition(nowBottomPos);

            losePlayer.setLose(true);
            losePlayer.setLoockCard(true);
            losePlayer.setHasPlay(false);
            losePlayer.getHandCard().setCompareResult(false);

            Game1PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
            notify.operationTimeOut(losePlayer.getUid());
            //牌局是否结束 没结束通知下一玩家操作
            if(playerSet.nowAllPayPlayer().size() == 1){
                Player winPlayer = myRoom.getPlayerSet().nowAllPayPlayer().get(0);
                //牌局结束
                setWinPlayer(winPlayer);
                //设置这把赢家的位置
                setWinPosition(winPlayer.getRoomPosition());
                setRoomEnd();
                setEndTime();
                //通知结束
            }else {
                //下一个位置的玩家
                long nextPlayerUid = playerSet.getNextPositionUid(losePlayer.getRoomPosition());
                Player nextPlayer = playerSet.getPlayerForPosition(nextPlayerUid);
                setNowBottomPos(nextPlayer.getRoomPosition());
                Game1PlayerOperationRoomNotify notify1 = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
                notify1.nextPlayerOperation(nextPlayerUid);
            }
            startTime = System.currentTimeMillis();
        }
        //延迟3秒结束
        if(roomEnd && System.currentTimeMillis() - endTime > END_TIME){
            myRoom.end();
        }
    }

    /**
     * 牌局结束
     */
    public void sattleEnd() {
        //哪个玩家赢了
        //赢了多少钱
        //赢得牌型
        //赢得牌id
        //哪些玩家输了
        //输了多少钱
        //输得牌型
        //输得牌id
        Game1SattleEndDto endDto = new Game1SattleEndDto();
        endDto.setWinPlayerUid(winPlayer.getUid());
        endDto.setWinCardId(ArrayUtils.arrayToList(winPlayer.getHandCard().getCardIds()));
        endDto.setWinCardType(winPlayer.getHandCard().getCardType());
        endDto.setWinGold(myRoom.getAllGold());
        endDto.setWinPlayerHoldGold(winPlayer.getGold());
        List<Game1SattleLoseInfoDto> loseInfos = new ArrayList<>();
        for(Map.Entry<Long,Long> entry : myRoom.getBetGoldMap().entrySet()){
            if(entry.getKey() != winPlayer.getUid()){
                Player losePlayer = playerSet.getPlayerByUid(entry.getKey());
                if(loseInfos != null && losePlayer.getHandCard() != null) {
                    Game1SattleLoseInfoDto loseInfo = new Game1SattleLoseInfoDto();
                    HandCard loseHandCard = losePlayer.getHandCard();
                    loseInfo.setLoseUid(entry.getKey());
                    loseInfo.setLoseGold(entry.getValue());
                    loseInfo.setLoseHoldGold(losePlayer.getGold());
                    loseInfo.setLoseCardIds(ArrayUtils.arrayToList(loseHandCard.getCardIds()));
                    loseInfo.setLoseCardType(loseHandCard.getCardType());
                    loseInfos.add(loseInfo);
                }
            }
        }
        endDto.setLoseInfo(loseInfos);
        //为赢钱得玩家加钱
        if(!betAllState) {
            //全压赢钱
            winPlayer.insertGold(myRoom.getAllGold());
            //日志
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            Integer[] ids = winPlayer.getHandCard().getCardIds();
            for(int i = 0;i<ids.length;i++){
                CardDataTable dataTable = CardDataTable.get(ids[i]);
                sb.append(dataTable.getDesc());
                if(i < ids.length - 1){
                    sb.append(",");
                }
            }
            sb.append("]");
            UserLog.userLog(myRoom.getScenesId(),winPlayer.getUid(),CardEnum.getEnum(winPlayer.getHandCard().getCardType()).desc(),myRoom.getAllGold(),"在"+myRoom.getRoomName()+"全压结算",sb.toString());
        }else {
            //正常赢钱
        }
        Game1PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
        //通知所有人牌局结束
        notify.sattleEnd(endDto);
        //广播全服消息

    }
    public boolean getBetAllState() {
        return betAllState;
    }

    public Player getBetAllPlayer() {
        return betAllPlayer;
    }

    public void setBetAllGold(long betAllGold) {
        this.betAllGold = betAllGold;
    }

    public long getBetAllGold() {
        return betAllGold;
    }

    public void setBetAllPlayer(Player betAllPlayer) {
        this.betAllPlayer = betAllPlayer;
    }
}
