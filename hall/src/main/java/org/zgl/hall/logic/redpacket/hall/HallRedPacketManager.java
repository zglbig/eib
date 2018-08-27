package org.zgl.hall.logic.redpacket.hall;

import org.zgl.dto.clinet.hall.HallRedPacketDto;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：
 */
public class HallRedPacketManager {
    /**
     * 线程安全的懒汉式单利模式
     */
    private static class SingletonHolder {
        public final static HallRedPacketManager instance = new HallRedPacketManager();
    }

    public static HallRedPacketManager getInstance() {
        return SingletonHolder.instance;
    }

    private final Map<Long, HallRedPacketModel> red;
    private final ReentrantReadWriteLock lock;

    public HallRedPacketManager() {
        this.red = new HashMap<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public void put(HallRedPacketModel model) {
        this.lock.writeLock().lock();
        try {
            red.put(model.getDto().getId(), model);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public HallRedPacketModel getModel(long id) {
        this.lock.writeLock().lock();
        try {
            if (red.containsKey(id)) {
                return red.get(id);
            }
            return null;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public List<HallRedPacketDto> getHallRedPacketList() {
        List<HallRedPacketDto> list = new ArrayList<>();
        this.lock.writeLock().lock();
        try {
            Iterator<Map.Entry<Long, HallRedPacketModel>> iterator = red.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Long, HallRedPacketModel> entry = iterator.next();
                list.add(entry.getValue().getDto());
            }
            return list;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public void timeOut() {
        this.lock.writeLock().lock();
        try {
            int size = red.size();
            if (size <= 10) {
                return;
            }
            Iterator<Map.Entry<Long, HallRedPacketModel>> iterator = red.entrySet().iterator();
            while (iterator.hasNext() && size >= 10) {
                Map.Entry<Long, HallRedPacketModel> entry = iterator.next();
                //如果红包天数大于十天或者红包总个数大于10就删除
                if (System.currentTimeMillis() - entry.getValue().getCreateTime() >= 864000000) {
                    iterator.remove();
                }
                size--;
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}
