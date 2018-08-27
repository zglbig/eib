package org.zgl.datable;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Protostuff("commond")
public class DatableModelListDto implements SerializeMessage {
    private List<DatableModelDto> msgList;

    public DatableModelListDto() {
    }

    public DatableModelListDto(List<DatableModelDto> msgList) {
        this.msgList = msgList;
    }

    public List<DatableModelDto> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<DatableModelDto> msgList) {
        this.msgList = msgList;
    }
}
