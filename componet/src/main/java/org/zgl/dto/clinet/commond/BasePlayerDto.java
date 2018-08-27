package org.zgl.dto.clinet.commond;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@Protostuff("commond")
public class BasePlayerDto implements SerializeMessage {
    private long uid;
    private String userName;
    private String headImgUrl;
    private int vipLv;

    public BasePlayerDto() {
    }

    public BasePlayerDto(long uid, String userName, String headImgUrl, int vipLv) {
        this.uid = uid;
        this.userName = userName;
        this.headImgUrl = headImgUrl;
        this.vipLv = vipLv;
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

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }
}
