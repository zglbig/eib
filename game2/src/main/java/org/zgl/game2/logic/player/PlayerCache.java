package org.zgl.game2.logic.player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：
 */
public class PlayerCache {
    private final ConcurrentHashMap<Long, Player> playerMapByUid = new ConcurrentHashMap();
    private final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    public PlayerCache() {
    }
    public List<Player> getPlayerList() {
        this.LOCK.readLock().lock();
        ArrayList var1;
        try {
            var1 = new ArrayList(this.playerMapByUid.values());
        } finally {
            this.LOCK.readLock().unlock();
        }

        return var1;
    }
    public boolean isOnline(long uid){
        return playerMapByUid.containsKey(uid);
    }
    public int getPlayerSize() {
        this.LOCK.readLock().lock();
        int var1;
        try {
            var1 = this.playerMapByUid.size();
        } finally {
            this.LOCK.readLock().unlock();
        }

        return var1;
    }

    public void putPlayer(Player player) {
        this.LOCK.writeLock().lock();
        try {
            try {
                this.playerMapByUid.put(player.getUid(), player);
            } catch (Exception var7) {
//                LoggerUtils.getLogicLog().info("添加玩家异常",var7);
            }
        } finally {
            this.LOCK.writeLock().unlock();
        }

    }

    public void removePlayer(Player player) {
        this.LOCK.writeLock().lock();
        try {

            try {
                this.playerMapByUid.remove(player.getUid());
            } catch (Exception var7) {
//                LoggerUtils.getLogicLog().info("删除玩家异常",var7);
            }
        } finally {
            this.LOCK.writeLock().unlock();
        }

    }

    public Player getPlayerByPlayerId(long uid) {
        this.LOCK.readLock().lock();
        Player var2;
        try {
            var2 = this.playerMapByUid.get(uid);
        } finally {
            this.LOCK.readLock().unlock();
        }

        return var2;
    }
}
