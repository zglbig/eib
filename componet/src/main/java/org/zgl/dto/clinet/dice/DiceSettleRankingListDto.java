package org.zgl.dto.clinet.dice;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
@Protostuff("dice")
public class DiceSettleRankingListDto implements SerializeMessage {
    List<DiceSettleRankingDto> dtos;

    public DiceSettleRankingListDto() {
    }

    public DiceSettleRankingListDto(List<DiceSettleRankingDto> dtos) {
        this.dtos = dtos;
    }

    public List<DiceSettleRankingDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<DiceSettleRankingDto> dtos) {
        this.dtos = dtos;
    }
}
