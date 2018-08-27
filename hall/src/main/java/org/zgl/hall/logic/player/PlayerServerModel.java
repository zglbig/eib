package org.zgl.hall.logic.player;


/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：
 */
public class PlayerServerModel {
    private static PlayerServerModel instance;

    public static PlayerServerModel getInstance() {
        if (instance == null) {
            instance = new PlayerServerModel();
        }
        return instance;
    }

    /**
     * 场景对应的房间集合对应的房间
     */
    private PlayerCache playerCache = new PlayerCache();

    public int getPlayerSize() {
        return this.playerCache.getPlayerSize();
    }

    public Player getPlayerByPlayerId(long uid) {
        return this.playerCache.getPlayerByPlayerId(uid);
    }

    public void putPlayer(Player player) {
        this.playerCache.putPlayer(player);
    }

    public void removePlayer(Player player) {
        this.playerCache.removePlayer(player);
    }

    public PlayerCache getPlayerCache() {
        return playerCache;
    }

}
