package org.zgl.hall.logic.player;

import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.dto.ItemDto;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.commond.BasePlayerDto;
import org.zgl.dto.server.HallServerPlayerDto;
import org.zgl.executer.pool.Worker;
import org.zgl.hall.socket.WorkerInit;
import org.zgl.service.server.commond.PlayerWeathUpdate;
import org.zgl.service.server.db.GiftHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.zgl.type.GiftType.*;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：
 */
public class Player {
    private long uid;
    private String userName;
    private String headImgUrl;
    private String sex;
    private int vipLv;
    private long gold;
    private int scenesId;
    private int trumpetCard;
    /**
     * 鲜花
     */
    private long flower;
    /**
     * 鸡蛋
     */
    private long egg;
    /**
     * 炸弹
     */
    private long bomb;
    /**
     * 钻戒
     */
    private int diamondRing;
    /**
     * 跑车
     */
    private int sportsCar;
    /**
     * 城堡
     */
    private int tower;
    /**
     * 轮船
     */
    private int streamer;
    /**
     * 飞机
     */
    private int aircraft;
    /**
     * 蓝色妖姬
     */
    private int blueSuccubus;
    private List<BasePlayerDto> friend;
    private final Worker worker;

    public Player() {
        worker = WorkerInit.getInstance().getPool().nextWorker();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getScenesId() {
        return scenesId;
    }

    public void setScenesId(int scenesId) {
        this.scenesId = scenesId;
    }

    public long getUid() {
        return uid;
    }

    public List<BasePlayerDto> getFriend() {
        return friend;
    }

    public void setFriend(List<BasePlayerDto> friend) {
        this.friend = friend;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public int getTrumpetCard() {
        return trumpetCard;
    }

    public void setTrumpetCard(int trumpetCard) {
        this.trumpetCard = trumpetCard;
    }

    public long getFlower() {
        return flower;
    }

    public void setFlower(long flower) {
        this.flower = flower;
    }

    public long getEgg() {
        return egg;
    }

    public void setEgg(long egg) {
        this.egg = egg;
    }

    public long getBomb() {
        return bomb;
    }

    public void setBomb(long bomb) {
        this.bomb = bomb;
    }

    public int getDiamondRing() {
        return diamondRing;
    }

    public void setDiamondRing(int diamondRing) {
        this.diamondRing = diamondRing;
    }

    public int getSportsCar() {
        return sportsCar;
    }

    public void setSportsCar(int sportsCar) {
        this.sportsCar = sportsCar;
    }

    public int getTower() {
        return tower;
    }

    public void setTower(int tower) {
        this.tower = tower;
    }

    public int getStreamer() {
        return streamer;
    }

    public void setStreamer(int streamer) {
        this.streamer = streamer;
    }

    public int getAircraft() {
        return aircraft;
    }

    public void setAircraft(int aircraft) {
        this.aircraft = aircraft;
    }

    public int getBlueSuccubus() {
        return blueSuccubus;
    }

    public void setBlueSuccubus(int blueSuccubus) {
        this.blueSuccubus = blueSuccubus;
    }

    public Player getPlayer(HallServerPlayerDto dto) {
        this.uid = dto.getUid();
        this.gold = dto.getGold();
        this.vipLv = dto.getVipLv();
        this.userName = dto.getUserName();
        this.headImgUrl = dto.getHeadImgUrl();
        this.friend = dto.getFriends();
        this.flower = dto.getFlower();
        this.egg = dto.getEgg();
        this.bomb = dto.getBomb();
        this.diamondRing = dto.getDiamondRing();
        this.sportsCar = dto.getSportsCar();
        this.tower = dto.getTower();
        this.streamer = dto.getStreamer();
        this.aircraft = dto.getAircraft();
        this.blueSuccubus = dto.getBlueSuccubus();
        this.sex = dto.getSex();
        return this;
    }

    public boolean hasFriend(long targetUid) {
        Iterator<BasePlayerDto> iterator = friend.iterator();
        while (iterator.hasNext()) {
            BasePlayerDto b = iterator.next();
            if (b.getUid() == targetUid) {
                return true;
            }
        }
        return false;
    }

    public void addFriend(BasePlayerDto b) {
        friend.add(b);
    }

    public boolean reduceGold(long gold) {
        if (this.gold < gold) {
            return false;
        }
        PlayerWeathUpdate weath = HttpProxyOutboundHandler.createProxy(PlayerWeathUpdate.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        ItemDto dto = weath.checkBet(this.uid, this.scenesId, gold);
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

    public void insertGift(int id, int count, long charm) {
        GiftHandler handler = HttpProxyOutboundHandler.createProxy(GiftHandler.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        ItemDto dto = handler.insertGift(this.uid, id, count, charm);
        long resultCount = dto.getItemCount();
        switch (id) {
            case FLOWER:
                this.flower = resultCount;
                break;
            case EGG:
                this.egg = resultCount;
                break;
            case BOMB:
                this.bomb = resultCount;
                break;
            case DIAMOND_RING:
                this.diamondRing = (int) resultCount;
                break;
            case SPORTS_CAR:
                this.sportsCar = (int) resultCount;
                break;
            case TOWER:
                this.tower = (int) resultCount;
                break;
            case STREAMER:
                this.streamer = (int) resultCount;
                break;
            case AIRCRAFT:
                this.aircraft = (int) resultCount;
                break;
            case BLUE_SUCCUBUS:
                this.blueSuccubus = (int) resultCount;
                break;
            default:
                break;
        }
    }

    public void submit(Runnable r) {
        worker.registerNewChannelTask(r);
    }
}
