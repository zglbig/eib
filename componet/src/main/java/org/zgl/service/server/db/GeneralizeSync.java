package org.zgl.service.server.db;

import org.zgl.dto.clinet.commond.GoldBaseDto;
import org.zgl.dto.clinet.hall.GeneralizeAwardDto;
import org.zgl.dto.clinet.hall.GeneralizeListDto;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：推广同步到大厅
 */
public interface GeneralizeSync extends IHttpSyncService {
    @MethodDesc("打开推广列表")
    GeneralizeListDto openGeneralizeList(long uid);
    @MethodDesc("邀请")
    GeneralizeAwardDto invite(long uid, long targetUid);
    @MethodDesc("领取奖励")
    GoldBaseDto receiveAward(long uid);
}
