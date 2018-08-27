package org.zgl.dto.clinet.db;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/31
 * @文件描述：玩家登陆信息
 */
@Protostuff("db")
public class LoginDto implements SerializeMessage {
    private long uid;
    private long gold;
    private long diamond;
    private long integral;
    private String userName;
    private String sex;
    private int vipLv;
    private long vipExp;
    private String headImgUrl;
    private boolean isDialAward;
    /**签到轮盘能否签到*/
    private int signInDialNum;
    /**当天是否已经领取*/
    private boolean isGiftAward;
    /**签到天数*/
    private int signInDay;
    /**今天是否签到*/
    private boolean isSignIn;
    /**当前使用座驾*/
    private int useAutoId;
    private String secretKey;
    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public long getDiamond() {
        return diamond;
    }

    public void setDiamond(long diamond) {
        this.diamond = diamond;
    }

    public long getIntegral() {
        return integral;
    }

    public void setIntegral(long integral) {
        this.integral = integral;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public long getVipExp() {
        return vipExp;
    }

    public void setVipExp(long vipExp) {
        this.vipExp = vipExp;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isDialAward() {
        return isDialAward;
    }

    public void setDialAward(boolean dialAward) {
        this.isDialAward = dialAward;
    }

    public int getSignInDialNum() {
        return signInDialNum;
    }

    public void setSignInDialNum(int signInDialNum) {
        this.signInDialNum = signInDialNum;
    }

    public boolean isGiftAward() {
        return isGiftAward;
    }

    public void setGiftAward(boolean giftAward) {
        this.isGiftAward = giftAward;
    }

    public int getSignInDay() {
        return signInDay;
    }

    public void setSignInDay(int signInDay) {
        this.signInDay = signInDay;
    }

    public boolean isSignIn() {
        return isSignIn;
    }

    public void setSignIn(boolean signIn) {
        this.isSignIn = signIn;
    }

    public int getUseAutoId() {
        return useAutoId;
    }

    public void setUseAutoId(int useAutoId) {
        this.useAutoId = useAutoId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
