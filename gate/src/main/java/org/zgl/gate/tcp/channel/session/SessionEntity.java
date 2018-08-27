package org.zgl.gate.tcp.channel.session;

import io.netty.channel.Channel;
import org.zgl.message.GateIoMessage;

/**
 * @author ： big
 * @创建时间： 2018/6/20
 * @文件描述：
 */
public class SessionEntity {
    /**注册连接时取db服获取这些信息，主要用来做聊天显示*/
    private final long uid;
    private final Channel channel;
    private String userName;
    private int vipLv;
    private String headUrl;
    /**在线标记*/
    private boolean isOnline;
    /**最后有操作的时间*/
    private long lastEditTime;
    private String secretKey;
    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public SessionEntity(long uid, Channel channel) {
        this.uid = uid;
        this.channel = channel;
        this.lastEditTime = System.currentTimeMillis();
        this.isOnline = true;
    }

    public SessionEntity(long uid, Channel channel, String userName, int vipLv, String headUrl, String secretKey) {
        this.uid = uid;
        this.channel = channel;
        this.userName = userName;
        this.vipLv = vipLv;
        this.headUrl = headUrl;
        this.secretKey = secretKey;
        this.lastEditTime = System.currentTimeMillis();
        this.isOnline = true;
    }

    public Channel getChannel() {
        return channel;
    }
    public long getUid() {
        return uid;
    }
    public long getLastEditTime() {
        return lastEditTime;
    }
    public String getUserName() {
        return userName;
    }
    public int getVipLv() {
        return vipLv;
    }
    public String getHeadUrl() {
        return headUrl;
    }
    public void setLastEditTime(long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void write(GateIoMessage ioMessage){
        if(channel != null && channel.isActive()){
            channel.writeAndFlush(ioMessage);
        }
    }
}
