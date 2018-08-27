package org.zgl.hall.logic.redpacket.friend;

import org.zgl.hall.logic.UserLog;
import org.zgl.hall.logic.player.Player;
import org.zgl.hall.logic.player.PlayerServerModel;
import org.zgl.hall.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.hall.HallRedPacketNotify;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public class FriendRedPacketManager {
    /**
     * 线程安全的懒汉式单利模式
     */
    private static class SingletonHolder {
        public final static FriendRedPacketManager instance = new FriendRedPacketManager();
    }

    public static FriendRedPacketManager getInstance() {
        return SingletonHolder.instance;
    }

    private final Map<Long, List<RedPacketModel>> cacheModelMap;
    private final ReentrantReadWriteLock lock;

    private FriendRedPacketManager() {
        this.cacheModelMap = new ConcurrentHashMap<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public boolean putMap(RedPacketModel model) {
        this.lock.writeLock().lock();
        try {
            List<RedPacketModel> models = cacheModelMap.getOrDefault(model.getTargetUid(), null);
            if (models != null) {
                models.add(model);
            } else {
                models = new ArrayList<>();
                models.add(model);
                cacheModelMap.put(model.getTargetUid(), models);
            }
        } finally {
            this.lock.writeLock().unlock();
        }
        return true;
    }

    public RedPacketModel getModel(long uid, int id) {
        this.lock.writeLock().lock();
        try {
            if (cacheModelMap.containsKey(uid)) {
                List<RedPacketModel> redPacketModels = cacheModelMap.get(uid);
                Iterator<RedPacketModel> iterator = redPacketModels.iterator();
                while (iterator.hasNext()) {
                    RedPacketModel model = iterator.next();
                    if (model.getId() == id) {
                        return model;
                    }
                }
                return null;
            } else {
                return null;
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public boolean remove(long uid, long id) {
        this.lock.writeLock().lock();
        try {
            if (cacheModelMap.containsKey(uid)) {
                List<RedPacketModel> redPacketModels = cacheModelMap.get(uid);
                Iterator<RedPacketModel> iterator = redPacketModels.iterator();
                while (iterator.hasNext()) {
                    RedPacketModel model = iterator.next();
                    if (model.getId() == id) {
                        iterator.remove();
                        return true;
                    }
                }
                return false;
            } else {
                return false;
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public void timeOut() {
        this.lock.writeLock().lock();
        try {
            Iterator<Map.Entry<Long, List<RedPacketModel>>> iterator = cacheModelMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Long, List<RedPacketModel>> entry = iterator.next();
                Iterator<RedPacketModel> iterator1 = entry.getValue().iterator();
                while (iterator1.hasNext()) {
                    RedPacketModel model = iterator1.next();
                    if (System.currentTimeMillis() - model.getCreateTime() >= 20000) {
                        iterator1.remove();
                        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(model.getUid());
                        if (player == null) {
                            continue;
                        }
                        player.insertGold(model.getGold());
                        UserLog.userLog(player.getUid(), model.getGold(), "发给uid为" + model.getTargetUid() + "昵称为" + model.getTargetUserName() + "的红包超时返还");
                        List<Long> uids = new ArrayList<>(1);
                        uids.add(player.getUid());
                        HallRedPacketNotify notify = TcpProxyOutboundHandler.createProxy(HallRedPacketNotify.class, uids);
                        //通知自己发给谁的红包返还了
                        notify.friendRedPacketBack(player.getGold(), model.getGold(), model.getTargetUserName());
                    }
                }
                if (entry.getValue().size() <= 0) {
                    iterator.remove();
                }
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}
