package org.zgl.dto.clinet.commond;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@Protostuff("hall")
public class GoldBaseDto implements SerializeMessage {
    private long exchangeGold;
    private long holdGold;

    public GoldBaseDto() {
    }

    public GoldBaseDto(long exchangeGold, long holdGold) {
        this.exchangeGold = exchangeGold;
        this.holdGold = holdGold;
    }

    public long getExchangeGold() {
        return exchangeGold;
    }

    public void setExchangeGold(long exchangeGold) {
        this.exchangeGold = exchangeGold;
    }

    public long getHoldGold() {
        return holdGold;
    }

    public void setHoldGold(long holdGold) {
        this.holdGold = holdGold;
    }
}
