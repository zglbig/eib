package gor.zgl.gamedice.socket.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zgl.JsonUtils;
import org.zgl.ProtostuffUtils;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.gamedice.ProxyScan;
import org.zgl.gamedice.utils.SpringUtils;
import org.zgl.message.HttpIoMessage;
import org.zgl.message.ServerCommunicationIoMessage;
import org.zgl.service.regist.ServiceModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/30
 * @文件描述：
 */
@Controller
public class HttpServerPostImpl {
    /**
     * 房间的所有http操作都在这进行
     * 1，进入房间
     * 2，离开房间
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/handler", method = RequestMethod.POST)
    private void intoRoom(HttpServletRequest request, HttpServletResponse response) {
        try (DataInputStream dis = new DataInputStream(request.getInputStream())) {
            int msgLength = dis.readInt();
            byte[] data = new byte[msgLength];
            dis.readFully(data);
            ServerCommunicationIoMessage ioMessage = ProtostuffUtils.deserializer(data, ServerCommunicationIoMessage.class);
            try {
                ServiceModel model = ProxyScan.getInstance().getServiceModel(ioMessage.getInterfaceName());
                if (model == null) {
                    new AppGeneralError(AppErrorCode.SERVER_NOT_THIS_OPERATION);
                }
                Method method = model.getMethodByName(ioMessage.getMethodName());
                if(method == null){
                    new AppGeneralError(AppErrorCode.SERVER_NOT_THIS_OPERATION);
                }else {
                    Object o = SpringUtils.getBean(model.getClazz());
                    if(o == null){
                        new AppGeneralError(AppErrorCode.SERVER_NOT_THIS_OPERATION);
                    }
                    if (method.getReturnType().equals(void.class)) {
                        //不返回
                        method.invoke(o, ioMessage.getArgs());
                    } else {
                        Object result = method.invoke(o, ioMessage.getArgs());
                        if (result != null) {
                            //响应事件
                            //响应事件
                            HttpIoMessage httpIoMessage = new HttpIoMessage(ioMessage.getUid(), AppErrorCode.SUCCESS, JsonUtils.jsonSerialize(result));
                            write(response, httpIoMessage);
                        }
                    }
                }
            } catch (Exception e) {
                Throwable throwable = e.getCause();
                HttpIoMessage httpIoMessage = null;
                if (throwable instanceof AppGeneralError || e instanceof AppGeneralError) {
                    AppGeneralError g = (AppGeneralError) throwable;
                    httpIoMessage = new HttpIoMessage(ioMessage.getUid(), g.getErrorCode(), g.getErrorMsg());
                } else {
                    //反射调用异常
                    httpIoMessage = new HttpIoMessage(ioMessage.getUid(), AppErrorCode.SERVER_ERROR, "10,服务器异常");
                    e.printStackTrace();
                }
                write(response, httpIoMessage);
            }
        } catch (Exception var21) {
            //读取数据异常
        }
    }

    private void write(HttpServletResponse response, HttpIoMessage ioMessage) {
        try (DataOutputStream dos = new DataOutputStream(response.getOutputStream())) {
            byte[] responesBuf = ProtostuffUtils.serializer(ioMessage);
            //将数据返回
            dos.writeInt(responesBuf.length);
            dos.write(responesBuf);
        } catch (Exception e) {
            //数据回写异常
            e.printStackTrace();
        }
    }
}
