package org.zgl.hall.logic.pay;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：充值缓存
 */
public class PayCacheModel {
    private final long uid;
    private final String sigin;
    /**
     * 订单号
     */
    private final long fromNumber;
    private final int productId;
    private final long createTime;

    public PayCacheModel(long uid, String sigin, long fromNumber, int productId) {
        this.uid = uid;
        this.createTime = System.currentTimeMillis();
        this.sigin = sigin;
        this.fromNumber = fromNumber;
        this.productId = productId;
    }

    public long getUid() {
        return uid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getSigin() {
        return sigin;
    }

    public long getFromNumber() {
        return fromNumber;
    }

    public int getProductId() {
        return productId;
    }
}
