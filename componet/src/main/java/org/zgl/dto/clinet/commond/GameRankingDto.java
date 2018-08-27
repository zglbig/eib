package org.zgl.dto.clinet.commond;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/22
 * @文件描述：
 */
@Protostuff("commond")
public class GameRankingDto implements SerializeMessage,Comparable<GameRankingDto> {
    private long winGold;
    private long holdGold;
    private String userName;

    public GameRankingDto() {
    }

    public GameRankingDto(long winGold, long holdGold, String userName) {
        this.winGold = winGold;
        this.holdGold = holdGold;
        this.userName = userName;
    }

    public long getWinGold() {
        return winGold;
    }

    public void setWinGold(long winGold) {
        this.winGold = winGold;
    }

    public long getHoldGold() {
        return holdGold;
    }

    public void setHoldGold(long holdGold) {
        this.holdGold = holdGold;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void insertWinGold(long count){
        this.winGold += count;
    }
    @Override
    public int compareTo(GameRankingDto o) {
        long temp = o.getWinGold() - this.holdGold;
        if(temp > 0){
            return 1;
        }else if(temp == 0){
            return 0;
        }else{
            return -1;
        }
    }
}
