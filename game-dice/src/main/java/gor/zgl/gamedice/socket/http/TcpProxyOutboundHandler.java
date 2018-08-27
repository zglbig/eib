package gor.zgl.gamedice.socket.http;

import org.zgl.JsonUtils;
import org.zgl.ProtostuffUtils;
import org.zgl.config.ConfigFactory;
import org.zgl.gamedice.socket.tcp.GameServer;
import org.zgl.message.GateIoMessage;
import org.zgl.message.ResponseIoMessage;
import org.zgl.type.MutualEnum;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author ： big
 * @创建时间： 2018/6/8
 * @文件描述：下发通知（远程调用客户端接口） 也算回调
 */
public class TcpProxyOutboundHandler {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class serviceInterFace, final List<Long> uids) {
        return (T) Proxy.newProxyInstance(serviceInterFace.getClassLoader(), new Class<?>[]{serviceInterFace}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String interfaceName = serviceInterFace.getName();
                String methodName = method.getName();
                String argsTemp = JsonUtils.jsonSerialize(args);
                ResponseIoMessage response = new ResponseIoMessage(uids,interfaceName,methodName,argsTemp);
                GateIoMessage gateIoMessage = new GateIoMessage(MutualEnum.SERVER_TO_CLIENT.id(),ConfigFactory.getInstance().getSocketUrlCfg().getGameDiceId(),ProtostuffUtils.serializer(response));
                GameServer.getInstance().write(gateIoMessage);
                return null;
            }
        });
    }
    @SuppressWarnings("unchecked")
    public static <T> T createNotifyAllProxy(final Class serviceInterFace){
        return (T) Proxy.newProxyInstance(serviceInterFace.getClassLoader(), new Class<?>[]{serviceInterFace}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String interfaceName = serviceInterFace.getName();
                String methodName = method.getName();
                String argsTemp = JsonUtils.jsonSerialize(args);
                ResponseIoMessage response = new ResponseIoMessage(null,interfaceName,methodName,argsTemp);
                GateIoMessage gateIoMessage = new GateIoMessage(MutualEnum.SERVER_TO_CLIENT_NOTIFY_ALL.id(),ConfigFactory.getInstance().getSocketUrlCfg().getGameDiceId(),ProtostuffUtils.serializer(response));
                GameServer.getInstance().write(gateIoMessage);
                return null;
            }
        });
    }
    @SuppressWarnings("unchecked")
    public static <T> T createTaskProxy(final Class serviceInterFace){
        return (T) Proxy.newProxyInstance(serviceInterFace.getClassLoader(), new Class<?>[]{serviceInterFace}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String interfaceName = serviceInterFace.getName();
                String methodName = method.getName();
                String argsTemp = JsonUtils.jsonSerialize(args);
                ResponseIoMessage response = new ResponseIoMessage(null,interfaceName,methodName,argsTemp);
                GateIoMessage gateIoMessage = new GateIoMessage(MutualEnum.SERVER_TO_CLIENT_TASK.id(),ConfigFactory.getInstance().getSocketUrlCfg().getGameDiceId(),ProtostuffUtils.serializer(response));
                GameServer.getInstance().write(gateIoMessage);
                return null;
            }
        });
    }
}