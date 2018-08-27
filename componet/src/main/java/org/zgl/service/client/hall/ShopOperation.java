package org.zgl.service.client.hall;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */

import org.zgl.dto.ItemListDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * 商城操作
 */
@ClassDesc("商城操作")
public interface ShopOperation extends ITcpAsyncService {
    @MethodDesc("购买vip")
    ItemListDto pay(int productId);
}
