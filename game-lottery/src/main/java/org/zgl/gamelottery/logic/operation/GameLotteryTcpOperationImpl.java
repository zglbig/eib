package org.zgl.gamelottery.logic.operation;

import org.springframework.stereotype.Component;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.lottery.LotteryBetDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.gamelottery.logic.Operation;
import org.zgl.gamelottery.logic.room.GamblingParty;
import org.zgl.service.server.lottery.GameLotteryTcpOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClinetProxy
@Component
public class GameLotteryTcpOperationImpl extends Operation implements GameLotteryTcpOperation {
    @Override
    public LotteryBetDto bet(int position, int chipCount) {
        if (room.getRoomStatus() != 1) {
            new AppGeneralError(AppErrorCode.BET_TIME_OUT);
        }
        int betCountx = room.getGamblingParty().canBet(player.getUid(), position);
        if (betCountx == -1) {
            new AppGeneralError(AppErrorCode.BET_UNNECESSARY);
        }
        //还可以下几注
        betCountx = chipCount > betCountx ? betCountx : chipCount;
        long allNum = betCountx * GamblingParty.BET_COUNT;

        if (!player.bet(allNum)) {
            new AppGeneralError(AppErrorCode.GOLD_NOT);
        }
        UserLog.userLog(player.getUid(),"not card",allNum,"在天天乐"+position+"位置下注","not card");
        room.getGamblingParty().bet(position, allNum, player, betCountx);
        //房间同步该玩家财富
        return new LotteryBetDto(player.getGold(),room.getGamblingParty().getAllGold(),allNum,position);
    }
}
