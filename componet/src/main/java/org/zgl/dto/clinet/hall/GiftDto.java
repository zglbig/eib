package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@Protostuff("db")
public class GiftDto implements SerializeMessage {
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
}
