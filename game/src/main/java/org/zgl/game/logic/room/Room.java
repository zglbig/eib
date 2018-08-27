package org.zgl.game.logic.room;

import org.zgl.ArrayUtils;
import org.zgl.dto.clinet.game1.Game1PlayerRoomBaseInfoDto;
import org.zgl.dto.clinet.game1.Game1PlayerRoomDto;
import org.zgl.executer.pool.Worker;
import org.zgl.game.logic.card.CardManager;
import org.zgl.game.logic.player.Player;
import org.zgl.game.logic.player.PlayerServerModel;
import org.zgl.game.socket.WorkerInit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：
 */
public class Room {
    private final int scenesId;
    private final String roomName;
    private final int roomId;
    private final long createUid;
    private final long createTime;
    private boolean hasDestroy;
    private int roomStatus;
    private final PlayerSet playerSet;
    private final GamblingParty gamblingParty;
    private final CardManager cardManager;
    /**
     * 下注的金额数量对应的玩家账号
     */
    private Map<Long, Long> betGoldMap;
    private long allGold;
    private final Worker worker;
    public Room(int scensesId, int roomId, long createUid) {
        this.scenesId = scensesId;
        this.roomId = roomId;
        this.createUid = createUid;
        this.createTime = System.currentTimeMillis();
        this.hasDestroy = false;
        this.roomStatus = 1;
        this.cardManager = CardManager.getInstance();
        this.playerSet = new PlayerSet(this);
        this.gamblingParty = new GamblingParty(this);
        this.betGoldMap = new ConcurrentHashMap<>();
        this.roomName = roomName();
        this.worker = WorkerInit.getInstance().getPool().nextWorker();
    }

    public CardManager getCardManager() {
        return cardManager;
    }

    public long getAllGold() {
        return allGold;
    }

    public Map<Long, Long> getBetGoldMap() {
        return betGoldMap;
    }

    public void bet(long uid, long gold) {
        allGold += gold;
        long num1 = gold;
        if (betGoldMap.containsKey(uid)) {
            num1 += betGoldMap.get(uid);
        }
        betGoldMap.put(uid, num1);
    }

    public GamblingParty getGamblingParty() {
        return gamblingParty;
    }


    public String getRoomName() {

        return roomName;
    }

    public PlayerSet getPlayerSet() {
        return playerSet;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    public int getScenesId() {
        return scenesId;
    }

    public int getRoomId() {
        return roomId;
    }

    public long getCreateUid() {
        return createUid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setHasDestroy(boolean hasDestroy) {
        this.hasDestroy = hasDestroy;
    }

    public boolean isHasDestroy() {
        return hasDestroy;
    }

    public int getPlayerNum() {
        return playerSet.getRoomPlayerNum();
    }


    public Game1PlayerRoomDto enter(Player player) {
        //进入房间
        player.setRoomId(roomId);
        player.setRoom(this);
        this.playerSet.enter(player);
        return roomInfo(player);
    }

    public void timer() {
        gamblingParty.timer();
    }

    /**
     * 获取房间信息
     *
     * @param player
     * @return
     */
    private Game1PlayerRoomDto roomInfo(Player player) {
        List<Player> players = new ArrayList<>(playerSet.getPlayerByUidMap().values());
        List<Game1PlayerRoomBaseInfoDto> baseInfoDtos = new ArrayList<>();
        for (Player fpr : players) {
            Game1PlayerRoomBaseInfoDto prbifd = fpr.roomPlayerDto();
            prbifd.setHasReady(playerSet.getPlayerChoice(prbifd.getUid()));
            prbifd.setPostion(fpr.getRoomPosition());
            Long m = betGoldMap.getOrDefault(fpr.getUid(), null);
            m = m == null ? 0 : m;
            prbifd.setNowBetAll(m);
            //当前房间筹码
            prbifd.setNowChip(gamblingParty.getNowBottomChip());
            baseInfoDtos.add(prbifd);
            if (roomStatus == 2) {
                //已经看牌
                if (fpr.getHandCard() != null) {
                    if (fpr.isLoockCard() || fpr.isLose()) {
                        prbifd.setCardId(ArrayUtils.arrayToList(fpr.getHandCard().getCardIds()));
                    }
                }
            }
        }
        return new Game1PlayerRoomDto(getRoomId(), roomStatus, player.getRoomPosition(), player.getExchangeCardNum(), baseInfoDtos);
    }

    public Object exit(Player player) {
        //退出房间
        playerSet.exit(player.getUid());
        PlayerServerModel.getInstance().removePlayer(player);
        return null;
    }

    public void end() {
        playerSet.end();
        gamblingParty.sattleEnd();
        gamblingParty.end();
        allGold = 0;
        betGoldMap.clear();
    }

    public void submit(Runnable runnable) {
        this.worker.registerNewChannelTask(runnable);
    }

    private String roomName() {
        switch (this.scenesId) {
            case 1:
                return "经典初级";
            case 2:
                return "经典中级";
            case 3:
                return "经典高级";
            case 6:
                return "千王初级";
            case 7:
                return "千王中级";
            case 8:
                return "千王高级";
            default:
                return "";
        }
    }
}
