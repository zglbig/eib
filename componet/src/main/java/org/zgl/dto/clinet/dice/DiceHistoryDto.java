package org.zgl.dto.clinet.dice;

import org.zgl.build.desc.Protostuff;

import java.util.List;

@Protostuff("dice")
public class DiceHistoryDto {
    List<DiceCountDto> diceCountDtos;

    public DiceHistoryDto() {
    }

    public DiceHistoryDto(List<DiceCountDto> diceCountDtos) {

        this.diceCountDtos = diceCountDtos;
    }

    public List<DiceCountDto> getDiceCountDtos() {
        return diceCountDtos;
    }

    public void setDiceCountDtos(List<DiceCountDto> diceCountDtos) {
        this.diceCountDtos = diceCountDtos;
    }
}