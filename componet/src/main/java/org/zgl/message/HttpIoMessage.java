package org.zgl.message;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/30
 * @文件描述：
 */
@Protostuff("commond")
public class HttpIoMessage implements SerializeMessage {
    private long uid;
    private int resultCode;
    private String msg;

    public HttpIoMessage(long uid, int resultCode, String msg) {
        this.uid = uid;
        this.resultCode = resultCode;
        this.msg = msg;
    }

    public HttpIoMessage() {
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "HttpIoMessage{" +
                "uid=" + uid +
                ", resultCode=" + resultCode +
                ", msg='" + msg + '\'' +
                '}';
    }
}
