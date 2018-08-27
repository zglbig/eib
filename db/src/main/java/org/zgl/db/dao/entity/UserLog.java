package org.zgl.db.dao.entity;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public class UserLog {
    private Integer id;
    private Long uid;
    private Long holdGold;
    private Long exchangeGold;
    private String userName;
    private Integer operationTime;
    private String operationTimeStr;
    private Integer position;
    private String card;
    private String cardType;
    private String operationDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Integer operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationTimeStr() {
        return operationTimeStr;
    }

    public void setOperationTimeStr(String operationTimeStr) {
        this.operationTimeStr = operationTimeStr;
    }

    public Integer getPosition() {
        return position;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }

    public Long getHoldGold() {
        return holdGold;
    }

    public void setHoldGold(Long holdGold) {
        this.holdGold = holdGold;
    }

    public Long getExchangeGold() {
        return exchangeGold;
    }

    public void setExchangeGold(Long exchangeGold) {
        this.exchangeGold = exchangeGold;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
