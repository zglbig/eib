package org.zgl.dto.clinet.dice;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
@Protostuff("dice")
public class DiceSettleRankingDto implements SerializeMessage,Comparable<DiceSettleRankingDto>{
    private long uid;
    private String userName;
    private long residueGold;
    private long winGold;

    public DiceSettleRankingDto() {
    }

    public DiceSettleRankingDto(long uid, String userName, long residueGold, long winGold) {
        this.uid = uid;
        this.userName = userName;
        this.residueGold = residueGold;
        this.winGold = winGold;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getResidueGold() {
        return residueGold;
    }

    public void setResidueGold(long residueGold) {
        this.residueGold = residueGold;
    }

    public long getWinGold() {
        return winGold;
    }

    public void setWinGold(long winGold) {
        this.winGold = winGold;
    }

    @Override
    public int compareTo(DiceSettleRankingDto o) {
        long temp = o.getWinGold() - this.winGold;
        if(temp<0){
            return -1;
        }else if(temp == 0){
            return 0;
        }else {
            return 1;
        }
    }
}
