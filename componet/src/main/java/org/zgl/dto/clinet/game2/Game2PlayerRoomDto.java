package org.zgl.dto.clinet.game2;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Protostuff("game2")
public class Game2PlayerRoomDto  implements SerializeMessage {
    /**庄家 null为系统庄家*/
    private Game2PositionPlayerInfoDto banker;
    /**房间人数*/
    private int playerNum;
    /**房间状态*/
    private int roomStatus;
    /**房间剩余时间*/
    private int roomTimer;
    /**自己的位置*/
    private int selfPosition;
    /**下注上线*/
    private long betLimit;
    /**房间的6个位置的信息*/
    private List<Game2PositionPlayerInfoDto> positionInfo;

    public Game2PositionPlayerInfoDto getBanker() {
        return banker;
    }

    public void setBanker(Game2PositionPlayerInfoDto banker) {
        this.banker = banker;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
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

    public List<Game2PositionPlayerInfoDto> getPositionInfo() {
        return positionInfo;
    }

    public void setPositionInfo(List<Game2PositionPlayerInfoDto> positionInfo) {
        this.positionInfo = positionInfo;
    }
}
