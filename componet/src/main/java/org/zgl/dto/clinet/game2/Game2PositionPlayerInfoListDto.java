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
public class Game2PositionPlayerInfoListDto implements SerializeMessage {
    private List<Game2PositionPlayerInfoDto> list;
    public Game2PositionPlayerInfoListDto() {
    }
    public Game2PositionPlayerInfoListDto(List<Game2PositionPlayerInfoDto> list) {
        this.list = list;
    }
    public List<Game2PositionPlayerInfoDto> getList() {
        return list;
    }
    public void setList(List<Game2PositionPlayerInfoDto> list) {
        this.list = list;
    }
}
