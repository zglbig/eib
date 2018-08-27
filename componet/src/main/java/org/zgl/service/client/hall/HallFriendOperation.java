package org.zgl.service.client.hall;

import org.zgl.dto.clinet.commond.BasePlayerDto;
import org.zgl.dto.clinet.hall.FriendListDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClassDesc("好友")
public interface HallFriendOperation extends ITcpAsyncService {
    boolean addFriend(long targetUid);
    boolean removeFriend(long targetUid);
    @MethodDesc("同意好友添加")
    BasePlayerDto consent(long targetUid);
    @MethodDesc("好友列表")
    FriendListDto friendList();
    @MethodDesc("发送好友聊天消息 msgType:消息类型1-普通文字 2-表情 ，msg：消息内容")
    boolean friendChat(long targetUid, int msgType, String msg);
}
