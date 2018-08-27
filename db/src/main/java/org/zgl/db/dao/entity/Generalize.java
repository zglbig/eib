package org.zgl.db.dao.entity;
/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
public class Generalize {
    private Integer num;
    private Long selfUid;
    private Long targetUid;
    private Long award;
    private Long allAward;
    private String targetUserName;
    private String createTime;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getSelfUid() {
        return selfUid;
    }

    public void setSelfUid(Long selfUid) {
        this.selfUid = selfUid;
    }

    public Long getTargetUid() {
        return targetUid;
    }

    public void setTargetUid(Long targetUid) {
        this.targetUid = targetUid;
    }

    public Long getAward() {
        return award;
    }

    public void setAward(Long award) {
        this.award = award;
    }

    public Long getAllAward() {
        return allAward;
    }

    public void setAllAward(Long allAward) {
        this.allAward = allAward;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
