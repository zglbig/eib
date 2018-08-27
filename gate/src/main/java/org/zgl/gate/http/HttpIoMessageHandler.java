package org.zgl.gate.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zgl.JsonUtils;
import org.zgl.ProtostuffUtils;
import org.zgl.common.HttpClientPostImpl;
import org.zgl.config.ConfigFactory;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.gate.online.LoginOutImpl;
import org.zgl.gate.register.Register;
import org.zgl.gate.utils.LoggerUtils;
import org.zgl.message.ClientHttpRequsetIoMessage;
import org.zgl.message.HttpIoMessage;
import org.zgl.message.ServerCommunicationIoMessage;
import org.zgl.service.gate.LoginOut;
import org.zgl.service.regist.MethodModel;
import org.zgl.service.regist.ServiceInterfaceModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
@Controller
public class HttpIoMessageHandler {
    @RequestMapping(value = "/handler", method = RequestMethod.POST)
    @ResponseBody
    private void handler(HttpServletRequest request, HttpServletResponse response) {
        try (DataInputStream dis = new DataInputStream(request.getInputStream()); DataOutputStream dos = new DataOutputStream(response.getOutputStream())) {
            byte key1 = dis.readByte();
            short key2 = dis.readShort();
            int key3 = dis.readInt();
            short gameId = dis.readShort();
            int msgLength = dis.readInt();
            if (key1 == 19 && key2 == 1024 && key3 == 5) {
                byte[] data = new byte[msgLength];
                String ip = request.getRemoteAddr();
                dis.readFully(data);
                if(gameId == ConfigFactory.getInstance().getSocketUrlCfg().getGateId()){
                    ServerCommunicationIoMessage message = exchangeIoMessage(gameId, data, ip);
                    long uid = (long) message.getArgs()[0];
                    LoginOut out = new LoginOutImpl();
                    boolean b = out.loginOut(uid);
                    write(dos,200,JsonUtils.jsonSerialize(b));
                }else {
                    String url = Register.getInstance().getUrl(gameId);
                    try {
                        byte[] buf = ProtostuffUtils.serializer(exchangeIoMessage(gameId, data, ip));
                        byte[] result = HttpClientPostImpl.getInstance().sendHttp(url, buf, true);
                        write(dos,result);
                    } catch (Exception e) {
                        write(dos,404,AppErrorCode.PARAMETER_ERR + ",解析参数异常");
                    }
                }
                //将数据返回
            } else {
                //数据解析不正确
                write(dos,404,AppErrorCode.PARAMETER_ERR + ",数据解析不正确");
                LoggerUtils.getLogicLog().error("数据解析不正确");
            }
        } catch (Exception e) {
            LoggerUtils.getLogicLog().error("数据解析不正确", e);
        }
    }

    private ServerCommunicationIoMessage exchangeIoMessage(short gameId, byte[] buf,String ip) {
        ClientHttpRequsetIoMessage ioMessage = ProtostuffUtils.deserializer(buf, ClientHttpRequsetIoMessage.class);
        ServerCommunicationIoMessage server = new ServerCommunicationIoMessage();
        //这里需要将各个服务器提供的接口服务注册到gate服中
        ServiceInterfaceModel sim = Register.getInstance().getServiceInterfaceModel(gameId, ioMessage.getInterfaceName());
        MethodModel methodModel = sim.getMethodModel(ioMessage.getMethodName());
        //这里根据链接来取
        server.setInterfaceName(sim.getInterfaceName());
        server.setIp(ip);
        server.setMethodName(methodModel.getMethodName());
        Class<?>[] parameterTypes = methodModel.getParamentTypes();
        server.setParamsType(parameterTypes);
        Object[] params = new Object[parameterTypes.length];
        List<Object> list = JsonUtils.jsonParseList(ioMessage.getArgs(), Object.class);
        if(list == null){
            list = new ArrayList<>();
        }
        if (list.size() != params.length) {
            new AppGeneralError(AppErrorCode.PARAMETER_ERR);
        }
        for (int i = 0; i < params.length; i++) {
            params[i] = list.get(i);
        }
        server.setArgs(params);
        //转换参数

        return server;
    }
    private void write(DataOutputStream dos,int code,String msg){
        try {
            HttpIoMessage ioMessage = new HttpIoMessage();
            ioMessage.setResultCode(code);
            ioMessage.setMsg(msg);
            byte[] buf = ProtostuffUtils.serializer(ioMessage);
            dos.writeInt(buf.length);
            dos.write(buf);
        }catch (Exception e){
            LoggerUtils.getLogicLog().error("数据回发异常");
        }
    }
    private void write(DataOutputStream dos,byte[] buf){
        try {
            dos.writeInt(buf.length);
            dos.write(buf);
        }catch (Exception e){
            LoggerUtils.getLogicLog().error("数据回发异常");
        }
    }
}
