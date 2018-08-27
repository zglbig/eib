package org.zgl.dto.clinet.db;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：
 */
@Protostuff("db")
public class GiftBagInfoDto implements SerializeMessage {
    private int day;
    private boolean isDone;
    private boolean isAward;
    public GiftBagInfoDto() {
    }

    public GiftBagInfoDto(int day, boolean isDone, boolean isAward) {
        this.day = day;
        this.isDone = isDone;
        this.isAward = isAward;
    }

    public boolean isAward() {
        return isAward;
    }

    public void setAward(boolean award) {
        isAward = award;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
