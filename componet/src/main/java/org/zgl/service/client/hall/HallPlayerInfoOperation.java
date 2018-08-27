package org.zgl.service.client.hall;

import org.zgl.dto.clinet.hall.GiftDto;
import org.zgl.dto.clinet.hall.PlayerInfoDto;
import org.zgl.dto.clinet.hall.PropDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@ClassDesc("玩家信息操作")
public interface HallPlayerInfoOperation extends IHttpSyncService {
    @MethodDesc("查看个人信息")
    PlayerInfoDto playerInfo(long uid);
    @MethodDesc("查看礼物")
    GiftDto playerGiftInfo(long uid);
    @MethodDesc("查看道具")
    PropDto playerPropInfo(long uid);
}
