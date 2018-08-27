package org.zgl.db.dao.entity;

import org.zgl.DateUtils;
import org.zgl.db.dao.mapper.RebateDialMapper;
import org.zgl.db.utils.SpringUtils;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/22
 * @文件描述：返利轮盘
 */
public class RebateDial {
    public static final int DIAL_COUNT_LIMIT = 100000000;
    private Long uid;
    private Integer today;
    private Long topUp;
    private Long dice;
    private Long toRoom;
    private Long lottery;
    private Long goldAward;
    private Long topUpAward;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getToday() {
        return today;
    }

    public void setToday(Integer today) {
        this.today = today;
    }

    public Long getTopUp() {
        return topUp;
    }

    public void setTopUp(Long topUp) {
        this.topUp = topUp;
    }

    public Long getDice() {
        return dice;
    }

    public void setDice(Long dice) {
        this.dice = dice;
    }

    public Long getToRoom() {
        return toRoom;
    }

    public void setToRoom(Long toRoom) {
        this.toRoom = toRoom;
    }

    public Long getLottery() {
        return lottery;
    }

    public void setLottery(Long lottery) {
        this.lottery = lottery;
    }

    public Long getGoldAward() {
        return goldAward;
    }

    public void setGoldAward(Long goldAward) {
        this.goldAward = goldAward;
    }

    public Long getTopUpAward() {
        return topUpAward;
    }

    public void setTopUpAward(Long topUpAward) {
        this.topUpAward = topUpAward;
    }
    public  void reset(){
        if(this.today != DateUtils.currentDay()){
            this.today = DateUtils.currentDay();
            this.topUp = 0L;
            this.dice = 0L;
            this.toRoom = 0L;
            this.lottery = 0L;
            this.goldAward = 0L;
            this.topUpAward = 0L;
        }
    }
    private int dialTopUpNum() {
        int count = (int) (topUp / 20);
        int result = (int) (count - topUpAward);
        return result > 0 ? result : 0;
    }
    /**
     * 金币剩余次数
     *
     * @return
     */
    private int dialGoldCount() {
        long allBet = toRoom + lottery + dice;
        int count = (int) (allBet / DIAL_COUNT_LIMIT);
        int result = (int) (count - goldAward);
        return result > 0 ? result : 0;
    }
    /**
     * 还可以领取的次数
     *
     * @return
     */
    public int awardCount() {
        return dialTopUpNum() + dialGoldCount();
    }
    public int luckyDrawed(){
        return (int) (topUpAward+goldAward);
    }
    /**
     * 能否领取奖励
     *
     * @return
     */
    public boolean canAward() {
        long allBet = toRoom + lottery + dice;
        int count = (int) (allBet / DIAL_COUNT_LIMIT);
        if (count - goldAward > 0) {
            return true;
        }
        int allTopUpCount = (int) (topUp / 20);
        if (allTopUpCount - topUpAward > 0) {
            return true;
        }
        return false;
    }
    /**
     * 领取
     *
     * @return
     */
    public void award() {
        if (dialGoldCount() > 0) {
            goldAward++;
        } else if (dialTopUpNum() > 0) {
            topUp++;
        }
    }
    public boolean update(){
        RebateDialMapper mapper = SpringUtils.getBean(RebateDialMapper.class);
        return mapper.updateRebateDial(this) == 1;
    }
}
