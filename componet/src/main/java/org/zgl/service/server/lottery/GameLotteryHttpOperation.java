package org.zgl.service.server.lottery;

import org.zgl.dto.clinet.lottery.LotteryRoomInfoDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClassDesc("天天乐操作")
public interface GameLotteryHttpOperation extends IHttpSyncService {
    @MethodDesc("打开天天乐界面的时候调用这个方法")
    LotteryRoomInfoDto enter(long uid);
    @MethodDesc("关闭天天乐界面的狮虎调用这个方法")
    boolean exit(long uid);
}
