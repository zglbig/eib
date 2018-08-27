package org.zgl.service.client.db;

import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.db.GiftBagInfoDto;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：
 */
public interface DbGiftBagOperation extends IHttpSyncService {
    //打开成长礼包 回发礼包信息
    @MethodDesc("打开成长礼包信息")
    GiftBagInfoDto open(long uid);
    //点击领取礼包奖励
    @MethodDesc("领取奖励")
    ItemListDto receiveAward(long uid);
}
