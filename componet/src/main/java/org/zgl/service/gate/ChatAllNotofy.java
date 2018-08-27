package org.zgl.service.gate;

import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.INotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/23
 * @文件描述：
 */
public interface ChatAllNotofy extends INotify {
    @MethodDesc("玩家全服聊天消息通知")
    void playerNote(ChatDto chatDto);
    @MethodDesc("系统全服聊天消息通知")
    void systemNote(ChatDto chatDto);
}
