package org.zgl.game.logic.room;

import java.util.Arrays;

public class HandCard {
    /**牌型*/
    private int cardType;
    private Integer[] cardFaces;
    private Integer[] cardIds;
    /**最大的牌面（如果是对子就是其中一个）*/
    private int max;
    /**最小的牌面（如果是对子就是散的那个）*/
    private int min;
    /**比较后的结果，输还是赢*/
    private boolean compareResult;
    /**持有手牌的位置*/
    private int position;
    public HandCard() {
    }

    public HandCard(int cardType, Integer[] cardFaces, Integer[] cardIds) {
        this.cardType = cardType;
        this.cardFaces = cardFaces;
        this.cardIds = cardIds;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public Integer[] getCardFaces() {
        return cardFaces;
    }

    public void setCardFaces(Integer[] cardFaces) {
        this.cardFaces = cardFaces;
    }

    public Integer[] getCardIds() {
        return cardIds;
    }

    public void setCardIds(Integer[] cardIds) {
        this.cardIds = cardIds;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public boolean isCompareResult() {
        return compareResult;
    }

    public void setCompareResult(boolean compareResult) {
        this.compareResult = compareResult;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "HandCard{" +
                "cardType=" + cardType +
                ", cardFaces=" + (cardFaces == null ? null : Arrays.asList(cardFaces)) +
                ", cardIds=" + (cardIds == null ? null : Arrays.asList(cardIds)) +
                ", max=" + max +
                ", min=" + min +
                ", compareResult=" + compareResult +
                ", position=" + position +
                '}';
    }
}
