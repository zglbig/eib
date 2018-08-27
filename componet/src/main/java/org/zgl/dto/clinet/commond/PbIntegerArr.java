package org.zgl.dto.clinet.commond;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */
@Protostuff("commond")
public class PbIntegerArr implements SerializeMessage {
    private List<Integer> vale;

    public PbIntegerArr() {
    }

    public PbIntegerArr(List<Integer> vale) {
        this.vale = vale;
    }

    public List<Integer> getVale() {
        return vale;
    }

    public void setVale(List<Integer> vale) {
        this.vale = vale;
    }
}
