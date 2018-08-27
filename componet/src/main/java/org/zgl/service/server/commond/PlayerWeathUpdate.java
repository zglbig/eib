package org.zgl.service.server.commond;

import org.zgl.dto.ItemDto;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.hall.PawnshopDto;
import org.zgl.proxy.rule.IHttpSyncService;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/6
 * @文件描述：
 */
public interface PlayerWeathUpdate extends IHttpSyncService {
    /**
     * 增加财富
     * @param uid 增加的对象
     * @param items 增加的财富id和数量
     * @param
     * @return 返回增加之后的结果
     */
    ItemListDto intertWeath(long uid, List<ItemDto> items);

    /**
     * 减少财富
     * @param uid
     * @param items
     * @param
     * @return
     */
    ItemListDto reduceWeath(long uid, List<ItemDto> items);

    /**
     * 所有下注操作都应该只用这个接口 检测是否可以下注
     * 下注成功直接返回当前剩余财富
     * @param uid
     * @param
     * @param count
     * @return
     */
    ItemDto checkBet(long uid, int scenesId, long count);
    ItemListDto payWeath(long uid, int productId);

    /**
     * 当铺
     * @param uid
     * @param productId
     * @param count
     * @return
     */
    PawnshopDto pawnshop(long uid, int productId, int count);
}
