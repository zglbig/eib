package org.zgl.game.logic.player;

import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.dto.ItemDto;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.game1.Game1PlayerRoomBaseInfoDto;
import org.zgl.dto.server.Game1ServerPlayerDto;
import org.zgl.game.logic.room.HandCard;
import org.zgl.game.logic.room.Room;
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
    private int exchangeCardNum;
    private int kickingCardNum;
    /**该金币只是用来作为房间信息显示用的不做参与计算*/
    private long gold;
    /**使用的座驾id*/
    private int useAutoId;
    private String sex;
    private int scenesId;
    private int roomId;
    private int roomPosition;
    private Room room;
    /**持有的手牌*/
    private HandCard handCard;
    /**是否已经看牌*/
    private boolean isLoockCard;
    /**玩家本局是否已经输掉*/
    private boolean isLose;
    /**玩家本局是否还在玩*/
    private boolean hasPlay;
    /**玩家是否已经准备*/
    private boolean isReady;
    public Player(Game1ServerPlayerDto playerDto) {
        this.uid = playerDto.getUid();
        this.useAutoId = playerDto.getUseAutoId();
        this.userName = playerDto.getUserName();
        this.headImg = playerDto.getHeadImg();
        this.vipLv = playerDto.getVipLv();
        this.exchangeCardNum = playerDto.getExchangeCardNum();
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

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public boolean isLose() {
        return isLose;
    }

    public void setLose(boolean lose) {
        this.isLose = lose;
    }

    public int getVipLv() {
        return vipLv;
    }

    public boolean isHasPlay() {
        return hasPlay;
    }

    public void setHasPlay(boolean hasPlay) {
        this.hasPlay = hasPlay;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public int getExchangeCardNum() {
        return exchangeCardNum;
    }

    public void setExchangeCardNum(int exchangeCardNum) {
        this.exchangeCardNum = exchangeCardNum;
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

    public HandCard getHandCard() {
        return handCard;
    }

    public void setHandCard(HandCard handCard) {
        this.handCard = handCard;
    }

    public int getUseAutoId() {
        return useAutoId;
    }

    public void setUseAutoId(int useAutoId) {
        this.useAutoId = useAutoId;
    }

    public boolean isLoockCard() {
        return isLoockCard;
    }

    public void setLoockCard(boolean loockCard) {
        this.isLoockCard = loockCard;
    }

    public Game1PlayerRoomBaseInfoDto roomPlayerDto(){
        Game1PlayerRoomBaseInfoDto roomPlayerDto = new Game1PlayerRoomBaseInfoDto();
        roomPlayerDto.setUid(uid);
        roomPlayerDto.setGold(gold);
        roomPlayerDto.setHeadIcon(headImg);
        roomPlayerDto.setUserName(userName);
        roomPlayerDto.setVipLv(vipLv);
        roomPlayerDto.setAutoId(useAutoId);
        roomPlayerDto.setSex(this.sex);
        return roomPlayerDto;
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
    public void endOperation(){
        this.hasPlay = false;
        this.isLoockCard = false;
        this.isLose = false;
        this.isReady = false;
        this.handCard = null;
    }
}
