package org.zgl.hall.logic.pay;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public class PayManager {
    /**
     * 线程安全的懒汉式单利模式
     */
    private static class SingletonHolder {
        public final static PayManager instance = new PayManager();
    }

    public static PayManager getInstance() {
        return SingletonHolder.instance;
    }

    private final Map<Long, PayCacheModel> cacheModelMap;
    private final ReentrantReadWriteLock lock;

    private PayManager() {
        this.lock = new ReentrantReadWriteLock();
        this.cacheModelMap = new ConcurrentHashMap<>();
    }

    /**
     * 提交订单
     *
     * @return
     */
    public boolean submitForm(PayCacheModel model) {
        this.lock.writeLock().lock();
        try {
            cacheModelMap.put(model.getUid(), model);
            return true;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * 删除订单
     *
     * @param uid
     * @return
     */
    public boolean removeForm(long uid) {
        this.lock.writeLock().lock();
        try {
            if (cacheModelMap.containsKey(uid)) {
                cacheModelMap.remove(uid);
                return true;
            }
            return false;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * 删除超时的订单
     */
    public void timeOutForm() {
        this.lock.writeLock().lock();
        try {
            Iterator<Map.Entry<Long, PayCacheModel>> iterator = cacheModelMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Long, PayCacheModel> entry = iterator.next();
                //超时是分钟的订单是为超时
                if (System.currentTimeMillis() - entry.getValue().getCreateTime() >= 600000) {
                    iterator.remove();
                }
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public PayCacheModel model(long uid) {
        this.lock.writeLock().lock();
        try {
            return cacheModelMap.get(uid);
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}
