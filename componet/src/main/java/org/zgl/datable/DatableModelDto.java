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
public class DatableModelDto implements SerializeMessage {
    private String objType;
    private List<String> jsonMsg;

    public DatableModelDto() {
    }

    public DatableModelDto(String objType, List<String> jsonMsg) {
        this.objType = objType;
        this.jsonMsg = jsonMsg;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public List<String> getJsonMsg() {
        return jsonMsg;
    }

    public void setJsonMsg(List<String> jsonMsg) {
        this.jsonMsg = jsonMsg;
    }
}
