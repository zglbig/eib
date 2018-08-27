package gor.zgl.gamedice.logic.room;

import org.zgl.dto.clinet.dice.DiceRoomInfiDto;
import org.zgl.executer.pool.Worker;
import org.zgl.gamedice.logic.player.Player;
import org.zgl.gamedice.socket.WorkerInit;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public class Room {
    public static int betLimit = 50000000;
    private final int scenesId;
    private final String roomName;
    private final int roomId;
    private final long createUid;
    private final long createTime;
    private boolean hasDestroy;
    private int roomStatus;
    private final PlayerSet playerSet;
    private final GamblingParty gamblingParty;
    private final Worker worker;
    public Room(int scensesId, int roomId, long createUid) {
        this.scenesId = scensesId;
        this.roomId = roomId;
        this.createUid = createUid;
        this.createTime = System.currentTimeMillis();
        this.hasDestroy = false;
        this.roomStatus = 1;
        this.playerSet = new PlayerSet(this);
        this.gamblingParty = new GamblingParty(this);
        this.roomName = "万人场";
        this.worker = WorkerInit.getInstance().getPool().nextWorker();
    }
    public boolean exit(Player p){
        playerSet.exit(p);
        return false;
    }

    public int getScenesId() {
        return scenesId;
    }

    public String getRoomName() {
        return roomName;
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

    public boolean isHasDestroy() {
        return hasDestroy;
    }

    public void setHasDestroy(boolean hasDestroy) {
        this.hasDestroy = hasDestroy;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    public PlayerSet getPlayerSet() {
        return playerSet;
    }

    public GamblingParty getGamblingParty() {
        return gamblingParty;
    }
    public void timer(){
        gamblingParty.timer();
    }
    public void submit(Runnable r){
        this.worker.registerNewChannelTask(r);
    }
    public DiceRoomInfiDto enter(Player player){
        playerSet.enter(player);
        DiceRoomInfiDto dto = new DiceRoomInfiDto();
        dto.setRoomNum(playerSet.getAllPlayer().size());
        dto.setRoomStatus(roomStatus);
        dto.setRoomTimer(gamblingParty.getResidueTime());
        dto.setBetAllNum(gamblingParty.getBetPlayerNum());
        dto.setBetLimit(GamblingParty.betLimit);
        dto.setBetAllNum(gamblingParty.getAllGold());
        dto.setSelfPosition(player.getRoomPosition());
        dto.setPositionInfo(playerSet.getPositionPlayer());
        return dto;
    }
}
