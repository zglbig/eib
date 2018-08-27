package org.zgl.service.server.hall;

import org.zgl.dto.clinet.commond.GoldBaseDto;
import org.zgl.dto.clinet.hall.GeneralizeAwardDto;
import org.zgl.dto.clinet.hall.GeneralizeListDto;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
public interface GeneralizeOperation extends ITcpAsyncService {
    @MethodDesc("打开推广列表")
    GeneralizeListDto openGeneralizeList();
    @MethodDesc("邀请")
    GeneralizeAwardDto invite(long targetUid);
    @MethodDesc("领取奖励")
    GoldBaseDto receiveAward();
}
