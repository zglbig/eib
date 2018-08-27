package org.zgl.dto.clinet.hall;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：
 */
@Protostuff("hall")
public class HallRedPacketListDto implements SerializeMessage {
    private List<HallRedPacketDto> redList;

    public HallRedPacketListDto() {
    }

    public HallRedPacketListDto(List<HallRedPacketDto> redList) {
        this.redList = redList;
    }

    public List<HallRedPacketDto> getRedList() {
        return redList;
    }

    public void setRedList(List<HallRedPacketDto> redList) {
        this.redList = redList;
    }
}
