package org.zgl.dto.clinet.game1;


import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

@Protostuff("game1")
public class Game1PlayerRoomBaseInfoDto implements SerializeMessage {
    private long uid;
    private String userName;
    private long gold;
    private String headIcon;
    private int vipLv;
    private long bottomNum;
    private boolean hasReady;
    private int postion;
    private int autoId;
    private String sex;
    private List<Integer> cardId;
    private long nowBetAll;
    private int nowChip;
    public Game1PlayerRoomBaseInfoDto() {
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getBottomNum() {
        return bottomNum;
    }

    public void setBottomNum(long bottomNum) {
        this.bottomNum = bottomNum;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public boolean isHasReady() {
        return hasReady;
    }

    public void setHasReady(boolean hasReady) {
        this.hasReady = hasReady;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public int getNowChip() {
        return nowChip;
    }

    public void setNowChip(int nowChip) {
        this.nowChip = nowChip;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public List<Integer> getCardId() {
        return cardId;
    }

    public void setCardId(List<Integer> cardId) {
        this.cardId = cardId;
    }

    public long getNowBetAll() {
        return nowBetAll;
    }

    public void setNowBetAll(long nowBetAll) {
        this.nowBetAll = nowBetAll;
    }
}
