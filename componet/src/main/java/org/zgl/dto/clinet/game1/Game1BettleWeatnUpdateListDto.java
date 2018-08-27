package org.zgl.dto.clinet.game1;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */
@Protostuff("game1")
public class Game1BettleWeatnUpdateListDto implements SerializeMessage {
    private List<Game1BettleWeatnUpdateDto> updateList;

    public Game1BettleWeatnUpdateListDto(List<Game1BettleWeatnUpdateDto> updateList) {
        this.updateList = updateList;
    }

    public Game1BettleWeatnUpdateListDto() {
    }

    public List<Game1BettleWeatnUpdateDto> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<Game1BettleWeatnUpdateDto> updateList) {
        this.updateList = updateList;
    }
}
