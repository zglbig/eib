package org.zgl.db.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zgl.JsonUtils;
import org.zgl.ProtostuffUtils;
import org.zgl.db.ProxyScan;
import org.zgl.db.logic.Operation;
import org.zgl.db.utils.SpringUtils;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
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
 * @创建时间： 2018/7/31
 * @文件描述：
 */
@Controller
public class HttpIoMessageHandler {
    @RequestMapping(value = "/handler", method = RequestMethod.POST)
    @ResponseBody
    private void clientHandler(HttpServletRequest request, HttpServletResponse response) {
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
                if (method == null) {
                    new AppGeneralError(AppErrorCode.SERVER_NOT_THIS_OPERATION);
                }
                Object o = SpringUtils.getBean(model.getClazz());
                if (o == null) {
                    new AppGeneralError(AppErrorCode.SERVER_NOT_THIS_OPERATION);
                }
                Operation op = ((Operation) o).clone();
                op.setAddr(ioMessage.getIp());
                if (method.getReturnType().equals(void.class)) {
                    //不返回
                    method.invoke(op, ioMessage.getArgs());
                } else {
                    Object result = method.invoke(op, ioMessage.getArgs());
                    if (result != null) {
                        //响应事件
                        HttpIoMessage httpIoMessage = new HttpIoMessage(ioMessage.getUid(), 200, JsonUtils.jsonSerialize(result));
                        write(response, httpIoMessage);
                    }
                }
            } catch (Exception e) {
                //普通异常
                Throwable throwable = e.getCause();
                HttpIoMessage httpIoMessage = null;
                if (throwable instanceof AppGeneralError || e instanceof AppGeneralError) {
                    AppGeneralError g = (AppGeneralError) throwable;
                    httpIoMessage = new HttpIoMessage(ioMessage.getUid(), 404, g.getErrorCode() + "," + g.getErrorMsg());
                } else {
                    //反射调用异常
                    httpIoMessage = new HttpIoMessage(ioMessage.getUid(), 404, "404,服务器异常");
                    e.printStackTrace();
                }
                write(response, httpIoMessage);
            }
            //以protostuff的形式进行传输
        } catch (Exception var21) {
            //读取数据异常
            var21.printStackTrace();
        }
    }

    private void write(HttpServletResponse response, HttpIoMessage httpIoMessage) {
        if (httpIoMessage == null) {
            return;
        }
        try (DataOutputStream dos = new DataOutputStream(response.getOutputStream())) {
            byte[] responesBuf = ProtostuffUtils.serializer(httpIoMessage);
            //将数据返回
            dos.writeInt(responesBuf.length);
            dos.write(responesBuf);
        } catch (Exception e) {
            //数据回写异常
            e.printStackTrace();
        }
    }
}
