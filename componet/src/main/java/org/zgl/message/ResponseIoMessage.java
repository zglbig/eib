package org.zgl.message;

import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/6
 * @文件描述：
 */
public class ResponseIoMessage implements IoMessage {
    private List<Long> uids;
    private String interfaceName;
    private String methodName;
    private String args;
    public ResponseIoMessage() {
    }

    public ResponseIoMessage(List<Long> uids, String interfaceName, String methodName, String args) {
        this.uids = uids;
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.args = args;
    }

    public List<Long> getUids() {
        return uids;
    }

    public void setUids(List<Long> uids) {
        this.uids = uids;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
}
