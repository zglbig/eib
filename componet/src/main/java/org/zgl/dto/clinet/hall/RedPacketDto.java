package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
@Protostuff("hall")
public class RedPacketDto {
    private long residueGold;
    private long reduceGold;
    private String friendUserName;

    public RedPacketDto() {
    }

    public RedPacketDto(long residueGold, long reduceGold, String friendUserName) {
        this.residueGold = residueGold;
        this.reduceGold = reduceGold;
        this.friendUserName = friendUserName;
    }

    public long getResidueGold() {
        return residueGold;
    }

    public void setResidueGold(long residueGold) {
        this.residueGold = residueGold;
    }

    public long getReduceGold() {
        return reduceGold;
    }

    public void setReduceGold(long reduceGold) {
        this.reduceGold = reduceGold;
    }

    public String getFriendUserName() {
        return friendUserName;
    }

    public void setFriendUserName(String friendUserName) {
        this.friendUserName = friendUserName;
    }
}
