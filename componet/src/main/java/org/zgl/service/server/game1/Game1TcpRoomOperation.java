package org.zgl.service.server.game1;

import org.zgl.dto.clinet.commond.CardsDto;
import org.zgl.dto.clinet.game1.Game1BetAllResponseDto;
import org.zgl.dto.clinet.game1.Game1PlayerRoomDto;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：房间操作
 */
public interface Game1TcpRoomOperation extends ITcpAsyncService {
    @MethodDesc("离开房间")
    boolean exitRoom();
    @MethodDesc("准备")
    boolean ready();
    @MethodDesc("看牌")
    CardsDto lookCard();
    @MethodDesc("下注 ：chip 下注筹码对应的索引值 ,返回自己剩余的金币")
    long bet(int chip);
    @MethodDesc("全压")
    Game1BetAllResponseDto betAll();
    @MethodDesc("加注 ：chipPosition 下注筹码对应的索引值 ,返回自己剩余的金币")
    long betAdd(int chipPosition);
    @MethodDesc("换房间")
    Game1PlayerRoomDto exchangeRoom();
    @MethodDesc("比牌")
    boolean compareCard(long targetUid);
    @MethodDesc("弃牌")
    boolean giveUpCard();
    @MethodDesc("发送聊天消息 msgType:消息类型1-普通文字 2-表情 ，msg：消息内容")
    boolean sendChatMsg(int msgType, String msg);
    @MethodDesc("踢人出房间")
    boolean kicking(long targetUid);
}
