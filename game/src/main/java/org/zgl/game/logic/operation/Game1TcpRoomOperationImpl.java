package org.zgl.game.logic.operation;

import org.springframework.stereotype.Component;
import org.zgl.ArrayUtils;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.commond.CardsDto;
import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.dto.clinet.commond.PbIntegerArr;
import org.zgl.dto.clinet.game1.Game1BetAllResponseDto;
import org.zgl.dto.clinet.game1.Game1PlayerRoomDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.game.logic.Operation;
import org.zgl.game.logic.card.CardManager;
import org.zgl.game.logic.player.Player;
import org.zgl.game.logic.player.PlayerServerModel;
import org.zgl.game.logic.room.*;
import org.zgl.game.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.game1.Game1PlayerOperationRoomNotify;
import org.zgl.service.server.game1.Game1TcpRoomOperation;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：这里应该实现动态代理接口
 */
@Component
@ClinetProxy
public class Game1TcpRoomOperationImpl extends Operation implements Game1TcpRoomOperation{
    @Override
    public boolean exitRoom(){
        GamblingParty gamblingParty = room.getGamblingParty();
        PlayerSet playerSet = room.getPlayerSet();
        Game1PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,playerSet.getAllPlayerUidAndNotUid(player.getUid()));
        if(room.getRoomStatus() == 2 && gamblingParty.getNowBottomPos() == player.getRoomPosition()){
            //如果当前房间正在游戏而且操作玩家是他 那么他走了就通知下一个
            long nextOperaiotnUid = playerSet.getNextPositionUid(player.getRoomPosition());
            notify.nextPlayerOperation(nextOperaiotnUid);
        }
        //通知所有人这个玩家走了
        notify.playerLoginOut(player.getUid());
        room.exit(player);
        PlayerServerModel.getInstance().removePlayer(player);
        return true;
    }
    @Override
    public boolean ready(){
        if(player.getGold() < 500){
            new AppGeneralError(AppErrorCode.GOLD_NOT_INSUFFICIENT,500,room.getRoomName());
        }
        if(room.getRoomStatus() == 2){
            new AppGeneralError(AppErrorCode.ROOM_IS_START_CAN_NOT_READY);
        }
        room.getPlayerSet().addPlayerChoice(player.getUid(),true);
        room.getGamblingParty().setStartTime();
        Game1PlayerOperationRoomNotify enterRoomNotify = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUidAndNotUid(player.getUid()));
        enterRoomNotify.ready(player.getUid());
        player.setHasPlay(true);
        player.setReady(true);
        return true;
    }
    @Override
    public CardsDto lookCard(){
        if(!player.isHasPlay()){
            new AppGeneralError(AppErrorCode.PLAYER_NOT_READY);
        }
        GamblingParty gamblingParty = room.getGamblingParty();
        if(gamblingParty.isBetAllState()){
            if(!gamblingParty.getBetAllPlayer().isLoockCard()){
                new AppGeneralError(AppErrorCode.BET_ALL_NOT_LOOCK_CARD);
            }
        }
        HandCard handCard = player.getHandCard();
        if(handCard == null) {
            new AppGeneralError(AppErrorCode.SERVER_ERROR);
        }
        CardsDto cardsDto = new CardsDto();
        cardsDto.setCardIds(ArrayUtils.arrayToList(handCard.getCardIds()));
        cardsDto.setCardType(handCard.getCardType());
        player.setLoockCard(true);
        Game1PlayerOperationRoomNotify enterRoomNotify = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUidAndNotUid(player.getUid()));
        enterRoomNotify.hasPlayerLookCard(player.getUid());
        return cardsDto;
    }
    /**
     * 下注
     * @param chip
     * @return
     */
    @Override
    public long bet(int chip) {
        PlayerSet playerSet = room.getPlayerSet();
        GamblingParty gamblingParty = room.getGamblingParty();
        if(!player.isReady()){
            new AppGeneralError(AppErrorCode.PLAYER_NOT_READY);
        }
        if(chip < gamblingParty.getNowBottomChip()){
            new AppGeneralError(AppErrorCode.CHIP_POS_ERR);
        }
        //玩家已经失败
        if(!player.isLose()){
            new AppGeneralError(AppErrorCode.PLAYER_NOT_READY);
        }
        if(gamblingParty.isBottomTimeOut()){
            new AppGeneralError(AppErrorCode.BET_TIME_OUT);
        }
        if(gamblingParty.isBetAllState()){
            new AppGeneralError(AppErrorCode.HAS_PLAYER_BET_ALL);
        }
        if(gamblingParty.getNowBottomPos() != player.getRoomPosition()){
            new AppGeneralError(AppErrorCode.NOT_IS_PLAYER_OPERATION);
        }
        int scenesId = room.getScenesId();
        int[] bets = {100,200,300,400,500,600};
        if(chip > bets.length){
            new AppGeneralError(AppErrorCode.BET_CHIP_POSITION_ERROR);
        }
        long betCount = bets[chip];
        if(player.isLoockCard()){
            //看牌后翻倍
            betCount *= GamblingParty.MULTIPLE;
        }
        if(player.bet(betCount)){
            new AppGeneralError(AppErrorCode.BRT_GOLD_NOT_INSUFFICIENT);
        }
        //日志
        UserLog.userLog(room.getScenesId(),player.getUid(),"not card",betCount,"在"+room.getRoomName()+"下注","not card");
        gamblingParty.setStartTime();
        room.bet(player.getUid(),betCount);
        //通知房间财富变更
        long nextPlayerUid = playerSet.getNextPositionUid(player.getRoomPosition());
        Player nextOperationPlayer = playerSet.getPlayerByUid(nextPlayerUid);
        Game1PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
        notify.roomBetWeathUpate(player.getUid(),betCount,player.getGold(),nextPlayerUid,room.getAllGold());
        //设置下以操作位置
        gamblingParty.setNowBottomPos(nextOperationPlayer.getRoomId());
        //通知自己下注成功
        return player.getGold();
    }

    @Override
    public Game1BetAllResponseDto betAll() {
        GamblingParty gamblingParty = room.getGamblingParty();
        PlayerSet playerSet = room.getPlayerSet();
        if(!player.isHasPlay()){
            new AppGeneralError(AppErrorCode.PLAYER_NOT_READY);
        }
        if(gamblingParty.isBottomTimeOut()){
            new AppGeneralError(AppErrorCode.BET_TIME_OUT);
        }
        if(gamblingParty.getNowBottomPos() != player.getRoomPosition()){
            new AppGeneralError(AppErrorCode.NOT_IS_PLAYER_OPERATION);
        }
        List<Player> nowPlayList = playerSet.nowAllPayPlayer();
        if(nowPlayList.size() != 2){
            new AppGeneralError(AppErrorCode.PLAYER_EXCEED_BET_LIMIT);
        }
        long resultGold = -1;
        if (!gamblingParty.getBetAllState()) {
            for (int i = 0, j = nowPlayList.size(); i < j; i++) {
                long nowMoney = nowPlayList.get(i).getGold();
                if (resultGold != -1) {
                    resultGold = nowMoney < resultGold ? nowMoney : resultGold;
                } else {
                    resultGold = nowMoney;
                }
            }
        } else {
            resultGold = gamblingParty.getBetAllGold();
        }
        player.bet(resultGold);
        //日志
        UserLog.userLog(room.getScenesId(),player.getUid(),"not card",resultGold,"在"+room.getRoomName()+"全压","not card");
        //比较两人的金币 取出少的人的筹码数做全压的金币
        room.getGamblingParty().setStartTime();
        room.bet(player.getUid(),resultGold);
        //是否时最后一个点全压
        //不是就通知下一玩家
        long nextOperationUid = -1;
        if(gamblingParty.getBetAllState()){
            //这里已经时最后一个了处理比牌结算
            Player other = gamblingParty.getBetAllPlayer();
            HandCard selfCardIds = player.getHandCard();
            HandCard otherCardIds = other.getHandCard();
            //整理牌型
            CardManager.getInstance().getCardType(selfCardIds);
            CardManager.getInstance().getCardType(otherCardIds);
            CardManager.getInstance().compareCard(selfCardIds,otherCardIds);
            HandCard temp = null;
            long loseUid = 0;
            if(selfCardIds.isCompareResult()){
                other.setLose(true);
                other.setHasPlay(false);
                other.setLoockCard(true);
                temp = other.getHandCard();
                loseUid = other.getUid();
                //牌局结束
                room.getGamblingParty().setWinPlayer(player);
                //设置这把赢家的位置
                room.getGamblingParty().setWinPosition(player.getRoomPosition());
            }else {
                player.setLose(true);
                player.setHasPlay(false);
                player.setLoockCard(true);
                temp = player.getHandCard();
                loseUid = player.getUid();
                room.getGamblingParty().setWinPlayer(other);
                //设置这把赢家的位置
                room.getGamblingParty().setWinPosition(other.getRoomPosition());
            }
            Game1PlayerOperationRoomNotify notifyOne = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUid());
            notifyOne.compare(player.getUid(),other.getUid(),loseUid,temp.getCardType(),new PbIntegerArr(ArrayUtils.arrayToList(temp.getCardIds())));
            room.getGamblingParty().setRoomEnd();
            room.getGamblingParty().setEndTime();
        }else {
            //这里是第一个点全压的通知全部人和下一位置的人全压或弃牌
            nextOperationUid = playerSet.getNextPositionUid(player.getRoomPosition());
            int nextPosition = playerSet.getPlayerPositionByUid(nextOperationUid);
            //通知房间人全压
            Game1PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,playerSet.getAllPlayerUidAndNotUid(player.getUid()));
            notify.betAll(player.getUid(),nextOperationUid,resultGold,player.getGold(),room.getAllGold());
            //设置房间状态
            gamblingParty.setNowBottomPos(nextPosition);
            gamblingParty.setBetAllState(true);
            gamblingParty.setBetAllPlayer(player);
            gamblingParty.setBetAllGold(resultGold);
            gamblingParty.setStartTime();
        }
        return new Game1BetAllResponseDto(player.getGold(),resultGold,room.getAllGold(),nextOperationUid);
    }

    @Override
    public long betAdd(int chipPosition) {
        bet(chipPosition);
        room.getGamblingParty().setNowBottomChip(chipPosition);
        Game1PlayerOperationRoomNotify notifyOne = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUidAndNotUid(player.getUid()));
        notifyOne.betAdd(player.getUid(),chipPosition);
        return player.getGold();
    }

    @Override
    public Game1PlayerRoomDto exchangeRoom() {
        GamblingParty gamblingParty = room.getGamblingParty();
        PlayerSet playerSet = room.getPlayerSet();
        Game1PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,playerSet.getAllPlayerUidAndNotUid(player.getUid()));
        if(room.getRoomStatus() == 2 && gamblingParty.getNowBottomPos() == player.getRoomPosition()){
            //如果当前房间正在游戏而且操作玩家是他 那么他走了就通知下一个
            long nextOperaiotnUid = playerSet.getNextPositionUid(player.getRoomPosition());
            notify.nextPlayerOperation(nextOperaiotnUid);
        }
        //通知所有人这个玩家走了
        notify.playerLoginOut(player.getUid());
        room.exit(player);
        //进入新房间
        Room newRoom = RoomServerModel.getInstance().getRoom(player.getScenesId());
        if(newRoom == null){
            newRoom = RoomServerModel.getInstance().createRoom(player.getUid(),player.getScenesId());
        }

        return newRoom.enter(player);
    }

    @Override
    public boolean compareCard(long targetUid) {
        Player other = room.getPlayerSet().getPlayerForPosition(targetUid);
        if(other == null) {
            //服务器异常，没这玩家
            new AppGeneralError(AppErrorCode.NOT_THIS_UID_PLAYER,targetUid);
        }
        if(!other.isHasPlay()){
            new AppGeneralError(AppErrorCode.PLAYER_IS_LOSE,other.getUserName());
        }
        if(room.getGamblingParty().getNowBottomPos() != player.getRoomPosition()) {
            //不是该玩家操作
            new AppGeneralError(AppErrorCode.NOT_IS_PLAYER_OPERATION);
        }
        HandCard selfCardIds = player.getHandCard();
        HandCard otherCardIds = other.getHandCard();
        //整理牌型
        CardManager.getInstance().getCardType(selfCardIds);
        CardManager.getInstance().getCardType(otherCardIds);
        CardManager.getInstance().compareCard(selfCardIds,otherCardIds);
        HandCard temp = null;
        long loseUid = 0;
        if(selfCardIds.isCompareResult()){
            other.setLose(true);
            other.setHasPlay(false);
            other.setLoockCard(true);
            temp = other.getHandCard();
            loseUid = targetUid;
        }else {
            player.setLose(true);
            player.setHasPlay(false);
            player.setLoockCard(true);
            temp = player.getHandCard();
            loseUid = player.getUid();
        }
        Game1PlayerOperationRoomNotify notifyOne = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUid());
        notifyOne.compare(player.getUid(),targetUid,loseUid,temp.getCardType(),new PbIntegerArr(ArrayUtils.arrayToList(temp.getCardIds())));

        isEnd();
        return true;
    }

    @Override
    public boolean giveUpCard() {
        if(!player.isHasPlay()){
            new AppGeneralError(AppErrorCode.PLAYER_NOT_READY);
        }
        player.getHandCard().setCompareResult(false);
        if(room.getGamblingParty().getBetAllPlayer() != null){
            if(room.getGamblingParty().getBetAllPlayer().getUid() == player.getUid()){
                new AppGeneralError(AppErrorCode.LAST_ONE_PLAYER_CAN_NOT_GIVE_UP);
            }
        }
        player.setHasPlay(false);
        player.setLose(true);
        isEnd();
        return true;
    }

    @Override
    public boolean sendChatMsg(int msgType, String msg) {
        ChatDto dto = new ChatDto();
        dto.setMsg(msg);
        dto.setMsgType(msgType);
        //1男 2女
        dto.setUid(player.getUid());
        dto.setHeadIcon(player.getHeadImg());
        dto.setSex(player.getSex());
        dto.setUsername(player.getUserName());
        dto.setVipLv(player.getVipLv());
        Game1PlayerOperationRoomNotify notifyOne = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUidAndNotUid(player.getUid()));
        notifyOne.receiveChatMsg(dto);
        return true;
    }

    @Override
    public boolean kicking(long targetUid) {
        Player target = PlayerServerModel.getInstance().getPlayerByPlayerId(targetUid);
        if(target == null){
            new AppGeneralError(AppErrorCode.NOT_PLAYER);
        }
        if(room.getRoomStatus() == 2){
            //房间已经开局
            new AppGeneralError(AppErrorCode.ROOM_IS_START_NOT_KICKING_PLAYER);
        }
        if(player.getVipLv() < target.getVipLv()){
            new AppGeneralError(AppErrorCode.TARGET_VIP_HIGTH);
        }
        if(player.getKickingCardNum() <= 0){
            new AppGeneralError(AppErrorCode.KICKING_CARD_INSUFFICIENT);
        }
        Game1PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUid());
        notify.kicking(player.getUserName(),targetUid,target.getUserName());
        room.exit(target);
        PlayerServerModel.getInstance().removePlayer(target);
        return false;
    }

    private void isEnd(){
        int nowPlaySize = room.getPlayerSet().nowAllPayPlayer().size();
        if(nowPlaySize == 1){
            Player winPlayer = room.getPlayerSet().nowAllPayPlayer().get(0);
            //牌局结束
            room.getGamblingParty().setWinPlayer(winPlayer);
            //设置这把赢家的位置
            room.getGamblingParty().setWinPosition(winPlayer.getRoomPosition());
            room.getGamblingParty().setRoomEnd();
            room.getGamblingParty().setEndTime();
        }else {
            if(room.getGamblingParty().getNowBottomPos() == player.getRoomPosition()) {
                //下一个位置的玩家
                long nextUid = room.getPlayerSet().getNextPositionUid(player.getRoomPosition());
                Player nextPlayer = room.getPlayerSet().getPlayerForPosition(nextUid);
                room.getGamblingParty().setNowBottomPos(nextPlayer.getRoomPosition());
                //通知下一个位置玩家做动作
                room.getGamblingParty().setStartTime();
                Game1PlayerOperationRoomNotify notify1 = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUid());
                notify1.hasPlayerGiveUp(player.getUid(),nextUid);
            }
        }
    }

}
