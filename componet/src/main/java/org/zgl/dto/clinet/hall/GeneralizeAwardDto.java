package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@Protostuff("hall")
public class GeneralizeAwardDto implements SerializeMessage {
    private long selfGetGold;
    private long selfHoldGold;
    private long otherGetGold;
    private long otherHoldGold;

    public long getSelfGetGold() {
        return selfGetGold;
    }

    public void setSelfGetGold(long selfGetGold) {
        this.selfGetGold = selfGetGold;
    }

    public long getSelfHoldGold() {
        return selfHoldGold;
    }

    public void setSelfHoldGold(long selfHoldGold) {
        this.selfHoldGold = selfHoldGold;
    }

    public long getOtherGetGold() {
        return otherGetGold;
    }

    public void setOtherGetGold(long otherGetGold) {
        this.otherGetGold = otherGetGold;
    }

    public long getOtherHoldGold() {
        return otherHoldGold;
    }

    public void setOtherHoldGold(long otherHoldGold) {
        this.otherHoldGold = otherHoldGold;
    }
}
