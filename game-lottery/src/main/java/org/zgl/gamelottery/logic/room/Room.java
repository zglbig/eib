package org.zgl.gamelottery.logic.room;
import org.zgl.dto.clinet.lottery.LotteryRoomInfoDto;
import org.zgl.executer.pool.Worker;
import org.zgl.gamelottery.logic.card.CardTypeManaer;
import org.zgl.gamelottery.logic.player.Player;
import org.zgl.gamelottery.logic.player.PlayerServerModel;
import org.zgl.gamelottery.socket.WorkerInit;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：
 */
public class Room {
    public static final long notifyLimit = 10000000;
    private final int scenesId;
    private final String roomName;
    private final int roomId;
    private final long createUid;
    private final long createTime;
    private boolean isDestroy;
    private int roomStatus;
    private final PlayerSet playerSet;
    private final GamblingParty gamblingParty;
    private final CardTypeManaer cardManager;
    private final Worker worker;
    public Room(int scensesId, int roomId, long createUid) {
        this.scenesId = scensesId;
        this.roomId = roomId;
        this.createUid = createUid;
        this.createTime = System.currentTimeMillis();
        this.isDestroy = false;
        this.roomStatus = 1;
        this.cardManager = CardTypeManaer.getInstance();
        this.playerSet = new PlayerSet(this);
        this.gamblingParty = new GamblingParty(this);
        this.roomName = "万人场";
        this.worker = WorkerInit.getInstance().getPool().nextWorker();
    }

    public CardTypeManaer getCardManager() {
        return cardManager;
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
        this.isDestroy = hasDestroy;
    }

    public boolean isHasDestroy() {
        return isDestroy;
    }

    public int getPlayerNum() {
        return 0;
    }


    public LotteryRoomInfoDto enter(Player player){
        //进入房间
        player.setRoom(this);
        this.playerSet.enter(player);
        return roomInfo(player);
    }
    /**
     * 获取房间信息
     * @param player
     * @return
     */
    private LotteryRoomInfoDto roomInfo(Player player) {
        LotteryRoomInfoDto dtos = new LotteryRoomInfoDto();
        dtos.setPlayerNum(gamblingParty.nowBetPlayerNum());
        long money = gamblingParty.getLastTimeGrantAward() > 0 ? gamblingParty.getLastTimeGrantAward() : 0;
        dtos.setLastTimeGrantAward(money);
        dtos.setNowBetMoney(gamblingParty.getAllGold());
        dtos.setHistoryDtos(gamblingParty.getHistoryDtos());
        dtos.setResidueTime(gamblingParty.getTimer());
        dtos.setRoomStatus(roomStatus);
        return dtos;
    }

    public boolean exit(Player player){
        //退出房间
        playerSet.exit(player);
        PlayerServerModel.getInstance().removePlayer(player);
        return true;
    }
    public void submin(Runnable runnable){
        this.worker.registerNewChannelTask(runnable);
    }
    public void timer(){
        gamblingParty.timer();
    }
}
