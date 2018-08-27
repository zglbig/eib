package org.zgl.dto.clinet.db;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@Protostuff("db")
public class RankingBaseDto implements SerializeMessage ,Comparable<RankingBaseDto>{
    private long uid;
    private String userName;
    private String headImgUrl;
    private int vipLv;
    /**显示的财富 金币或者人品值*/
    private long showWeath;
    /**当前使用座驾*/
    private int useAutoId;

    public RankingBaseDto() {
    }

    public RankingBaseDto(long uid, String userName, String headImgUrl, int vipLv, long showWeath, int useAutoId) {
        this.uid = uid;
        this.userName = userName;
        this.headImgUrl = headImgUrl;
        this.vipLv = vipLv;
        this.showWeath = showWeath;
        this.useAutoId = useAutoId;
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

    public long getShowWeath() {
        return showWeath;
    }

    public void setShowWeath(long showWeath) {
        this.showWeath = showWeath;
    }

    public int getUseAutoId() {
        return useAutoId;
    }

    public void setUseAutoId(int useAutoId) {
        this.useAutoId = useAutoId;
    }

    @Override
    public int compareTo(RankingBaseDto o) {
        long temp = this.showWeath - o.getShowWeath();
        if(temp > 0){
            return 1;
        }else if(temp == 0){
            return 0;
        }else {
            return -1;
        }
    }
}
