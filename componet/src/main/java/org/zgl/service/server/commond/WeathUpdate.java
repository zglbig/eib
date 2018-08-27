package org.zgl.service.server.commond;

import org.zgl.dto.ItemListDto;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：db服财富有变更之后同步到其他房间（比如购买，天天了下注，结算）
 * 大厅和天天乐不用同步
 */
public interface WeathUpdate extends IHttpSyncService {
    void updateWeath(long uid, ItemListDto itemList);
}
