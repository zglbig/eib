package org.zgl.service.gate;

import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/23
 * @文件描述：
 */
@ClassDesc("全服消息")
public interface ChatAll extends ITcpAsyncService {
    @MethodDesc("玩家聊天得全服消息")
    boolean playerChat(int msgType, String msg);
    @MethodDesc("系统消息")
    boolean systemChat(int msgType, String msg);
}
