package org.zgl.game.logic.room;


import org.zgl.dto.clinet.game1.Game1PlayerRoomBaseInfoDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.game.logic.player.Player;
import org.zgl.game.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.game1.Game1PlayerOperationRoomNotify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerSet {
    /**
     * 最大人数
     */
    public static final int MAX_SIZE = 5;
    /**
     * 玩家位置信息
     */
    private Room room;
    /**
     * 玩家集合
     */
    private ConcurrentHashMap<Long, Player> playerByUidMap;
    /**
     * 玩家位置
     */
    private Player[] playerRoomPostion;
    /**
     * 玩家的选择状态 key:玩家ID  value:是否同意
     */
    private final ConcurrentHashMap<Long, Boolean> playerChoiceStateMap = new ConcurrentHashMap<>();

    /**
     * 房间本局结束
     */
    public void end() {
        playerChoiceStateMap.clear();
    }

    public PlayerSet(Room room) {
        this.playerByUidMap = new ConcurrentHashMap<>();
        this.room = room;
        this.playerRoomPostion = new Player[MAX_SIZE];
    }

    /**
     * 离开房间
     *
     * @param uid
     */
    public void exit(long uid) {
        Player player = playerByUidMap.getOrDefault(uid, null);
        if (player != null) {
            this.playerByUidMap.remove(uid);
            this.playerChoiceStateMap.remove(uid);
            Player player1 = playerRoomPostion[player.getRoomPosition()];
            if (player1 != null && player1.getUid() == player.getUid()) {
                playerRoomPostion[player.getRoomPosition()] = null;
            }
        }
        Game1PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class, room.getPlayerSet().getAllPlayerUidAndNotUid(player.getUid()));
        notify.playerLoginOut(player.getUid());
    }

    public void enter(Player player) {
        this.resetpos(player);
        this.playerByUidMap.put(player.getUid(), player);
        Game1PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game1PlayerOperationRoomNotify.class, room.getPlayerSet().getAllPlayerUidAndNotUid(player.getUid()));
        Game1PlayerRoomBaseInfoDto dto = new Game1PlayerRoomBaseInfoDto();
        dto.setUid(player.getUid());
        dto.setUserName(player.getUserName());
        dto.setHeadIcon(player.getHeadImg());
        dto.setVipLv(player.getVipLv());
        dto.setGold(player.getGold());
        dto.setPostion(player.getRoomPosition());
        dto.setAutoId(player.getUseAutoId());
        dto.setSex(player.getSex());
        notify.playerEnterRoom(dto);
    }

    public Room getRoom() {
        return room;
    }

    /**
     * 设置玩家位置
     *
     * @param p
     */
    private void resetpos(Player p) {
        int pos = getNullPosition(p);
        if (pos == -1) {
            new AppGeneralError(AppErrorCode.SERVER_ERROR);
        }
        long uid = p.getUid();
    }

    private int getNullPosition(Player player) {
        for (int i = 0; i < playerRoomPostion.length; i++) {
            if (playerRoomPostion[i] != null) {
                playerRoomPostion[i] = player;
                player.setRoomPosition(i);
                return i;
            }
        }
        return -1;
    }

    public Map<Long, Player> getPlayerByUidMap() {
        return new HashMap<>(playerByUidMap);
    }

    /**
     * 获取指定玩家的位置
     *
     * @param
     * @return
     */
    public int getPlayerPositionByUid(long uid) {
        Player p = playerByUidMap.get(uid);
        return p == null ? -1 : p.getRoomPosition();
    }

    public List<Player> getAllPlayer() {
        return new ArrayList<>(playerByUidMap.values());
    }

    public List<Long> getAllPlayerUid() {
        return new ArrayList<>(playerByUidMap.keySet());
    }

    /**
     * 查找除某个uid除外的所有玩家uid
     *
     * @param uid
     * @return
     */
    public List<Long> getAllPlayerUidAndNotUid(long uid) {
        List<Long> list = new ArrayList<>();
        for (long id : playerByUidMap.keySet()) {
            if (id != uid) {
                list.add(id);
            }
        }
        return list;
    }

    /**
     * 获取除指定玩家除外的房间人
     *
     * @return
     */
    public List<Player> getNotAccountPlayer(long uid) {
        ArrayList<Player> arr = new ArrayList<>(playerByUidMap.size());
        for (Map.Entry<Long, Player> e : playerByUidMap.entrySet()) {
            if (!e.getKey().equals(uid)) {
                arr.add(e.getValue());
            }
        }
        return arr;
    }

    /**
     * 获取房间人数
     *
     * @return
     */
    public int getRoomPlayerNum() {
        return playerByUidMap.size();
    }

    /**
     * 获取下一个有人而且在玩 还没输掉的人的uid
     *
     * @param nowPosition
     * @return
     */
    public long getNextPositionUid(int nowPosition) {
        Player nextPos = getNextPos(nowPosition + 1, playerRoomPostion.length);
        if (nextPos == null) {
            nextPos = getNextPos(0, nowPosition - 1);
        }

        return nextPos.getUid();
    }

    /**
     * 获取下一个有人而且在玩 还没输掉的人的位置
     *
     * @param startIndex
     * @param endIndex
     * @param
     * @return
     */
    private Player getNextPos(int startIndex, int endIndex) {
        for (int i = startIndex; i < endIndex; i++) {
            Player p = playerRoomPostion[i];
            if (p != null && !p.isLose() && p.isHasPlay()) {
                return p;
            }
        }
        return null;
    }

    /**
     * 根据账号找到玩家
     *
     * @param uid
     * @return
     */
    public Player getPlayerForPosition(long uid) {
        return playerByUidMap.get(uid);
    }

    /**
     * 获取指定位置上的玩家
     *
     * @param position
     * @return
     */
    public Player getPlayerForPosition(int position) {
        if (position > playerRoomPostion.length || position < 0) {
            return null;
        }
        return playerRoomPostion[position];
    }

    /**
     * 添加一个玩家的选择
     *
     * @param uid
     * @param bor
     */
    public void addPlayerChoice(long uid, boolean bor) {
        this.playerChoiceStateMap.put(uid, bor);
    }

    /**
     * 获取指定选择的人数
     *
     * @param choice
     * @return
     */
    public int getChoiceNum(boolean choice) {
        int num = 0;
        for (Map.Entry<Long, Boolean> entry : this.playerChoiceStateMap.entrySet()) {
            if (choice == entry.getValue()) {
                num++;
            }
        }
        return num;
    }

    /**
     * 获取指定玩家的选择
     *
     * @param uid
     * @return
     */
    public boolean getPlayerChoice(long uid) {
        if (playerChoiceStateMap.containsKey(uid)) {
            return this.playerChoiceStateMap.get(uid);
        }
        return false;
    }

    /**
     * 获取房间指定玩家
     *
     * @param uid
     * @return
     */
    public Player getPlayerByUid(long uid) {
        return playerByUidMap.getOrDefault(uid, null);
    }

    /**
     * 获取房间所有已经准备的玩家
     *
     * @return
     */
    public List<Player> getReadyPlayer(boolean state) {
        List<Player> list = new ArrayList<>(5);
        for (Map.Entry<Long, Boolean> e : playerChoiceStateMap.entrySet()) {
            if (e.getValue() == state && playerByUidMap.containsKey(e.getKey())) {
                list.add(playerByUidMap.get(e.getKey()));
            }
        }
        return list;
    }

    /**
     * 当前所有在玩的玩家
     */
    public List<Player> nowAllPayPlayer() {
        List<Player> m = new ArrayList<>();
        for (int i = 0; i < playerRoomPostion.length; i++) {
            Player player = playerRoomPostion[i];
            if (player != null && player.isHasPlay() && player.isReady()) {
                m.add(player);
            }
        }
        return m;
    }
}
