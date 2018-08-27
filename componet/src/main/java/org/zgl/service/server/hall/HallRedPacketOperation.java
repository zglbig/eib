package org.zgl.service.server.hall;

import org.zgl.dto.ItemDto;
import org.zgl.dto.clinet.hall.HallRedEnvelopePlayerListDto;
import org.zgl.dto.clinet.hall.HallRedPacketListDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：
 */
@ClassDesc("d大厅红包")
public interface HallRedPacketOperation extends ITcpAsyncService {
    @MethodDesc("打开大厅红包列表")
    HallRedPacketListDto openHallList();
    @MethodDesc("发大厅红包 返回自己当前剩余金币 redType：红包类型 1 普通红包 2 运气红包，count：发多少个红包 desc：说明")
    ItemDto redEnvelope(short redType, int count, String desc);
    //打开单个红包信息
    //点击领取红包
    @MethodDesc("领取红包")
    ItemDto recieve(long id);
    @MethodDesc("打开单个红包信息")
    HallRedEnvelopePlayerListDto openOneRed(long id);
}
