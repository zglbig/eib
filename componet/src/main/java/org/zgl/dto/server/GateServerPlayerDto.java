package org.zgl.dto.server;

import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */

public class GateServerPlayerDto implements SerializeMessage {
    private long uid;
    private String userName;
    private String headUrl;
    private int vipLv;
    private String secretKey;
    private String sex;
    public GateServerPlayerDto() {
    }

    public GateServerPlayerDto(long uid, String userName, String headUrl, int vipLv, String secretKey, String sex) {
        this.uid = uid;
        this.userName = userName;
        this.headUrl = headUrl;
        this.vipLv = vipLv;
        this.secretKey = secretKey;
        this.sex = sex;
    }

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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
