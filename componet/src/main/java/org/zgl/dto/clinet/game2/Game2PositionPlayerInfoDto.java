package org.zgl.dto.clinet.game2;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Protostuff("game2")
public class Game2PositionPlayerInfoDto implements SerializeMessage,Comparable<Game2PositionPlayerInfoDto> {
    private long uid;
    private String userName;
    private String headUrl;
    private int vipLv;
    private long gold;
    private int position;
    private int useAutoId;
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

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getVipLv() {
        return vipLv;
    }

    public void setVipLv(int vipLv) {
        this.vipLv = vipLv;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getUseAutoId() {
        return useAutoId;
    }

    public void setUseAutoId(int useAutoId) {
        this.useAutoId = useAutoId;
    }

    @Override
    public int compareTo(Game2PositionPlayerInfoDto o) {
        long temp = o.getGold()- this.gold;
        if(temp > 0){
            return 1;
        }else if(temp == 0){
            return 0;
        }else if(temp < 0){
            return -1;
        }
        return 0;
    }
}
