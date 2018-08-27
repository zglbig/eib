package org.zgl.dto.clinet.dice;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：
 */
@Protostuff("dice")
public class DiceRoomInfiDto implements SerializeMessage {
    private int betPlayerNum;
    private long betAllNum;
    private int roomNum;
    private int roomStatus;
    private int roomTimer;
    private int selfPosition;
    private long betLimit;
    //座位上的人
    List<GameDicePositionPlayerInfoDto> positionInfo;

    public int getBetPlayerNum() {
        return betPlayerNum;
    }

    public void setBetPlayerNum(int betPlayerNum) {
        this.betPlayerNum = betPlayerNum;
    }

    public long getBetAllNum() {
        return betAllNum;
    }

    public void setBetAllNum(long betAllNum) {
        this.betAllNum = betAllNum;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    public int getRoomTimer() {
        return roomTimer;
    }

    public void setRoomTimer(int roomTimer) {
        this.roomTimer = roomTimer;
    }

    public int getSelfPosition() {
        return selfPosition;
    }

    public void setSelfPosition(int selfPosition) {
        this.selfPosition = selfPosition;
    }

    public long getBetLimit() {
        return betLimit;
    }

    public void setBetLimit(long betLimit) {
        this.betLimit = betLimit;
    }

    public List<GameDicePositionPlayerInfoDto> getPositionInfo() {
        return positionInfo;
    }

    public void setPositionInfo(List<GameDicePositionPlayerInfoDto> positionInfo) {
        this.positionInfo = positionInfo;
    }
}
