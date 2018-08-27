package org.zgl.db.dao.entity;

import org.zgl.dto.clinet.commond.BasePlayerDto;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
public class Friends {
    private Integer id;
    private Long uid;
    private Long friendUid;
    private String userName;
    private String headUrl;
    private Integer vipLv;
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

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public Long getFriendUid() {
        return friendUid;
    }

    public void setFriendUid(Long friendUid) {
        this.friendUid = friendUid;
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
    public BasePlayerDto basePlayer(){
        BasePlayerDto dto = new BasePlayerDto();
        dto.setVipLv(this.vipLv);
        dto.setHeadImgUrl(this.headUrl);
        dto.setUserName(this.userName);
        dto.setUid(this.uid);
        return dto;
    }
}
