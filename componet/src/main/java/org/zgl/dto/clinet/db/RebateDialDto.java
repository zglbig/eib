package org.zgl.dto.clinet.db;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.ItemDto;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@Protostuff("db")
public class RebateDialDto implements SerializeMessage {
    private int position;
    private int awardId;
    private List<ItemDto> items;

    public RebateDialDto() {
    }

    public RebateDialDto(int position, int awardId, List<ItemDto> items) {
        this.position = position;
        this.awardId = awardId;
        this.items = items;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getAwardId() {
        return awardId;
    }

    public void setAwardId(int awardId) {
        this.awardId = awardId;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}
