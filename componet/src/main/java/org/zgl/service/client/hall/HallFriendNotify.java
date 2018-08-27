package org.zgl.service.client.hall;

import org.zgl.dto.clinet.commond.BasePlayerDto;
import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.INotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClassDesc("好友操作通知")
public interface HallFriendNotify extends INotify {
    @MethodDesc("有人請求添加为好友 uid：請求人的uid，userName：請求人的用户名")
    void hasFriendRequest(long uid, String userName);
    @MethodDesc("同意还好友請求")
    void friendConsent(BasePlayerDto dto);
    @MethodDesc("收到好友聊天信息")
    void receiveChatMsg(long sendUid, ChatDto msg);
}
