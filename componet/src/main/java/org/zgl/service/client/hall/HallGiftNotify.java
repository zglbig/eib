package org.zgl.service.client.hall;

import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.INotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/20
 * @文件描述：
 */
public interface HallGiftNotify extends INotify {
    @MethodDesc("收到谁礼物")
    void getPresents(int giftId, int giftCount, long giveUid, String giveUserName);
    @MethodDesc("收到感谢")
    void thank(int giftId, int giftCount, long giveUid, String giveUserName);
}
