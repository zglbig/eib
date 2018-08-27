package org.zgl.service.client.db;

import org.zgl.datable.DatableModelListDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@ClassDesc("从服务器获取游戏所需要的静态数据")
public interface DbDatableInfo extends IHttpSyncService {
    DatableModelListDto getData();
}
