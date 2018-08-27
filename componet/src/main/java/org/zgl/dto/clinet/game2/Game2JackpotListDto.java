package org.zgl.dto.clinet.game2;


import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @作者： big
 * @创建时间： 2018/6/21
 * @文件描述：
 */
@Protostuff("game2")
public class Game2JackpotListDto implements SerializeMessage {
    private List<Game2JackpotDto> jackpotList;

    public Game2JackpotListDto() {
    }

    public Game2JackpotListDto(List<Game2JackpotDto> jackpotList) {
        this.jackpotList = jackpotList;
    }

    public List<Game2JackpotDto> getJackpotList() {
        return jackpotList;
    }

    public void setJackpotList(List<Game2JackpotDto> jackpotList) {
        this.jackpotList = jackpotList;
    }
}
