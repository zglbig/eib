package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@Protostuff("hall")
public class PawnshopDto implements SerializeMessage {
    /**当铺获得多少金币*/
    private long acquireGold;
    /**当前持有金币*/
    private long holdGold;
    /**当前持有该物品id*/
    private int productId;
    /**当前持有该物品数量*/
    private int productHoldCount;

    public PawnshopDto() {
    }

    public long getAcquireGold() {
        return acquireGold;
    }

    public void setAcquireGold(long acquireGold) {
        this.acquireGold = acquireGold;
    }

    public long getHoldGold() {
        return holdGold;
    }

    public void setHoldGold(long holdGold) {
        this.holdGold = holdGold;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductHoldCount() {
        return productHoldCount;
    }

    public void setProductHoldCount(int productHoldCount) {
        this.productHoldCount = productHoldCount;
    }

    public PawnshopDto(long acquireGold, long holdGold, int productId, int productHoldCount) {
        this.acquireGold = acquireGold;
        this.holdGold = holdGold;
        this.productId = productId;
        this.productHoldCount = productHoldCount;
    }
}
