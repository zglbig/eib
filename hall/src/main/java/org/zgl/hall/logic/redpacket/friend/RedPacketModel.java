package org.zgl.hall.logic.redpacket.friend;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public class RedPacketModel {
    /**
     * 红包id
     */
    private final long id;
    /**
     * 发红包人的uid
     */
    private final long uid;
    /**
     * 发红包人的用户名
     */
    private final String userName;
    /**
     * 领好包人的uid
     */
    private final long targetUid;
    private final String targetUserName;
    /**
     * 发了多少钱
     */
    private final long gold;
    private final long createTime;

    public RedPacketModel(long uid, String userName, long targetUid, String targetUserName, long gold) {
        this.uid = uid;
        this.userName = userName;
        this.targetUserName = targetUserName;
        this.targetUid = targetUid;
        this.gold = gold;
        this.id = System.currentTimeMillis();
        this.createTime = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public long getUid() {
        return uid;
    }

    public String getUserName() {
        return userName;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public long getTargetUid() {
        return targetUid;
    }

    public long getGold() {
        return gold;
    }

    public long getCreateTime() {
        return createTime;
    }
}
