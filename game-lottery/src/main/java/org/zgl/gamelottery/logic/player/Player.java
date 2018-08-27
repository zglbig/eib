package org.zgl.gamelottery.logic.player;

import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.dto.ItemDto;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.server.GameLotteryServerPlayerDto;
import org.zgl.gamelottery.logic.room.Room;
import org.zgl.gamelottery.logic.room.RoomFactory;
import org.zgl.service.server.commond.PlayerWeathUpdate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：
 */
public class Player {
    private long uid;
    private String userName;
    private Room room;
    private long gold;
    private int scenesId;
    /**是否已经退出天天乐界面 用来标识用推出然后一些协议不用发送通知*/
    private boolean isExit;
    public Player(GameLotteryServerPlayerDto dto) {
        this.uid = dto.getUid();
        this.userName = dto.getUserName();
        this.gold = dto.getGold();
        this.room = RoomFactory.getInstance().getRoom();
        this.scenesId = room.getScenesId();
        this.isExit = false;
    }

    public Player() {
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public long getUid() {
        return uid;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public int getScenesId() {
        return scenesId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setScenesId(int scenesId) {
        this.scenesId = scenesId;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public boolean isExit() {
        return isExit;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    public boolean bet(long gold) {
        if (this.gold < gold) {
            return false;
        }
        PlayerWeathUpdate weath = HttpProxyOutboundHandler.createProxy(PlayerWeathUpdate.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        ItemDto dto = weath.checkBet(this.uid,this.scenesId,gold);
        this.gold = dto.getItemCount();
        //同步到db服验证金币是否足够
        return true;
    }

    public boolean insertGold(long gold) {
        PlayerWeathUpdate weath = HttpProxyOutboundHandler.createProxy(PlayerWeathUpdate.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        List<ItemDto> items = new ArrayList<>(1);
        items.add(new ItemDto(1, gold));
        ItemListDto dto = weath.intertWeath(this.uid, items);
        updateWeath(dto.getItems());
        return true;
    }

    public void updateWeath(List<ItemDto> items) {
        for (ItemDto item : items) {
            switch (item.getItemId()) {
                case 1:
                    this.gold = item.getItemCount();
                    break;
                //vip要另外算
                default:
                    break;
            }
        }
    }
}
