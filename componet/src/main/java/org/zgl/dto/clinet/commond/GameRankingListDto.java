package org.zgl.dto.clinet.commond;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/22
 * @文件描述：
 */
@Protostuff("commond")
public class GameRankingListDto implements SerializeMessage {
    private List<GameRankingDto> listDto;

    public GameRankingListDto() {
    }

    public GameRankingListDto(List<GameRankingDto> listDto) {
        this.listDto = listDto;
    }

    public List<GameRankingDto> getListDto() {
        return listDto;
    }

    public void setListDto(List<GameRankingDto> listDto) {
        this.listDto = listDto;
    }
}
