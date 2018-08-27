package org.zgl.dto.clinet.game2;


import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @作者： big
 * @创建时间： 2018/6/21
 * @文件描述：
 */
@Protostuff("game2")
public class Game2JackpotDto implements SerializeMessage {
    private String userName;
    /**中了多少奖*/
    private long winGold;
    /**中奖类型*/
    private int awardType;

    public Game2JackpotDto() {
    }

    public Game2JackpotDto(String userName, long winGold, int awardType) {
        this.userName = userName;
        this.winGold = winGold;
        this.awardType = awardType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getWinGold() {
        return winGold;
    }

    public void setWinGold(long winGold) {
        this.winGold = winGold;
    }

    public int getAwardType() {
        return awardType;
    }

    public void setAwardType(int awardType) {
        this.awardType = awardType;
    }
}
