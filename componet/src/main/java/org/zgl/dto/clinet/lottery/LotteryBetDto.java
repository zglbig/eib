package org.zgl.dto.clinet.lottery;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@Protostuff("lottery")
public class LotteryBetDto implements SerializeMessage {
    private long reduceGold;
    private long roomAllGold;
    private long betGold;
    private int betPosition;

    public LotteryBetDto() {
    }

    public LotteryBetDto(long reduceGold, long roomAllGold, long betGold, int betPosition) {
        this.reduceGold = reduceGold;
        this.roomAllGold = roomAllGold;
        this.betGold = betGold;
        this.betPosition = betPosition;
    }

    public long getReduceGold() {
        return reduceGold;
    }

    public void setReduceGold(long reduceGold) {
        this.reduceGold = reduceGold;
    }

    public long getRoomAllGold() {
        return roomAllGold;
    }

    public void setRoomAllGold(long roomAllGold) {
        this.roomAllGold = roomAllGold;
    }

    public long getBetGold() {
        return betGold;
    }

    public void setBetGold(long betGold) {
        this.betGold = betGold;
    }

    public int getBetPosition() {
        return betPosition;
    }

    public void setBetPosition(int betPosition) {
        this.betPosition = betPosition;
    }
}
