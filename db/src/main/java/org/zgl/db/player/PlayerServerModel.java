package org.zgl.db.player;

import org.zgl.db.dao.entity.Player;

public class PlayerServerModel {
    private static PlayerServerModel instance;
    public static PlayerServerModel getInstance(){
        if(instance == null){
            instance = new PlayerServerModel();
        }
        return instance;
    }
    /**场景对应的房间集合对应的房间*/
    private PlayerCache playerCache = new PlayerCache();

    public int getPlayerSize() {
        return this.playerCache.getPlayerSize();
    }

    public Player getPlayerByPlayerId(long uid) {
        return this.playerCache.getPlayerByPlayerId(uid);
    }
    public boolean isOnline(long uid){
        return playerCache.isOnline(uid);
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