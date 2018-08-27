package org.zgl.dto.clinet.commond;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

@Protostuff("commond")
public class CardsDto implements SerializeMessage {
    private int cardType;
    private List<Integer> cardIds;
    public CardsDto() {
    }

    public CardsDto(int cardType, List<Integer> cardIds) {
        this.cardType = cardType;
        this.cardIds = cardIds;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public List<Integer> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<Integer> cardIds) {
        this.cardIds = cardIds;
    }

}