package org.zgl.service.client.hall;

import org.zgl.dto.clinet.db.RebateDialDto;
import org.zgl.dto.clinet.db.RebateDialInfoDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClassDesc("返利轮盘")
public interface RebateDialOperation extends IHttpSyncService {
    @MethodDesc("抽奖")
    RebateDialDto luckyDraw(long uid);
    @MethodDesc("查看轮盘信息")
    RebateDialInfoDto dialInfo(long uid);
}
