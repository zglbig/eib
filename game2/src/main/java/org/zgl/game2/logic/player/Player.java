package org.zgl.game2.logic.player;

import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.dto.ItemDto;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.server.Game2ServerPlayerDto;
import org.zgl.game2.logic.room.Room;
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
    private String headImg;
    private int vipLv;
    private String sex;
    private int kickingCardNum;
    /**该金币只是用来作为房间信息显示用的不做参与计算*/
    private long gold;
    /**使用的座驾id*/
    private int useAutoId;

    private int scenesId;
    private int roomId;
    private int roomPosition;
    private Room room;
    public Player(Game2ServerPlayerDto playerDto) {
        this.uid = playerDto.getUid();
        this.useAutoId = playerDto.getUseAutoId();
        this.userName = playerDto.getUserName();
        this.headImg = playerDto.getHeadImg();
        this.vipLv = playerDto.getVipLv();
        this.kickingCardNum = playerDto.getKickingCardNum();
        this.gold = playerDto.getGold();
        this.sex = playerDto.getSex();
    }

    public Player() {
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


    public void setUid(long uid) {
        this.uid = uid;
    }


    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public int getKickingCardNum() {
        return kickingCardNum;
    }

    public void setKickingCardNum(int kickingCardNum) {
        this.kickingCardNum = kickingCardNum;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getUid() {
        return uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getScenesId() {
        return scenesId;
    }

    public void setScenesId(int scenesId) {
        this.scenesId = scenesId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomPosition() {
        return roomPosition;
    }

    public void setRoomPosition(int roomPosition) {
        this.roomPosition = roomPosition;
    }

    public int getUseAutoId() {
        return useAutoId;
    }

    public void setUseAutoId(int useAutoId) {
        this.useAutoId = useAutoId;
    }

    public boolean bet(long gold){
        if(this.gold < gold){
            return false;
        }
        PlayerWeathUpdate weath = HttpProxyOutboundHandler.createProxy(PlayerWeathUpdate.class,ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        ItemDto dto = weath.checkBet(this.uid,this.scenesId,gold);
        this.gold = dto.getItemCount();
        //同步到db服验证金币是否足够
        return true;
    }
    public boolean insertGold(long gold){
        PlayerWeathUpdate weath = HttpProxyOutboundHandler.createProxy(PlayerWeathUpdate.class,ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        List<ItemDto> items = new ArrayList<>(1);
        items.add(new ItemDto(1,gold));
        ItemListDto dto = weath.intertWeath(this.uid,items);
        updateWeath(dto.getItems());
        return true;
    }
    public void updateWeath(List<ItemDto> items){
        for(ItemDto item : items){
            switch (item.getItemId()){
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
