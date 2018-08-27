package org.zgl.gate.register.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zgl.ProtostuffUtils;
import org.zgl.gate.register.Register;
import org.zgl.service.regist.RegisterModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/4
 * @文件描述：注册接口
 */
@Controller
public class RegistService {
    @RequestMapping(value = "/registinterface",method = RequestMethod.POST)
    @ResponseBody
    private void regist(HttpServletRequest request, HttpServletResponse response){
        //不是服务器的就扣不可以注册
        if(request.getRemoteAddr().equals("")){
            return;
        }
        try(DataInputStream dis = new DataInputStream(request.getInputStream()); DataOutputStream dos = new DataOutputStream(response.getOutputStream())) {
            short gameId = dis.readShort();
            int msgLength = dis.readInt();
            byte[] data = new byte[msgLength];
            dis.readFully(data);
            RegisterModel registerModel = ProtostuffUtils.deserializer(data,RegisterModel.class);
            Register.getInstance().putAll(gameId,registerModel);
            System.err.println("服务器"+gameId+"注册网关服务成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
