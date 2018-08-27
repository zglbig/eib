package org.zgl.db.dao.entity;

import org.zgl.db.dao.mapper.PropMapper;
import org.zgl.db.utils.SpringUtils;
import org.zgl.dto.ItemDto;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.zgl.type.GiftType.*;
import static org.zgl.type.GiftType.AIRCRAFT;
import static org.zgl.type.GiftType.BLUE_SUCCUBUS;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
public class Prop {
    private Integer id;
    private Long uid;
    /**
     * 换牌卡
     */
    private Integer exchangeCard;
    /**
     * 踢人卡
     */
    private Integer kickingCard;
    /**
     * 喇叭卡
     */
    private Integer trumpetCard;
    /**
     * 鲜花
     */
    private Long flower;
    /**
     * 鸡蛋
     */
    private Long egg;
    /**
     * 炸弹
     */
    private Long bomb;
    /**
     * 钻戒
     */
    private Integer diamondRing;
    /**
     * 跑车
     */
    private Integer sportsCar;
    /**
     * 城堡
     */
    private Integer tower;
    /**
     * 轮船
     */
    private Integer streamer;
    /**
     * 飞机
     */
    private Integer aircraft;
    /**
     * 蓝色妖姬
     */
    private Integer blueSuccubus;
    /**
     * 10元话费
     */
    private Integer telephoneCharge10;
    /**
     * 50元话费
     */
    private Integer telephoneCharge50;
    /**
     * 100元话费
     */
    private Integer telephoneCharge100;
    /**
     * 苹果x
     */
    private Integer iphoneX;
    /**
     * 苹果7p
     */
    private Integer ipone7p;
    /**
     * 小米2s
     */
    private Integer mix2s;
    /**
     * 拓展类
     */
    private String extend;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public void init() {
        this.id = 0;
        this.uid = 0L;
        this.exchangeCard = 0;
        this.kickingCard = 0;
        this.trumpetCard = 0;
        this.flower = 0L;
        this.egg = 0L;
        this.bomb = 0L;
        this.diamondRing = 0;
        this.sportsCar = 0;
        this.tower = 0;
        this.streamer = 0;
        this.aircraft = 0;
        this.blueSuccubus = 0;
        this.telephoneCharge10 = 0;
        this.telephoneCharge50 = 0;
        this.telephoneCharge100 = 0;
        this.iphoneX = 0;
        this.ipone7p = 0;
        this.mix2s = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getExtend() {
        return extend;
    }

    public Integer getExchangeCard() {
        return exchangeCard;
    }

    public void setExchangeCard(Integer exchangeCard) {
        this.exchangeCard = exchangeCard;
    }

    public Integer getKickingCard() {
        return kickingCard;
    }

    public void setKickingCard(Integer kickingCard) {
        this.kickingCard = kickingCard;
    }

    public Integer getTrumpetCard() {
        return trumpetCard;
    }

    public void setTrumpetCard(Integer trumpetCard) {
        this.trumpetCard = trumpetCard;
    }

    public Long getFlower() {
        return flower;
    }

    public void setFlower(Long flower) {
        this.flower = flower;
    }

    public Long getEgg() {
        return egg;
    }

    public void setEgg(Long egg) {
        this.egg = egg;
    }

    public Long getBomb() {
        return bomb;
    }

    public void setBomb(Long bomb) {
        this.bomb = bomb;
    }

    public Integer getDiamondRing() {
        return diamondRing;
    }

    public void setDiamondRing(Integer diamondRing) {
        this.diamondRing = diamondRing;
    }

    public Integer getSportsCar() {
        return sportsCar;
    }

    public void setSportsCar(Integer sportsCar) {
        this.sportsCar = sportsCar;
    }

    public Integer getTower() {
        return tower;
    }

    public void setTower(Integer tower) {
        this.tower = tower;
    }

    public Integer getStreamer() {
        return streamer;
    }

    public void setStreamer(Integer streamer) {
        this.streamer = streamer;
    }

    public Integer getAircraft() {
        return aircraft;
    }

    public void setAircraft(Integer aircraft) {
        this.aircraft = aircraft;
    }

    public Integer getBlueSuccubus() {
        return blueSuccubus;
    }

    public void setBlueSuccubus(Integer blueSuccubus) {
        this.blueSuccubus = blueSuccubus;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public Integer getTelephoneCharge10() {
        return telephoneCharge10;
    }

    public void setTelephoneCharge10(Integer telephoneCharge10) {
        this.telephoneCharge10 = telephoneCharge10;
    }

    public Integer getTelephoneCharge50() {
        return telephoneCharge50;
    }

    public void setTelephoneCharge50(Integer telephoneCharge50) {
        this.telephoneCharge50 = telephoneCharge50;
    }

    public Integer getTelephoneCharge100() {
        return telephoneCharge100;
    }

    public void setTelephoneCharge100(Integer telephoneCharge100) {
        this.telephoneCharge100 = telephoneCharge100;
    }

    public Integer getIphoneX() {
        return iphoneX;
    }

    public void setIphoneX(Integer iphoneX) {
        this.iphoneX = iphoneX;
    }

    public Integer getIpone7p() {
        return ipone7p;
    }

    public void setIpone7p(Integer ipone7p) {
        this.ipone7p = ipone7p;
    }

    public Integer getMix2s() {
        return mix2s;
    }

    public void setMix2s(Integer mix2s) {
        this.mix2s = mix2s;
    }

    public ItemDto insertProp(int id, int count) {
        this.lock.writeLock().lock();
        try {
            ItemDto dto = new ItemDto();
            dto.setItemId(id);
            switch (id) {
                case FLOWER:
                    this.flower += count;
                    dto.setItemCount(this.flower);
                    break;
                case EGG:
                    this.egg += count;
                    dto.setItemCount(this.egg);
                    break;
                case BOMB:
                    this.bomb += count;
                    dto.setItemCount(this.bomb);
                    break;
                case DIAMOND_RING:
                    this.diamondRing += count;
                    dto.setItemCount(this.diamondRing);
                    break;
                case SPORTS_CAR:
                    this.sportsCar += count;
                    dto.setItemCount(this.sportsCar);
                    break;
                case TOWER:
                    this.tower += count;
                    dto.setItemCount(this.tower);
                    break;
                case STREAMER:
                    this.streamer += count;
                    dto.setItemCount(this.streamer);
                    break;
                case AIRCRAFT:
                    this.aircraft += count;
                    dto.setItemCount(this.aircraft);
                    break;
                case BLUE_SUCCUBUS:
                    this.blueSuccubus += count;
                    dto.setItemCount(this.blueSuccubus);
                    break;
                default:
                    break;
            }
            return dto;
        }finally {
            this.lock.writeLock().unlock();
        }
    }

    public ItemDto reduceProp(int id, int count) {
        return insertProp(id, -count);
    }

    public boolean checkProp(int id, int count) {
        this.lock.writeLock().lock();
        try {
            switch (id) {
                case FLOWER:
                    return this.flower >= count;
                case EGG:
                    return this.egg >= count;
                case BOMB:
                    return this.bomb >= count;
                case DIAMOND_RING:
                    return this.diamondRing >= count;
                case SPORTS_CAR:
                    return this.sportsCar >= count;
                case TOWER:
                    return this.tower >= count;
                case STREAMER:
                    return this.streamer >= count;
                case AIRCRAFT:
                    return this.aircraft >= count;
                case BLUE_SUCCUBUS:
                    return this.blueSuccubus >= count;
                default:
                    return false;
            }
        }finally {
            this.lock.writeLock().unlock();
        }
    }
    public boolean updateProp(Prop prop){
        PropMapper mapper = SpringUtils.getBean(PropMapper.class);
        return mapper.updateProp(prop) == 1;
    }
    public boolean updateProp(){
        return updateProp(this);
    }
}
