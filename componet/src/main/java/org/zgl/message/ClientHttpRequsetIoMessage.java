package org.zgl.message;

import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

/**
 * @author ： big
 * @创建时间： 2018/6/29
 * @文件描述：
 */
@Protostuff("commond")
public class ClientHttpRequsetIoMessage implements IoMessage,SerializeMessage {
    /**接口名*/
    private String interfaceName;
    /**方法名*/
    private String methodName;
    /**参数*/
    private String args;

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