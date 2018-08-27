package org.zgl.message;

public class ServerCommunicationIoMessage implements IoMessage {
    private long uid;
    private String ip;
    private String interfaceName;
    private String methodName;
    private Class<?>[] paramsType;
    private Object[] args;

    public long getUid() {
        return uid;
    }

    public void setUid(long uids) {
        this.uid = uids;
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

    public Class<?>[] getParamsType() {
        return paramsType;
    }

    public void setParamsType(Class<?>[] paramsType) {
        this.paramsType = paramsType;
    }

    public Object[] getArgs() {
        return args;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}