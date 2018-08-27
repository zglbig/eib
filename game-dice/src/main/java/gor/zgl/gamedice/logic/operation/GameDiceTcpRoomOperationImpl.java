package gor.zgl.gamedice.logic.operation;

import org.springframework.stereotype.Component;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.gamedice.logic.Operation;
import org.zgl.gamedice.logic.player.Player;
import org.zgl.gamedice.logic.player.PlayerServerModel;
import org.zgl.gamedice.logic.room.PlayerSet;
import org.zgl.gamedice.logic.room.Room;
import org.zgl.gamedice.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.dice.GameDicePlayerOperationRoomNotify;
import org.zgl.service.server.dice.GameDiceTcpRoomOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
@Component
@ClinetProxy
public class GameDiceTcpRoomOperationImpl extends Operation implements GameDiceTcpRoomOperation {
    @Override
    public GameDiceBetUpdateWeathDto bet(int chip, int position) {
        if(player.getNowBetCount() >= Room.betLimit) {
            new AppGeneralError(AppErrorCode.BET_LIMIT);
        }
        int[] CHIP = {10,20,30,40,50,60,70,80};
        if(CHIP == null || chip <= 0) {
            //下注位置异常
            new AppGeneralError(AppErrorCode.SERVER_ERROR);
        }
        if(chip > CHIP.length || chip < 0) {
            //筹码位置一场
            new AppGeneralError(AppErrorCode.SERVER_ERROR);
        }

        if(room.getRoomStatus() != 1) {
            //房间已经停止下注等待下一句开始吧
            new AppGeneralError(AppErrorCode.BET_TIME_OUT);
        }
        int moneyNum = CHIP[chip];

        if(!player.bet(moneyNum)) {
            //减少金币金币不足
            new AppGeneralError(AppErrorCode.GOLD_NOT);
        }
        //日志
        UserLog.userLog(player.getUid(),"not card",moneyNum,"在骰子场"+position+"位置下注","not card");
        room.getGamblingParty().bet(player,moneyNum,position);
        return new GameDiceBetUpdateWeathDto(player.getGold(),moneyNum,position);
    }

    @Override
    public GameDiceBetUpdateWeathDto clearBet() {
        if(room.getRoomStatus() != 1) {
            new AppGeneralError(AppErrorCode.DICE_SETTLE_ACCOUNTS_NOT_CLEAR_BET);
        }
        long clearNum = room.getGamblingParty().clearBet(player);
        return new GameDiceBetUpdateWeathDto(player.getGold(),clearNum,0);
    }

    @Override
    public boolean positionDown() {
        return room.getPlayerSet().downPosition(player);
    }

    @Override
    public boolean positionUp(int position) {
        return room.getPlayerSet().upPosition(player,position);
    }

    @Override
    public GameDicePositionPlayerInfoListDto nowPlayPlayerList() {
        PlayerSet playerSet = room.getPlayerSet();
        List<Player> toPlayerList = playerSet.getAllPlayer();
        List<GameDicePositionPlayerInfoDto> baseInfoDtos = new ArrayList<>(toPlayerList.size());
        for(Player tp:toPlayerList){
            if(tp.getRoomPosition() == PlayerSet.DEFAULT_POSITION){
                baseInfoDtos.add(playerSet.baseInfo(tp,PlayerSet.DEFAULT_POSITION));
            }
        }
        Collections.sort(baseInfoDtos);
        return new GameDicePositionPlayerInfoListDto(baseInfoDtos);
    }

    @Override
    public DiceHistoryDto history() {
        List<DiceCountDto> list = room.getGamblingParty().getHistory();
        Collections.sort(list);
        return new DiceHistoryDto(list);
    }

    @Override
    public boolean kicking(long targetUid) {
        Player other = PlayerServerModel.getInstance().getPlayerByPlayerId(targetUid);
        if(other == null){
            new AppGeneralError(AppErrorCode.NOT_THIS_USER,targetUid);
        }
        if(other.getRoomPosition() == PlayerSet.DEFAULT_POSITION){
            new AppGeneralError(AppErrorCode.PLAYER_NOT_IN_POSITION);
        }
        if(player.getVipLv() < other.getVipLv()){
            new AppGeneralError(AppErrorCode.VIP_LV_INSUFFICIENT);
        }
        if(player.getKickingCardNum() <= 0){
            new AppGeneralError(AppErrorCode.KICKING_INSUFFICIENT);
        }
        return room.getPlayerSet().kicking(targetUid,player);
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
        GameDicePlayerOperationRoomNotify notifyOne = TcpProxyOutboundHandler.createProxy(GameDicePlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUidByNotPlayer(player.getUid()));
        notifyOne.receiveChatMsg(dto);
        return true;
    }
}
