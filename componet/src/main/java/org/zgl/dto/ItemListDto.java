package org.zgl.dto;
import org.zgl.build.desc.Protostuff;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */
@Protostuff("commond")
public class ItemListDto implements SerializeMessage {
    private List<ItemDto> items;

    public ItemListDto() {
    }

    public ItemListDto(List<ItemDto> items) {
        this.items = items;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}
