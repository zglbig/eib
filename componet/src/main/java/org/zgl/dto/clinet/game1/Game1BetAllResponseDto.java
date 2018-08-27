package org.zgl.dto.clinet.game1;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */
@Protostuff("game1")
public class Game1BetAllResponseDto implements SerializeMessage {
    private long holdGold;
    private long betGold;
    private long roomGld;
    private long nextOperationUid;
    public Game1BetAllResponseDto() {
    }

    public Game1BetAllResponseDto(long holdGold, long betGold, long roomGld, long nextOperationUid) {
        this.holdGold = holdGold;
        this.betGold = betGold;
        this.roomGld = roomGld;
        this.nextOperationUid = nextOperationUid;
    }

    public long getHoldGold() {
        return holdGold;
    }

    public void setHoldGold(long holdGold) {
        this.holdGold = holdGold;
    }

    public long getBetGold() {
        return betGold;
    }

    public void setBetGold(long betGold) {
        this.betGold = betGold;
    }

    public long getRoomGld() {
        return roomGld;
    }

    public void setRoomGld(long roomGld) {
        this.roomGld = roomGld;
    }

    public long getNextOperationUid() {
        return nextOperationUid;
    }

    public void setNextOperationUid(long nextOperationUid) {
        this.nextOperationUid = nextOperationUid;
    }
}
