package org.zgl;

import org.zgl.common.HttpClientPostImpl;
import org.zgl.common.StringUtils;
import org.zgl.error.AppGeneralError;
import org.zgl.message.HttpIoMessage;
import org.zgl.message.ServerCommunicationIoMessage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ： big
 * @创建时间： 2018/6/8
 * @文件描述：下发通知（远程调用客户端接口） 也算回调
 */
public class HttpProxyOutboundHandler {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class serviceInterFace, final String url) {
        return (T) Proxy.newProxyInstance(serviceInterFace.getClassLoader(), new Class<?>[]{serviceInterFace}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ServerCommunicationIoMessage ioMessage = new ServerCommunicationIoMessage();
                ioMessage.setInterfaceName(serviceInterFace.getName());
                ioMessage.setMethodName(method.getName());
                ioMessage.setParamsType(method.getParameterTypes());
                ioMessage.setArgs(args);
                Class<?> returnType = method.getReturnType();
                if (!returnType.equals(void.class)) {
                    byte[] buf = HttpClientPostImpl.getInstance().sendHttp(url, ProtostuffUtils.serializer(ioMessage), true);
                    return getResult(buf, returnType);
                } else {
                    HttpClientPostImpl.getInstance().sendHttp(url,  ProtostuffUtils.serializer(ioMessage), false);
                }
                return null;
            }
        });
    }

    public static <T> T getResult(byte[] buf, Class<T> tClass) {
        T t = null;
        HttpIoMessage ioMessage = ProtostuffUtils.deserializer(buf, HttpIoMessage.class);
        if (ioMessage.getResultCode() == 404) {
            String[] str = StringUtils.split(ioMessage.getMsg(), ",");
            StringBuilder sb = new StringBuilder();
            if (str.length > 1) {
                for (int i = 1; i < str.length; i++) {
                    sb.append(str[i]);
                    if (i != str.length - 1) {
                        sb.append(",");
                    }
                }
            }
            int errorCode = Integer.parseInt(str[0]);
            new AppGeneralError(errorCode, sb.toString());
        } else if (ioMessage.getResultCode() == 200) {
            t = JsonUtils.jsonDeserialization(ioMessage.getMsg(), tClass);
        }

        return t;
    }
}