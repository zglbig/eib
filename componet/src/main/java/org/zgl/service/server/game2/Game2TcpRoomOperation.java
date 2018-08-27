package org.zgl.service.server.game2;
import org.zgl.dto.clinet.game2.Game2BetUpdateWeathDto;
import org.zgl.dto.clinet.game2.Game2HistoryListDto;
import org.zgl.dto.clinet.game2.Game2JackpotListDto;
import org.zgl.dto.clinet.game2.Game2PositionPlayerInfoListDto;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：房间操作
 */
public interface Game2TcpRoomOperation extends ITcpAsyncService {
    @MethodDesc("离开房间")
    boolean exitRoom();
    @MethodDesc("下注 ：chip 下注筹码对应的索引值 ,返回自己剩余的金币")
    Game2BetUpdateWeathDto bet(int chip, int position);
    int bankerUp();
    boolean bankerDown();
    Game2PositionPlayerInfoListDto bankerList();
    boolean positionDown();
    boolean positionUp(int position);
    Game2HistoryListDto history();
    Game2PositionPlayerInfoListDto playPlayerList();
    Game2JackpotListDto jackpot();
    @MethodDesc("发送聊天消息 msgType:消息类型1-普通文字 2-表情 ，msg：消息内容")
    boolean sendChatMsg(int msgType, String msg);
    @MethodDesc("踢人下坐")
    boolean kicking(long targetUid);
}
