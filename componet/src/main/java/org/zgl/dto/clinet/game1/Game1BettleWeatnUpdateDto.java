package org.zgl.dto.clinet.game1;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */
@Protostuff("game1")
public class Game1BettleWeatnUpdateDto implements SerializeMessage {
    private long uid;
    private long holdGold;
    public Game1BettleWeatnUpdateDto() {
    }

    public Game1BettleWeatnUpdateDto(long uid, long holdGold) {
        this.uid = uid;
        this.holdGold = holdGold;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getHoldGold() {
        return holdGold;
    }

    public void setHoldGold(long holdGold) {
        this.holdGold = holdGold;
    }
}
