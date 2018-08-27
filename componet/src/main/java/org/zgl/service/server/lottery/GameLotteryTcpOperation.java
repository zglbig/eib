package org.zgl.service.server.lottery;

import org.zgl.dto.clinet.lottery.LotteryBetDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClassDesc("天天乐操作")
public interface GameLotteryTcpOperation extends ITcpAsyncService {
    @MethodDesc("下注 position:下注位置,chipCount:下注注数")
    LotteryBetDto bet(int position, int chipCount);
}
