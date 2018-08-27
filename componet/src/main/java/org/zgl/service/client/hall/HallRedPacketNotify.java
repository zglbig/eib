package org.zgl.service.client.hall;

import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.INotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public interface HallRedPacketNotify extends INotify {
    @MethodDesc("好友红包返还")
    void friendRedPacketBack(long residueGold, long reduceGold, String targetUserName);
    @MethodDesc("收到好友红包 userName：发红包的用户名 ，redPacketId：红包id")
    void recieveRedPacket(String userName, long redPacketId);
    @MethodDesc("好友领取了你的红包 gold：多少钱的红包")
    void friendBonus(String userName, long gold);
}
