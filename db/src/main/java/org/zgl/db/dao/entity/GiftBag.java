package org.zgl.db.dao.entity;

import org.zgl.DateUtils;
import org.zgl.datable.GiftBagAwardDataTable;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：成长礼包
 */
public class GiftBag {
    private Long uid;
    /**登陆第几天*/
    private Integer dayCount;
    /**上次登陆时间*/
    private Integer lastLoginTime;
    /**初级场*/
    private Integer primaryCount;
    /**中级场*/
    private Integer intermedite;
    /**高级场*/
    private Integer advanced;
    /**是否完成*/
    private Boolean isDone;
    /**是否已经领取*/
    private Boolean isAward;
    private String extend;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Integer lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getPrimaryCount() {
        return primaryCount;
    }

    public void setPrimaryCount(Integer primaryCount) {
        this.primaryCount = primaryCount;
    }

    public Integer getIntermedite() {
        return intermedite;
    }

    public void setIntermedite(Integer intermedite) {
        this.intermedite = intermedite;
    }

    public Integer getAdvanced() {
        return advanced;
    }

    public void setAdvanced(Integer advanced) {
        this.advanced = advanced;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Boolean getAward() {
        return isAward;
    }

    public void setAward(Boolean award) {
        isAward = award;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public void reset(){
        this.dayCount = 1;
        this.lastLoginTime = DateUtils.currentDay();
        this.primaryCount = 0;
        this.intermedite = 0;
        this.advanced = 0;
        this.isAward = false;
        this.isDone = false;
    }
    /**
     * 根据限制产看任务是否完成
     * @return
     */
    public boolean checkTaskHasDone(){
        GiftBagAwardDataTable dataTable = GiftBagAwardDataTable.get(dayCount);
        if(dataTable == null){
            return false;
        }
        if(dataTable.getPrimary() <= this.primaryCount
                && dataTable.getAdvanced() <= this.advanced
                && dataTable.getIntermediate() <= this.intermedite){
            this.isDone = true;
            return true;
        }
        return false;
    }
}
