package org.zgl.dto.clinet.game2;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Protostuff("game2")
public class Game2CardDto implements SerializeMessage {
    /**哪个位置*/
    private int position;
    /**牌型*/
    private int cardType;
    /**输或者赢*/
    private boolean result;
    /**牌的id*/
    private List<Integer> cardIds;

    public Game2CardDto() {
    }

    public Game2CardDto(int position, int cardType, boolean result, List<Integer> cardIds) {
        this.position = position;
        this.cardType = cardType;
        this.result = result;
        this.cardIds = cardIds;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<Integer> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<Integer> cardIds) {
        this.cardIds = cardIds;
    }
}
