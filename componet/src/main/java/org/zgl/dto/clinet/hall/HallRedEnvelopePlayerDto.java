package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：领取大厅红包的人的信息
 */
@Protostuff("hall")
public class HallRedEnvelopePlayerDto implements SerializeMessage {
    private String userName;
    private String headImgUrl;
    private long editTime;
    private long recieveGold;
    public HallRedEnvelopePlayerDto() {
    }
    public HallRedEnvelopePlayerDto(String userName, String headImgUrl, long recieveGold) {
        this.userName = userName;
        this.headImgUrl = headImgUrl;
        this.editTime = System.currentTimeMillis();
        this.recieveGold = recieveGold;
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
    public long getEditTime() {
        return editTime;
    }
    public void setEditTime(long editTime) {
        this.editTime = editTime;
    }
    public long getRecieveGold() {
        return recieveGold;
    }
    public void setRecieveGold(long recieveGold) {
        this.recieveGold = recieveGold;
    }
}
