package org.zgl.service.server.hall;

import org.zgl.dto.clinet.hall.RedPacketDto;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public interface FriendRedPacketOperation extends ITcpAsyncService {
    @MethodDesc("发好友红包")
    RedPacketDto giveRedEnvelopes(long targetUid, long gold);
    @MethodDesc("领好友红包")
    RedPacketDto bonus(int id);
}
