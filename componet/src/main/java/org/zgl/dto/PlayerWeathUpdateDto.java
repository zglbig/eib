package org.zgl.dto;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/6
 * @文件描述：
 */
public class PlayerWeathUpdateDto {
    private List<ItemDto> items;

    public PlayerWeathUpdateDto() {
    }

    public PlayerWeathUpdateDto(List<ItemDto> items) {
        this.items = items;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}
