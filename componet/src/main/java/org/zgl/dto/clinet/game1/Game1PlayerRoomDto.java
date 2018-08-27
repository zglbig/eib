package org.zgl.dto.clinet.game1;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

@Protostuff("game1")
public class Game1PlayerRoomDto implements SerializeMessage {
    private int roomId;
    private int roomState;
    private int selfPosition;
    /**换牌卡数量*/
    private int exchangeCardCount;
    /**房间其他人的信息*/
    private List<Game1PlayerRoomBaseInfoDto> players;

    public Game1PlayerRoomDto() {
    }

    public Game1PlayerRoomDto(int roomId, int roomState, int selfPosition, int exchangeCardCount, List<Game1PlayerRoomBaseInfoDto> players) {
        this.roomId = roomId;
        this.roomState = roomState;
        this.selfPosition = selfPosition;
        this.exchangeCardCount = exchangeCardCount;
        this.players = players;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public List<Game1PlayerRoomBaseInfoDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<Game1PlayerRoomBaseInfoDto> players) {
        this.players = players;
    }

    public int getRoomState() {
        return roomState;
    }

    public void setRoomState(int roomState) {
        this.roomState = roomState;
    }

    public int getSelfPosition() {
        return selfPosition;
    }

    public void setSelfPosition(int selfPosition) {
        this.selfPosition = selfPosition;
    }

    public int getExchangeCardCount() {
        return exchangeCardCount;
    }

    public void setExchangeCardCount(int exchangeCardCount) {
        this.exchangeCardCount = exchangeCardCount;
    }
}
