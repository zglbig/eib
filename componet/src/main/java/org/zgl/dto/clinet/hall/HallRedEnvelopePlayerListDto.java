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
public class HallRedEnvelopePlayerListDto implements SerializeMessage {
    private List<HallRedEnvelopePlayerDto> redList;

    public HallRedEnvelopePlayerListDto() {
    }

    public HallRedEnvelopePlayerListDto(List<HallRedEnvelopePlayerDto> redList) {
        this.redList = redList;
    }

    public List<HallRedEnvelopePlayerDto> getRedList() {
        return redList;
    }

    public void setRedList(List<HallRedEnvelopePlayerDto> redList) {
        this.redList = redList;
    }
}
