package org.zgl.dto.clinet.commond;

import org.zgl.DateUtils;
import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

@Protostuff("commond")
public class ChatDto implements SerializeMessage {
    /**广播类型 1，玩家广播 2 系统广播*/
    private long uid;
    private int msgType;
    private String username;
    private String headIcon;
    private int vipLv;
    private String msg;
    private String sex;
    private String sendTime;
    public ChatDto() {
        sendTime = DateUtils.nowTime();
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}