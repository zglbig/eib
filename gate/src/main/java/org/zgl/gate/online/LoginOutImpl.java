package org.zgl.gate.online;

import org.springframework.stereotype.Controller;
import org.zgl.desc.ClinetProxy;
import org.zgl.gate.tcp.channel.session.SessionEntity;
import org.zgl.gate.tcp.channel.session.SessionServerModel;
import org.zgl.service.gate.LoginOut;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/23
 * @文件描述：
 */
@ClinetProxy
@Controller
public class LoginOutImpl implements LoginOut {
    @Override
    public boolean loginOut(long uid) {
        SessionEntity entity = SessionServerModel.getInstance().getEntityUid(uid);
        if(entity != null){
            OnlieManager.loginOut(entity);
        }
        return true;
    }
}
