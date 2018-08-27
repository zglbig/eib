package org.zgl.service.server.db;

import org.zgl.dto.ItemDto;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/20
 * @文件描述：
 */
public interface GiftHandler extends IHttpSyncService {
    ItemDto insertGift(long uid, int id, int count, long charm);

    /**
     * 减少礼物 当铺
     * @param id
     * @param count
     */
    ItemDto residuceGift(long uid, int id, int count);
}
