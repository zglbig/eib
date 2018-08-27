package org.zgl.db.dao.entity;

import org.zgl.db.dao.mapper.SiginMapper;
import org.zgl.db.utils.SpringUtils;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
public class Sigin {
    private Integer id;
    private Long uid;
    /**签到第几天*/
    private Integer siginDay;
    /**上次签到时间*/
    private Long lastTimeSiginTime;
    /**轮盘总次数*/
    private Integer dialAllNum;
    /**轮盘剩余次数*/
    private Integer dialUseNum;
    private String extend;

    public void init() {
        this.uid = 0L;
        this.siginDay = 0;
        this.lastTimeSiginTime = 0L;
        this.dialAllNum = 0;
        this.dialUseNum = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getSiginDay() {
        return siginDay;
    }

    public void setSiginDay(Integer siginDay) {
        this.siginDay = siginDay;
    }

    public Long getLastTimeSiginTime() {
        return lastTimeSiginTime;
    }

    public void setLastTimeSiginTime(Long lastTimeSiginTime) {
        this.lastTimeSiginTime = lastTimeSiginTime;
    }

    public Integer getDialAllNum() {
        return dialAllNum;
    }

    public void setDialAllNum(Integer dialAllNum) {
        this.dialAllNum = dialAllNum;
    }

    public Integer getDialUseNum() {
        return dialUseNum;
    }

    public void setDialUseNum(Integer dialUseNum) {
        this.dialUseNum = dialUseNum;
    }
    public boolean updateSigin(){
        SiginMapper mapper = SpringUtils.getBean(SiginMapper.class);
        return mapper.updateSigin(this) == 1;
    }
}
