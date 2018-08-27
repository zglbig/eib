package org.zgl.game2.logic.operation;
import org.springframework.stereotype.Component;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.game2.logic.Operation;
import org.zgl.game2.logic.player.Player;
import org.zgl.game2.logic.player.PlayerServerModel;
import org.zgl.game2.logic.room.PlayerSet;
import org.zgl.game2.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.game2.Game2PlayerOperationRoomNotify;
import org.zgl.service.server.game2.Game2TcpRoomOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：这里应该实现动态代理接口
 */
@Component
@ClinetProxy
public class Game2TcpRoomOperationImpl extends Operation implements Game2TcpRoomOperation {
    @Override
    public boolean exitRoom() {
        return room.exit(player);
    }
    @Override
    public Game2BetUpdateWeathDto bet(int chip, int position) {
        if(player.getGold() < 3000){
            new AppGeneralError(AppErrorCode.GOLD_NOT_INSUFFICIENT);
        }
        int[] CHIP = {100,200,300,400,500};
        if(chip > CHIP.length || chip < 0) {
            new AppGeneralError(AppErrorCode.CHIP_POS_ERR);
        }
        Player banker = room.getPlayerSet().getBankerPlayer();
        if(banker != null && banker.getUid() == player.getUid()) {
            //庄家不能下注
            new AppGeneralError(AppErrorCode.BANKER_CAN_NOT_BET);
        }
        if(room.getRoomStatus() != 2){
            //房间已经停止下注等待下一句开始吧
            new AppGeneralError(AppErrorCode.BET_TIME_OUT);
        }
        int goldNum = CHIP[chip];
        //检查庄家是否有足够的钱赔
        if(banker != null && (room.getGamblingParty().getAllBetGlod()+goldNum)*3 >= banker.getGold()){
            //不能下注超过庄家剩余的钱的5倍
            new AppGeneralError(AppErrorCode.BET_LIMIT);
        }
        if(!player.bet(goldNum)) {
            //金币不足
            new AppGeneralError(AppErrorCode.GOLD_NOT_INSUFFICIENT);
        }
        //日志
        UserLog.userLog(room.getScenesId(),player.getUid(),"not card",goldNum,"在"+room.getRoomName()+position+"位置下注","not card");
        room.getGamblingParty().bet(position,goldNum,player);
        return new Game2BetUpdateWeathDto(player.getGold(),goldNum,position);
    }

    @Override
    public int bankerUp() {
        //上装金币不能小于3百亿
        if(player.getGold() < 100000000) {
            new AppGeneralError(AppErrorCode.GOLD_NOT,100000000);
        }
        if(!room.getPlayerSet().bankerUp(player)) {
            new AppGeneralError(AppErrorCode.NOW_IN_BANKER_LIST);
        }
        return room.getPlayerSet().getBankerList().size();
    }

    @Override
    public boolean bankerDown() {
        if(room.getPlayerSet().getBankerPlayer().getUid() != player.getUid()){
            new AppGeneralError(AppErrorCode.PLAYER_NOT_IS_BANKER_NOR_DOWN);
        }
        room.getPlayerSet().bankerDownTag(player.getUid());
        return false;
    }

    @Override
    public Game2PositionPlayerInfoListDto bankerList() {
        List<Player> toPlayers = room.getPlayerSet().getBankerList();
        List<Game2PositionPlayerInfoDto> infoDtos = new ArrayList<>(toPlayers.size());
        PlayerSet playerSet = room.getPlayerSet();
        for(Player t : toPlayers){
            infoDtos.add(playerSet.getDto(t));
        }
        return new Game2PositionPlayerInfoListDto(infoDtos);
    }

    @Override
    public boolean positionDown() {
        if(player.getRoomPosition() == PlayerSet.DEFAULT_POS) {
            new AppGeneralError(AppErrorCode.PLAYER_NOT_POSITION);
        }
        room.getPlayerSet().downPos(player);
        return false;
    }

    @Override
    public boolean positionUp(int position) {
        if(player.getRoomPosition()!= PlayerSet.DEFAULT_POS) {
            //当前已经有位置了
            new AppGeneralError(AppErrorCode.TO_ROOM_HASH_POSITION);
        }
        if(room.getPlayerSet().getNowPositionNum() > PlayerSet.MAX_POSITION) {
            new AppGeneralError(AppErrorCode.POSITION_NOT);
        }
        if(!room.getPlayerSet().upPosition(position,player)) {
            new AppGeneralError(AppErrorCode.POSITION_NOT);
        }
        return true;
    }

    @Override
    public Game2HistoryListDto history() {
        List<Game2HistoryDto> dtos = room.getGamblingParty().historyDtos();
        return new Game2HistoryListDto(dtos);
    }

    @Override
    public Game2PositionPlayerInfoListDto playPlayerList() {
        PlayerSet playerSet = room.getPlayerSet();
        List<Player> toPlayerList = playerSet.getAllPlayer();
        List<Game2PositionPlayerInfoDto> baseInfoDtos = new ArrayList<>(toPlayerList.size());
        for(Player tp:toPlayerList){
            if(tp.getRoomPosition() == PlayerSet.DEFAULT_POS){
                baseInfoDtos.add(playerSet.getDto(tp));
            }
        }
        Collections.sort(baseInfoDtos);
        return new Game2PositionPlayerInfoListDto(baseInfoDtos);
    }

    @Override
    public Game2JackpotListDto jackpot() {
        List<Game2JackpotDto> jackpotDtos = room.getGamblingParty().getJackpotListInfo();
        return new Game2JackpotListDto(jackpotDtos);
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
        Game2PlayerOperationRoomNotify notifyOne = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUidAndNotUid(player.getUid()));
        notifyOne.receiveChatMsg(dto);
        return true;
    }

    @Override
    public boolean kicking(long targetUid) {
        Player target = PlayerServerModel.getInstance().getPlayerByPlayerId(targetUid);
        if(target == null){
            new AppGeneralError(AppErrorCode.NOT_PLAYER);
        }
        if(player.getVipLv() < target.getVipLv()){
            new AppGeneralError(AppErrorCode.TARGET_VIP_HIGTH);
        }
        if(player.getKickingCardNum() <= 0){
            new AppGeneralError(AppErrorCode.KICKING_CARD_INSUFFICIENT);
        }
        room.getPlayerSet().downPos(target);
        Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUid());
        notify.kicking(player.getUserName(),targetUid,target.getUserName());
        return false;
    }
}
