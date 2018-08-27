package org.zgl.dto.server;

import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/30
 * @文件描述：
 */
public class Game1EnterRoomRequestDto implements SerializeMessage {
    private long uid;
    private int scenesId;

    public Game1EnterRoomRequestDto() {
    }

    public Game1EnterRoomRequestDto(long uid, int scenesId) {
        this.uid = uid;
        this.scenesId = scenesId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getScenesId() {
        return scenesId;
    }

    public void setScenesId(int scenesId) {
        this.scenesId = scenesId;
    }
}
