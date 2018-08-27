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
public class Game2CardListDto implements SerializeMessage {
    private List<Game2CardDto> cardDtoList;

    public Game2CardListDto() {
    }

    public Game2CardListDto(List<Game2CardDto> cardDtoList) {
        this.cardDtoList = cardDtoList;
    }

    public List<Game2CardDto> getCardDtoList() {
        return cardDtoList;
    }

    public void setCardDtoList(List<Game2CardDto> cardDtoList) {
        this.cardDtoList = cardDtoList;
    }
}
