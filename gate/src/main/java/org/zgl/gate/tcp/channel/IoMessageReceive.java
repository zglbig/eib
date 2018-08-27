package org.zgl.gate.tcp.channel;

import io.netty.channel.Channel;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.JsonUtils;
import org.zgl.ProtostuffUtils;
import org.zgl.config.ConfigFactory;
import org.zgl.dto.server.GateServerPlayerDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.gate.register.Register;
import org.zgl.gate.tcp.channel.session.SessionEntity;
import org.zgl.gate.tcp.channel.session.SessionServerModel;
import org.zgl.gate.utils.LoggerUtils;
import org.zgl.message.ClientTcpIoMessage;
import org.zgl.message.GateIoMessage;
import org.zgl.message.ResponseIoMessage;
import org.zgl.message.ServerCommunicationIoMessage;
import org.zgl.service.client.err.AppError;
import org.zgl.service.regist.MethodModel;
import org.zgl.service.regist.ServiceInterfaceModel;
import org.zgl.service.server.commond.GamePlayerInfo;
import org.zgl.type.MutualEnum;

import java.util.List;

/**
 * @author ： big
 * @创建时间： 2018/6/20
 * @文件描述：消息处理
 */
public class IoMessageReceive {
    public static void ioMessageHandler(Channel channel, GateIoMessage ioMessage) {
        try {
//            if(!Register.getInstance().isInitSuccess()){
//                LoggerUtils.getLogicLog().error("服务器尚未启动完成");
//                new AppGeneralError(AppErrorCode.SERVER_NOT_INIT_SUCCESS);
//            }
            recieveMessage(channel, ioMessage);
        } catch (Exception e) {
            ClientTcpIoMessage errorMsg = new ClientTcpIoMessage();
            errorMsg.setInterfaceName(AppError.class.getName());
            errorMsg.setMethodName("error");
            Throwable throwable = e.getCause();
            if (throwable instanceof AppGeneralError) {
                AppGeneralError error = (AppGeneralError) throwable;
                errorMsg.setArgs(error.getErrorMsgArgs());
            } else if (e instanceof AppGeneralError) {
                AppGeneralError error = (AppGeneralError) e;
                errorMsg.setArgs(error.getErrorMsgArgs());
            } else {
                errorMsg.setArgs(AppErrorCode.DATA_ANALYSIS_ERR + "");
                LoggerUtils.getLogicLog().error("数据解析异常", e);
            }
            GateIoMessage gateIoMessage = new GateIoMessage((short) 1, (short) 1, ProtostuffUtils.serializer(errorMsg));
            channel.writeAndFlush(gateIoMessage);
        }
    }

    private static void recieveMessage(Channel channel, GateIoMessage ioMessage) {
        MutualEnum mutualEnum = MutualEnum.getMutualEnum(ioMessage.getCmd());
        if (mutualEnum == null) {
            new AppGeneralError(AppErrorCode.THIS_GAME_IS_UPDATE_ERR);
        }
        //少这里以后只有客户端请求服务器的时候才反序列化 减反序列化次数
        switch (mutualEnum) {
            case CLIENT_TO_SERVER:
                //客户端请求服务器
                SessionEntity session = SessionServerModel.getInstance().getEntityUid(ioMessage.getGameId());
                if (session == null) {
                    new AppGeneralError(AppErrorCode.NOW_SERVER_UPDATE);
                }
                ioMessage = exchangeIoMessage(channel, ioMessage.getCmd(), ioMessage.getGameId(), ioMessage.getMessage());
                session.write(ioMessage);
                session.setLastEditTime(System.currentTimeMillis());
                break;
            case SERVER_TO_CLIENT:
                //服务器返回数据到客户端
                ResponseIoMessage s = ProtostuffUtils.deserializer(ioMessage.getMessage(), ResponseIoMessage.class);
                ClientTcpIoMessage c = new ClientTcpIoMessage();
                c.setArgs(s.getArgs());
                c.setInterfaceName(s.getInterfaceName());
                c.setMethodName(s.getMethodName());
                //cmd 1普通返回消息通知 2 服务器主动通知
                GateIoMessage gateIoMessage = new GateIoMessage((short) 1, (short) 1, ProtostuffUtils.serializer(c));
                //解析参数回传
                for (long uid : s.getUids()) {
                    SessionEntity entity = SessionServerModel.getInstance().getEntityUid(uid);
                    if (entity != null) {
                        entity.write(gateIoMessage);
                        entity.setLastEditTime(System.currentTimeMillis());
                    }
                }
                break;
            case REGIST:
                //注册链接
                ClientTcpIoMessage registerMsg = ProtostuffUtils.deserializer(ioMessage.getMessage(), ClientTcpIoMessage.class);
                List<String> args = JsonUtils.jsonParseList(registerMsg.getArgs(), String.class);
                if (args.size() != 2) {
                    new AppGeneralError(AppErrorCode.REGISTER_UID_ERR);
                }
                long uid = 0;
                String secretKey = "";
                try {
                    uid = Long.valueOf(args.get(0));
                    secretKey = args.get(1);
                } catch (NumberFormatException e) {
                    LoggerUtils.getLogicLog().error("数据异常", e);
                    new AppGeneralError(AppErrorCode.REGISTER_UID_ERR);
                }
                registSession(channel, uid, secretKey);
                break;
            case SERVER_TO_CLIENT_NOTIFY_ALL:
                //全服通知
                //服务器返回数据到客户端
                ResponseIoMessage sa = ProtostuffUtils.deserializer(ioMessage.getMessage(), ResponseIoMessage.class);
                ClientTcpIoMessage ca = new ClientTcpIoMessage();
                ca.setArgs(sa.getArgs());
                ca.setInterfaceName(sa.getInterfaceName());
                ca.setMethodName(sa.getMethodName());
                //cmd 1普通返回消息通知 2 服务器主动通知
                GateIoMessage gateIoMessageA = new GateIoMessage((short) 1, (short) 1, ProtostuffUtils.serializer(ca));
                //解析参数回传
                for (SessionEntity entity : SessionServerModel.getInstance().getSessionList()) {
                    entity.write(gateIoMessageA);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 转换客户端发送过来的消息
     *
     * @param channel
     * @param cmd
     * @param gameId
     * @param buf
     * @return
     */
    private static GateIoMessage exchangeIoMessage(Channel channel, short cmd, short gameId, byte[] buf) {
        ClientTcpIoMessage ioMessage = ProtostuffUtils.deserializer(buf, ClientTcpIoMessage.class);
        ServerCommunicationIoMessage server = new ServerCommunicationIoMessage();
        try {
            //这里需要将各个服务器提供的接口服务注册到gate服中
            ServiceInterfaceModel sim = Register.getInstance().getServiceInterfaceModel(gameId, ioMessage.getInterfaceName());
            MethodModel methodModel = sim.getMethodModel(ioMessage.getMethodName());
            SessionEntity sessionEntity = SessionServerModel.getInstance().getEntityByChannel(channel);
            if (sessionEntity == null || sessionEntity.getUid() == 0) {
                //链接尚未注册
                new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
            }
            //这里根据链接来取
            server.setUid(sessionEntity.getUid());
            server.setInterfaceName(sim.getInterfaceName());
            server.setMethodName(methodModel.getMethodName());
            Class<?>[] parameterTypes = methodModel.getParamentTypes();

            server.setParamsType(parameterTypes);
            List<Object> params = JsonUtils.jsonParseList(ioMessage.getArgs(), Object.class);
            if (parameterTypes.length != params.size()) {
                new AppGeneralError(AppErrorCode.PARAMETER_ERR);
            }
            //这里不做其他类的处理
            //如果要处理就先转成JSONObject对象再取它的toString再掉json反序列化才可以
            Object[] args = new Object[params.size()];
            for (int i = 0; i < args.length; i++) {
                args[i] = params.get(i);
            }
            server.setArgs(args);
            server.setIp(channel.remoteAddress().toString());
            //转换参数
            byte[] buffer = ProtostuffUtils.serializer(server);
            sessionEntity.setLastEditTime(System.currentTimeMillis());
            return new GateIoMessage(cmd, gameId, buffer);
        } catch (Exception e) {
            LoggerUtils.getLogicLog().error("数据异常", e);
            throw new RuntimeException("数据异常");
        }
    }

    /**
     * 注册绑定链接
     *
     * @param channel
     * @param uid
     */
    private static void registSession(Channel channel, long uid, String secretKey) {
        //用户注册链接
        SessionEntity entity = null;
        if (uid > 0) {
            entity = SessionServerModel.getInstance().getEntityUid(uid);
            if (entity == null) {
                GamePlayerInfo gamePlayerInfo = HttpProxyOutboundHandler.createProxy(GamePlayerInfo.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
                GateServerPlayerDto dto = gamePlayerInfo.getGatePlayer(uid);
                if (dto == null) {
                    new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
                }
                if (!dto.getSecretKey().equals(secretKey)) {
                    new AppGeneralError(AppErrorCode.SIGIN_ERR);
                }
                entity = new SessionEntity(uid, channel, dto.getUserName(), dto.getVipLv(), dto.getHeadUrl(), secretKey);
            } else {
                if (!entity.getSecretKey().equals(secretKey)) {
                    new AppGeneralError(AppErrorCode.SIGIN_ERR);
                }
                entity = new SessionEntity(uid, channel, entity.getUserName(), entity.getVipLv(), entity.getHeadUrl(), secretKey);
            }
        } else {
            entity = new SessionEntity(uid, channel);
        }
        //绑定消息
        LoggerUtils.getLogicLog().debug("uid为：" + uid + "的连接注册成功");
        SessionServerModel.getInstance().putEntity(entity);
    }

    /**
     * 是否是基础数据类型
     *
     * @param clazz
     * @return
     */
    private static boolean isPrimit(Class<?> clazz) {
        if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
            return true;
        } else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
            return true;
        } else if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
            return true;
        } else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
            return true;
        } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
            return true;
        } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
            return true;
        } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
            return true;
        } else if (clazz.equals(String.class)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 转换参数类型
     *
     * @param params
     * @param clazz
     * @return
     */
    public static Object exchangeParams(String params, Class<?> clazz) {
        if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
            return Integer.parseInt(params);
        } else if (clazz.equals(short.class) || clazz.equals(Short.class)) {
            return Short.parseShort(params);
        } else if (clazz.equals(byte.class) || clazz.equals(Byte.class)) {
            return Byte.parseByte(params);
        } else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
            return Boolean.parseBoolean(params);
        } else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
            return Long.parseLong(params);
        } else if (clazz.equals(float.class) || clazz.equals(Float.class)) {
            return Float.parseFloat(params);
        } else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
            return Double.parseDouble(params);
        } else if (clazz.equals(String.class)) {
            return params;
        } else {
            return JsonUtils.jsonDeserialization(params, clazz);
        }
    }

    public static void exceptionCaught(Channel channel) {
        SessionEntity entity = SessionServerModel.getInstance().getEntityByChannel(channel);
        if (entity != null) {
            if (entity.getUid() < 0) {
                //如果是服务器的连接则连注册的接口都删除
                Register.getInstance().removeRegist((short) entity.getUid());
            } else {
                entity.setOnline(false);
            }
        }
    }
}
