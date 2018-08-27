package org.zgl.service.server.dice;

import org.zgl.dto.clinet.dice.DiceHistoryDto;
import org.zgl.dto.clinet.dice.GameDiceBetUpdateWeathDto;
import org.zgl.dto.clinet.dice.GameDicePositionPlayerInfoListDto;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public interface GameDiceTcpRoomOperation extends ITcpAsyncService {
    @MethodDesc("下注")
    GameDiceBetUpdateWeathDto bet(int chip, int position);
    @MethodDesc("清除下注 返回剩下多少钱，返回乐多少钱")
    GameDiceBetUpdateWeathDto clearBet();
    @MethodDesc("下位置")
    boolean positionDown();
    @MethodDesc("上位置")
    boolean positionUp(int position);
    @MethodDesc("当前房间没在位置上所有的人")
    GameDicePositionPlayerInfoListDto nowPlayPlayerList();
    DiceHistoryDto history();
    @MethodDesc("踢人下坐")
    boolean kicking(long targetUid);
    @MethodDesc("发送聊天消息 msgType:消息类型1-普通文字 2-表情 ，msg：消息内容")
    boolean sendChatMsg(int msgType, String msg);
}
