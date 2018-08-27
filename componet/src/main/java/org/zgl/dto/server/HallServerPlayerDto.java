package org.zgl.dto.server;

import org.zgl.dto.SerializeMessage;
import org.zgl.dto.clinet.commond.BasePlayerDto;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */

public class HallServerPlayerDto implements SerializeMessage {
    private long uid;
    private String userName;
    private String headImgUrl;
    private String sex;
    private int vipLv;
    private long gold;
    /**鲜花*/
    private long flower;
    /**鸡蛋*/
    private long egg;
    /**炸弹*/
    private long bomb;
    /**钻戒*/
    private int diamondRing;
    /**跑车*/
    private int sportsCar;
    /**城堡*/
    private int tower;
    /**轮船*/
    private int streamer;
    /**飞机*/
    private int aircraft;
    /**蓝色妖姬*/
    private int blueSuccubus;
    private List<BasePlayerDto> friends;

    public HallServerPlayerDto() {
    }

    public HallServerPlayerDto(long uid, String userName, String headImgUrl, int vipLv, long gold, List<BasePlayerDto> friends) {
        this.uid = uid;
        this.userName = userName;
        this.headImgUrl = headImgUrl;
        this.vipLv = vipLv;
        this.gold = gold;
        this.friends = friends;
    }

    public long getFlower() {
        return flower;
    }

    public void setFlower(long flower) {
        this.flower = flower;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public long getUid() {
        return uid;
    }
    public void setUid(long uid) {
        this.uid = uid;
    }

    public List<BasePlayerDto> getFriends() {
        return friends;
    }

    public void setFriends(List<BasePlayerDto> friends) {
        this.friends = friends;
    }
}
