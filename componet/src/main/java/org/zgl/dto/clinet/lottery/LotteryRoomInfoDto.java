package org.zgl.dto.clinet.lottery;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@Protostuff("lottery")
public class LotteryRoomInfoDto implements SerializeMessage {
    /**当前在线人数*/
    private int playerNum;
    /**上期奖励*/
    private long lastTimeGrantAward;
    /**当前下注所有金额*/
    private long nowBetMoney;
    /**房间剩余时间*/
    private int residueTime;
    /**房间状态*/
    private int roomStatus;
    private List<LotteryHistoryDto> historyDtos;

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public long getLastTimeGrantAward() {
        return lastTimeGrantAward;
    }

    public void setLastTimeGrantAward(long lastTimeGrantAward) {
        this.lastTimeGrantAward = lastTimeGrantAward;
    }

    public long getNowBetMoney() {
        return nowBetMoney;
    }

    public void setNowBetMoney(long nowBetMoney) {
        this.nowBetMoney = nowBetMoney;
    }

    public int getResidueTime() {
        return residueTime;
    }

    public void setResidueTime(int residueTime) {
        this.residueTime = residueTime;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    public List<LotteryHistoryDto> getHistoryDtos() {
        return historyDtos;
    }

    public void setHistoryDtos(List<LotteryHistoryDto> historyDtos) {
        this.historyDtos = historyDtos;
    }
}
