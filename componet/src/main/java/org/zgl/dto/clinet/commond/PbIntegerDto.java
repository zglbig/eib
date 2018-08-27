package org.zgl.dto.clinet.commond;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/30
 * @文件描述：pb的int包装类
 */
@Protostuff("commond")
public class PbIntegerDto implements SerializeMessage {
    private int value;

    public PbIntegerDto(int value) {
        this.value = value;
    }

    public PbIntegerDto() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
