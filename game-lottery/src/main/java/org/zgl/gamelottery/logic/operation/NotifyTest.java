package org.zgl.gamelottery.logic.operation;

import org.zgl.desc.ClinetProxy;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.lottery.LotteryHistoryDto;
import org.zgl.service.client.lottery.GameLotteryOperationRoomNotify;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClinetProxy
public class NotifyTest implements GameLotteryOperationRoomNotify {
    @Override
    public void betNotify(int nowBetPlayerNumber, long nowLotteryAllGold) {

    }

    @Override
    public void settleAccount(long bonus) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stopBet() {

    }

    @Override
    public void result(LotteryHistoryDto dto) {

    }

    @Override
    public void playerWeathUpdate(long uid, ItemListDto dto) {

    }
}
