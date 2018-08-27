package org.zgl.hall.socket.handler;

import io.netty.channel.Channel;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.JsonUtils;
import org.zgl.ProtostuffUtils;
import org.zgl.config.ConfigFactory;
import org.zgl.dto.server.HallServerPlayerDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.hall.ProxyScan;
import org.zgl.hall.logic.Operation;
import org.zgl.hall.logic.player.Player;
import org.zgl.hall.logic.player.PlayerServerModel;
import org.zgl.hall.utils.SpringUtils;
import org.zgl.message.GateIoMessage;
import org.zgl.message.ResponseIoMessage;
import org.zgl.message.ServerCommunicationIoMessage;
import org.zgl.service.regist.ServiceModel;
import org.zgl.service.server.commond.GamePlayerInfo;
import org.zgl.type.MutualEnum;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：
 */
public class IoMessageHandler {
    private static IoMessageHandler intance;

    public static IoMessageHandler getIntance() {
        if (intance == null) {
            intance = new IoMessageHandler();
        }
        return intance;
    }

    public void handler(ServerCommunicationIoMessage ioMessage, Channel channel) {
        try {
            Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(ioMessage.getUid());
            //使用http进入房间和离开房间 如果这里取不到玩家信息就是错误的协议
            if (player == null) {
                GamePlayerInfo playerInfo = HttpProxyOutboundHandler.createProxy(GamePlayerInfo.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
                HallServerPlayerDto dto = playerInfo.getHallPlayer(ioMessage.getUid());
                if (dto == null) {
                    new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
                }
                player = new Player().getPlayer(dto);
                PlayerServerModel.getInstance().putPlayer(player);
            }
            ServiceModel model = ProxyScan.getInstance().getServiceModel(ioMessage.getInterfaceName());
            if (model == null) {
                new AppGeneralError(AppErrorCode.SERVER_NOT_THIS_OPERATION);
            }
            Method method = model.getMethodByName(ioMessage.getMethodName());
            if (method == null) {
                new AppGeneralError(AppErrorCode.SERVER_NOT_THIS_OPERATION);
            }
            Object o = SpringUtils.getBean(model.getClazz());
            if (o == null) {
                new AppGeneralError(AppErrorCode.SERVER_NOT_THIS_OPERATION);
            }
            Operation op = ((Operation) o).clone();
            op.setPlayer(player);
            Runnable runnable = new MyRunnable(method, op, ioMessage, channel, player);
            player.submit(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyRunnable implements Runnable {
        private final Method method;
        private final Object obj;
        private final ServerCommunicationIoMessage ioMessage;
        private final Channel channel;
        private final Player player;

        public MyRunnable(Method method, Object obj, ServerCommunicationIoMessage ioMessage, Channel channel, Player player) {
            this.method = method;
            this.obj = obj;
            this.ioMessage = ioMessage;
            this.channel = channel;
            this.player = player;
        }

        @Override
        public void run() {
            try {
                if (method.getReturnType().equals(void.class)) {
                    //不返回
                    method.invoke(obj, ioMessage.getArgs());
                } else {
                    Object result = method.invoke(obj, ioMessage.getArgs());
                    List<Long> uids = new ArrayList<>(1);
                    uids.add(player.getUid());
                    String interfaceName = ioMessage.getInterfaceName();
                    String methodName = ioMessage.getMethodName() + "2CallBack";
                    List<String> args = new ArrayList<>(1);
                    args.add(JsonUtils.jsonSerialize(result));
                    ResponseIoMessage response = new ResponseIoMessage(uids, interfaceName, methodName, JsonUtils.jsonSerialize(args));
                    GateIoMessage gateIoMessage = new GateIoMessage(MutualEnum.SERVER_TO_CLIENT.id(), (short) -9, ProtostuffUtils.serializer(response));
                    write(channel, gateIoMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void write(Channel channel, Object msg) {
        channel.writeAndFlush(msg);
    }
}
