package org.zgl.dto;

import org.zgl.build.desc.Protostuff;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/6
 * @文件描述：
 */
@Protostuff("commond")
public class ItemDto {
    private int itemId;
    private long itemCount;

    public ItemDto(int itemId, long itemCount) {
        this.itemId = itemId;
        this.itemCount = itemCount;
    }

    public ItemDto() {
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public long getItemCount() {
        return itemCount;
    }

    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
    }
}
