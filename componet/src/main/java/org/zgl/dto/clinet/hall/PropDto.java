package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@Protostuff("db")
public class PropDto implements SerializeMessage {
    /**
     * 换牌卡
     */
    private Integer exchangeCard;
    /**
     * 踢人卡
     */
    private Integer kickingCard;
    /**
     * 喇叭卡
     */
    private Integer trumpetCard;

    public PropDto(Integer exchangeCard, Integer kickingCard, Integer trumpetCard) {
        this.exchangeCard = exchangeCard;
        this.kickingCard = kickingCard;
        this.trumpetCard = trumpetCard;
    }

    public PropDto() {
    }

    public Integer getExchangeCard() {
        return exchangeCard;
    }

    public void setExchangeCard(Integer exchangeCard) {
        this.exchangeCard = exchangeCard;
    }

    public Integer getKickingCard() {
        return kickingCard;
    }

    public void setKickingCard(Integer kickingCard) {
        this.kickingCard = kickingCard;
    }

    public Integer getTrumpetCard() {
        return trumpetCard;
    }

    public void setTrumpetCard(Integer trumpetCard) {
        this.trumpetCard = trumpetCard;
    }
}
