package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@Protostuff("db")
public class PlayerInfoDto implements SerializeMessage {
    private long uid;
    private String userName;
    private String headImgUrl;
    private long gold;
    private int vipLv;
    private long diamond;
    private long charm;
    private String sigin;
    /**联系方式*/
    private String contactWay;

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

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public long getDiamond() {
        return diamond;
    }

    public void setDiamond(long diamond) {
        this.diamond = diamond;
    }

    public long getCharm() {
        return charm;
    }

    public void setCharm(long charm) {
        this.charm = charm;
    }

    public String getSigin() {
        return sigin;
    }

    public void setSigin(String sigin) {
        this.sigin = sigin;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }
}
