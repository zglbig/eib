package org.zgl.hall.logic.friend;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
public class FriendModelManager {
    /**
     * 线程安全的懒汉式单利模式
     */
    private static class SingletonHolder {
        public final static FriendModelManager instance = new FriendModelManager();
    }

    public static FriendModelManager getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 添加好友是暂时放在这然后定时删除
     */
    private final Map<Long, FriendTempModel> friendTempMap;
    private final ReentrantReadWriteLock lock;

    private FriendModelManager() {
        this.lock = new ReentrantReadWriteLock();
        this.friendTempMap = new ConcurrentHashMap<>();
    }

    /**
     * 請求添加
     *
     * @param
     * @param
     */
    public void putMap(long add, long target) {
        lock.writeLock().lock();
        try {
            FriendTempModel model = friendTempMap.get(target);
            if (model == null) {
                model = new FriendTempModel();
                friendTempMap.put(target, model);
            }
            model.putMap(add);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 同意添加
     *
     * @param yes
     * @param other
     */
    public void remove(long yes, long other) {
        lock.writeLock().lock();
        try {
            FriendTempModel model = friendTempMap.get(yes);
            if (model == null) {
                return;
            }
            model.remove(other);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void checkTimeOut() {
        lock.writeLock().lock();
        try {
            Iterator<Map.Entry<Long, FriendTempModel>> iterator = friendTempMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Long, FriendTempModel> map = iterator.next();
                map.getValue().timeOut();
                if (map.getValue().isNull()) {
                    iterator.remove();
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}
