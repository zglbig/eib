package org.zgl.gate.tcp.channel.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：
 */
public class SesseionCache {
    private final ConcurrentHashMap<ChannelId, SessionEntity> sessionMapByChannelId = new ConcurrentHashMap();
    private final ConcurrentHashMap<Long, SessionEntity> sessionMapByUid = new ConcurrentHashMap();
    private final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    public SesseionCache() {
    }
    public List<SessionEntity> getEntityList() {
        this.LOCK.readLock().lock();
        ArrayList var1;
        try {
            var1 = new ArrayList(this.sessionMapByUid.values());
        } finally {
            this.LOCK.readLock().unlock();
        }

        return var1;
    }

    public boolean isOnline(ChannelId channelId){
        return sessionMapByChannelId.containsKey(channelId);
    }
    public boolean isOnline(long uid){
        return sessionMapByUid.containsKey(uid);
    }
    public int getEntitySize() {
        this.LOCK.readLock().lock();
        int var1;
        try {
            var1 = this.sessionMapByUid.size();
        } finally {
            this.LOCK.readLock().unlock();
        }

        return var1;
    }

    public void putEntity(SessionEntity sessionEntity) {
        this.LOCK.writeLock().lock();
        try {
            try {
                this.sessionMapByChannelId.put(sessionEntity.getChannel().id(), sessionEntity);
            } catch (Exception var8) {
//                LoggerUtils.getLogger().info("添加玩家异常",var8);
            }

            try {
                this.sessionMapByUid.put(sessionEntity.getUid(), sessionEntity);
            } catch (Exception var7) {
//                LoggerUtils.getLogger().info("添加玩家异常",var7);
            }
        } finally {
            this.LOCK.writeLock().unlock();
        }

    }

    public void removeEntity(SessionEntity sessionEntity) {
        this.LOCK.writeLock().lock();
        try {
            try {
                this.sessionMapByChannelId.remove(sessionEntity.getChannel().id());
            } catch (Exception var8) {
//                LoggerUtils.getLogger().info("删除玩家异常",var8);
            }

            try {
                this.sessionMapByUid.remove(sessionEntity.getUid());
            } catch (Exception var7) {
//                LoggerUtils.getLogger().info("删除玩家异常",var7);
            }
        } finally {
            this.LOCK.writeLock().unlock();
        }

    }

    public SessionEntity getEntityByChannel(Channel channel) {
        return this.getEntityByChannelId(channel.id());
    }

    public SessionEntity getEntityByChannelId(ChannelId channelId) {
        this.LOCK.readLock().lock();
        SessionEntity var2;
        try {
            var2 = this.sessionMapByChannelId.get(channelId);
        } finally {
            this.LOCK.readLock().unlock();
        }

        return var2;
    }

    public SessionEntity getEntityByUid(long uid) {
        this.LOCK.readLock().lock();
        SessionEntity var2;
        try {
            var2 = this.sessionMapByUid.get(uid);
        } finally {
            this.LOCK.readLock().unlock();
        }

        return var2;
    }
}
