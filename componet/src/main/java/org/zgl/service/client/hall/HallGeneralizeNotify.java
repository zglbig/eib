package org.zgl.service.client.hall;

import org.zgl.dto.clinet.hall.GeneralizeAwardDto;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.INotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/22
 * @文件描述：
 */
public interface HallGeneralizeNotify extends INotify {
    @MethodDesc("如果玩家在线 通知推广人有玩家填了你的邀请码")
    void notifyInvite(GeneralizeAwardDto dto);
}
