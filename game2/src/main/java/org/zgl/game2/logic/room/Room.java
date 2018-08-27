package org.zgl.game2.logic.room;
import org.zgl.dto.clinet.game2.Game2PlayerRoomDto;
import org.zgl.executer.pool.Worker;
import org.zgl.game2.logic.card.CardManager;
import org.zgl.game2.logic.player.Player;
import org.zgl.game2.logic.player.PlayerServerModel;
import org.zgl.game2.socket.WorkerInit;

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
        this.roomName = "万人场";
        this.worker = WorkerInit.getInstance().getPool().nextWorker();
    }

    public CardManager getCardManager() {
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
        this.hasDestroy = hasDestroy;
    }

    public boolean isHasDestroy() {
        return hasDestroy;
    }

    public int getPlayerNum() {
        return playerSet.getRoomPlayerNum();
    }


    public Game2PlayerRoomDto enter(Player player){
        //进入房间
        player.setRoomId(roomId);
        player.setRoom(this);
        this.playerSet.enter(player);
        return roomInfo(player);
    }
    /**
     * 获取房间信息
     * @param player
     * @return
     */
    private Game2PlayerRoomDto roomInfo(Player player) {
        Game2PlayerRoomDto info = new Game2PlayerRoomDto();
        Player banker = playerSet.getBankerPlayer();
        if(banker != null){
            info.setBanker(playerSet.getDto(banker));
        }
        info.setBetLimit(100000000);
        info.setPlayerNum(playerSet.getRoomPlayerNum());
        info.setRoomStatus(roomStatus);
        info.setSelfPosition(player.getRoomPosition());
        info.setPositionInfo(playerSet.getPositionPlayerInfoDtos());
        info.setRoomTimer(gamblingParty.getResidueTime());
        return info;
    }

    public boolean exit(Player player){
        //退出房间
        playerSet.exit(player.getUid());
        PlayerServerModel.getInstance().removePlayer(player);
        return true;
    }
    public void timer(){
        gamblingParty.timer();
    }
    public void submit(Runnable r){
        this.worker.registerNewChannelTask(r);
    }
}
