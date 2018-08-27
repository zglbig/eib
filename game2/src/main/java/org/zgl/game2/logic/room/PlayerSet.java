package org.zgl.game2.logic.room;

import org.zgl.dto.clinet.game2.Game2PositionPlayerInfoDto;
import org.zgl.game2.logic.player.Player;
import org.zgl.game2.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.game2.Game2PlayerOperationRoomNotify;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 */
public class PlayerSet {
    public static final int MAX_POSITION = 4;
    /**房间默认位置*/
    public static final int DEFAULT_POS = -1111;
    /**最大连庄次数*/
    private static final int MAX_BANKER_COUNT = 6;
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
     * 上庄列表
     */
    private LinkedList<Player> bankerUpList;
    /**
     * 房间本局结束
     */
    private Player bankerPlayer;
    /**当前玩家上庄次数*/
    private int bankerCount;
    public PlayerSet(Room room) {
        this.playerByUidMap = new ConcurrentHashMap<>();
        this.room = room;
        this.playerRoomPostion = new Player[4];
        this.bankerUpList = new LinkedList<>();
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
            Player player1 = playerRoomPostion[player.getRoomPosition()];
            if (player1 != null && player1.getUid() == player.getUid()) {
                downPos(player);
            }
            Iterator<Player> iterator = bankerUpList.iterator();
            while (iterator.hasNext()){
                if(uid == iterator.next().getUid()){
                    iterator.remove();
                }
            }
            if(uid == bankerPlayer.getUid()){
                bankerExit(uid);
            }
        }
    }

    public void enter(Player player) {
        this.resetpos(player);

        this.playerByUidMap.put(player.getUid(), player);

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
        //设置位置
        if (pos != -1) {
            //通知有人上位置
            Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class, getAllPlayerUidAndNotUid(p.getUid()));
            notify.newPlayerUpPosition(getDto(p));
        }
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

    public int getNowPositionNum() {
        int count = 0;
        for (int i = 0; i < playerRoomPostion.length; i++) {
            if (playerRoomPostion[i] != null) {
                count++;
            }
        }
        return count;
    }

    /**
     * 上位
     *
     * @param pos
     * @param p
     */
    public boolean upPosition(int pos, Player p) {
        //当前已经有位置
        Player temp = playerRoomPostion[pos];
        if (temp == null) {
            playerRoomPostion[pos] = p;
            p.setRoomPosition(pos);
            //通知所有玩家
            //通知所有人有人上了位置
            Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class, getAllPlayerUid());
            notify.newPlayerUpPosition(getDto(p));
            return true;
        }
        return false;
    }

    /**
     * 下位置
     *
     * @param p
     * @return
     */
    public boolean downPos(Player p) {
        playerRoomPostion[p.getRoomPosition()] = null;
        p.setRoomPosition(DEFAULT_POS);
        Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,getAllPlayerUid());
        notify.positionPlayerExitRoom(p.getUid());
        return true;
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
     * 根据账号找到玩家
     *
     * @param uid
     * @return
     */
    public Player getPlayerForPosition(long uid) {
        return playerByUidMap.get(uid);
    }

    /**
     * 申请上庄
     *
     * @param p
     */
    public boolean bankerUp(Player p) {
        if (bankerUpList.contains(p) || p.getUid() == bankerPlayer.getUid()) {
            return false;
        }
        bankerUpList.offer(p);
        Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class, getAllPlayerUidAndNotUid(p.getUid()));
        notify.bankerListCount(bankerUpList == null ? 0 : bankerUpList.size());
        return true;
    }
    private void bankerUp() {
        if(bankerPlayer != null) {
            return;
        }
        int bankerType = 1;
        Game2PositionPlayerInfoDto player = null;
        if (bankerUpList.size() > 0) {
            bankerPlayer = bankerUpList.pop();
            bankerCount = 0;
            //通知所有人换庄
           bankerType = 2;
           player = getDto(bankerPlayer);
        }
        Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class, getAllPlayerUid());
        notify.bankerExchange(bankerType,player);
    }
    /**
     * 下庄
     *
     * @param uid
     * @return
     */
    private boolean bankerExit(long uid) {
        if (bankerPlayer.getUid() == uid) {
            //通知上一庄家下庄了
            bankerPlayer = null;
            bankerDownTag = systemUid;
            return true;
        }
        return false;
    }
    private static final long systemUid = -11111111;
    private long bankerDownTag = systemUid;
    public boolean bankerDownTag(long uid){
        if (bankerPlayer.getUid() == uid) {
            bankerDownTag = uid;
            return true;
        }
        return false;
    }
    public Player getBankerPlayer() {
        return bankerPlayer;
    }
    public List<Player> getBankerList(){
        return new ArrayList<>(bankerUpList);
    }
    public List<Game2PositionPlayerInfoDto> getPositionPlayerInfoDtos() {
        List<Game2PositionPlayerInfoDto> list = new ArrayList<>();
        for (int i = 0; i < playerRoomPostion.length; i++) {
            Player p = playerRoomPostion[i];
            if (p != null) {
                list.add(getDto(p));
            }
        }
        return list;
    }
    /**
     * 结束的时候调用
     */
    public void addBankerCount() {
        if(bankerDownTag != systemUid){
            //主动下庄成功
            if(bankerPlayer.getUid() == bankerDownTag){
                bankerCount = 0;
                List<Long> uids = new ArrayList<>(1);
                uids.add(bankerDownTag);
                Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,uids);
                notify.bankerDownSuccess();
                bankerExit(bankerDownTag);
            }
        }
        bankerCount++;
        if(bankerPlayer != null){
            if(bankerPlayer.getGold() < 80000000){
                bankerCount = 0;
                List<Long> uids = new ArrayList<>(1);
                uids.add(bankerDownTag);
                Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,uids);
                notify.bankerDownByGlodInsufficient();
                bankerExit(bankerPlayer.getUid());
            }
        }
        if (bankerCount >= MAX_BANKER_COUNT) {
            if (bankerPlayer == null) {
                bankerCount = 0;
                return;
            }
            List<Long> uids = new ArrayList<>(1);
            uids.add(bankerPlayer.getUid());
            Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,uids);
            notify.bankerCountLimit();
            bankerExit(bankerPlayer.getUid());
        }
        bankerUp();
    }
    public Game2PositionPlayerInfoDto getDto(Player p) {
        Game2PositionPlayerInfoDto dto = new Game2PositionPlayerInfoDto();
        dto.setUid(p.getUid());
        dto.setUserName(p.getUserName());
        dto.setHeadUrl(p.getHeadImg());
        dto.setVipLv(p.getVipLv());
        dto.setGold(p.getGold());
        dto.setPosition(p.getRoomPosition());
        dto.setUseAutoId(p.getUseAutoId());
        return dto;
    }
}
