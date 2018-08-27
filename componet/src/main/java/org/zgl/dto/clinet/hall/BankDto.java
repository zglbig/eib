package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@Protostuff("hall")
public class BankDto implements SerializeMessage {
    private long exchangeGold;
    private long holdGold;
    private long bankResiduceGold;
    public BankDto() {
    }

    public BankDto(long exchangeGold, long holdGold, long bankResiduceGold) {
        this.exchangeGold = exchangeGold;
        this.holdGold = holdGold;
        this.bankResiduceGold = bankResiduceGold;
    }

    public long getBankResiduceGold() {
        return bankResiduceGold;
    }

    public void setBankResiduceGold(long bankResiduceGold) {
        this.bankResiduceGold = bankResiduceGold;
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
