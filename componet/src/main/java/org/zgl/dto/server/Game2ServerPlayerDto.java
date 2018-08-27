package org.zgl.dto.server;

import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/6
 * @文件描述：
 */
public class Game2ServerPlayerDto  implements SerializeMessage {
    private long uid;
    private String userName;
    private String headImg;
    private String sex;
    private int vipLv;
    private int kickingCardNum;
    /**该金币只是用来作为房间信息显示用的不做参与计算*/
    private long gold;
    /**使用的座驾id*/
    private int useAutoId;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public int getKickingCardNum() {
        return kickingCardNum;
    }

    public void setKickingCardNum(int kickingCardNum) {
        this.kickingCardNum = kickingCardNum;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public int getUseAutoId() {
        return useAutoId;
    }

    public void setUseAutoId(int useAutoId) {
        this.useAutoId = useAutoId;
    }
}
