package org.zgl.service.server.hall;

import org.zgl.dto.clinet.hall.PawnshopDto;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/20
 * @文件描述：礼物操作
 */
public interface GiftOperation extends ITcpAsyncService {
    @MethodDesc("赠送礼物 返回自己当前财富")
    long giveGifts(long targetUid, int giftId, int count);
    @MethodDesc("感谢赠送的礼物")
    long thank(long targetUid);
    @MethodDesc("当铺 返回剩余礼物和当前金币")
    PawnshopDto pawnshop(int id, int count);
}
