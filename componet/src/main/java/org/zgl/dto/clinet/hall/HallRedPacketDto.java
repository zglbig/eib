package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：
 */
@Protostuff("hall")
public class HallRedPacketDto implements SerializeMessage {
    private long id;
    private String userName;
    private String headImgUrl;
    private String desc;
    private boolean isFinish;
    private short redPacketType;
    private long createTime;
    public HallRedPacketDto() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public short getRedPacketType() {
        return redPacketType;
    }

    public void setRedPacketType(short redPacketType) {
        this.redPacketType = redPacketType;
    }
}
