package org.zgl.db.dao.entity;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：大厅红包这个不需要存在数据库 每次重启都会清空
 */
public class RedPacket {
    private Integer id;
    private Long uid;
    private String userName;
    private String headUrl;
    private Integer vipLv;
    private Integer allNum;
    private Long allGold;
    private Integer drawNum;
    private Long residueGold;
    private Long createTime;
    private String createTimeStr;
    private String extend;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Integer getVipLv() {
        return vipLv;
    }

    public void setVipLv(Integer vipLv) {
        this.vipLv = vipLv;
    }

    public Integer getAllNum() {
        return allNum;
    }

    public void setAllNum(Integer allNum) {
        this.allNum = allNum;
    }

    public Long getAllGold() {
        return allGold;
    }

    public void setAllGold(Long allGold) {
        this.allGold = allGold;
    }

    public Integer getDrawNum() {
        return drawNum;
    }

    public void setDrawNum(Integer drawNum) {
        this.drawNum = drawNum;
    }

    public Long getResidueGold() {
        return residueGold;
    }

    public void setResidueGold(Long residueGold) {
        this.residueGold = residueGold;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
}
