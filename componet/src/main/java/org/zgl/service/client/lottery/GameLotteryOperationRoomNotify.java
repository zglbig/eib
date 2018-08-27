package org.zgl.service.client.lottery;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.lottery.LotteryHistoryDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.INotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClassDesc("天天乐服务器主动通知通知")
public interface GameLotteryOperationRoomNotify extends INotify {
    @MethodDesc("下注通知 nowBetPlayerNumber:当前下注人数,nowLotteryAllGold:当前房间下注总金额")
    void betNotify(int nowBetPlayerNumber, long nowLotteryAllGold);
    @MethodDesc("结算通知 bonus:中奖金额")
    void settleAccount(long bonus);
    @MethodDesc("房间开始")
    void start();
    @MethodDesc("停止下注")
    void stopBet();
    @MethodDesc("开奖结果")
    void result(LotteryHistoryDto dto);
    @MethodDesc("房间有玩家财富变更同步更新，uid：变更得uid，dto：变更得财富")
    void playerWeathUpdate(long uid, ItemListDto dto);
}
