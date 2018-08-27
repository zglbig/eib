package org.zgl.service.client.db;

import org.zgl.dto.ItemListDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClassDesc("签到")
public interface SiginOperation extends ITcpAsyncService {
    @MethodDesc("签到奖励")
    ItemListDto sigin(long uid);
    @MethodDesc("签到转盘")
    ItemListDto dial(long uid);
}
