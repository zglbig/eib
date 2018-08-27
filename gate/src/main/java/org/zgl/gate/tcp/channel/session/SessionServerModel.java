package org.zgl.gate.tcp.channel.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：
 */
public class SessionServerModel {
    private static SessionServerModel instance;
    private final SesseionCache sessionCache = new SesseionCache();
    private SessionServerModel() {
    }
    //线程安全的懒汉式单利模式
    private static class SingletonHolder {
        public final static SessionServerModel instance = new SessionServerModel();
    }
    public static SessionServerModel getInstance() {
        return SingletonHolder.instance;
    }

    public List<SessionEntity> getSessionList(){
        return sessionCache.getEntityList();
    }

    public int getEntitySize() {
        return this.sessionCache.getEntitySize();
    }

    public SessionEntity getEntityByChannel(Channel channel) {
        return this.sessionCache.getEntityByChannel(channel);
    }

    public SessionEntity getEntityByChannelId(ChannelId channelId) {
        return this.sessionCache.getEntityByChannelId(channelId);
    }

    public SessionEntity getEntityUid(long uid) {
        return this.sessionCache.getEntityByUid(uid);
    }

    public void putEntity(SessionEntity entity) {
        this.sessionCache.putEntity(entity);
    }

    public void removeEntity(SessionEntity entity) {
        this.sessionCache.removeEntity(entity);
    }

    public SesseionCache getSessionCache() {
        return sessionCache;
    }

    /**
     * 是否在线
     * @param channelId
     * @return
     */
    public boolean isOnline(ChannelId channelId){
        return sessionCache.isOnline(channelId);
    }
    public boolean isOnline(long uid){
        return sessionCache.isOnline(uid);
    }

}
