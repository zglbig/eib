package org.zgl.dto.server;

import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
public class GameLotteryServerPlayerDto  implements SerializeMessage {
    private long uid;
    private String userName;
    private long gold;

    public GameLotteryServerPlayerDto() {
    }

    public GameLotteryServerPlayerDto(long uid, String userName, long gold) {
        this.uid = uid;
        this.userName = userName;
        this.gold = gold;
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

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }
}
