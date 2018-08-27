package org.zgl.hall.logic;

import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.commond.BasePlayerDto;
import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.service.client.hall.HallFriendNotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClinetProxy
public class NotifyTest implements HallFriendNotify {
    @Override
    public void hasFriendRequest(long uid, String userName) {

    }

    @Override
    public void friendConsent(BasePlayerDto dto) {

    }

    @Override
    public void receiveChatMsg(long sendUid, ChatDto msg) {

    }

}
