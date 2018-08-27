package org.zgl.gate.online;

import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.gate.register.Register;
import org.zgl.gate.tcp.channel.session.SessionEntity;
import org.zgl.gate.tcp.channel.session.SessionServerModel;
import org.zgl.service.gate.LoginOut;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：断线处理
 */
public class OnlieManager {
    /**这里使用springboot得定时任务执行*/
    public static void onlineHandler(){
        List<SessionEntity> entitieList = SessionServerModel.getInstance().getSessionList();
        for(SessionEntity e:entitieList){
            if(!e.isOnline() && System.currentTimeMillis() - e.getLastEditTime() > 20000){
                //已经断线或者长时间没操作 通知下线
                SessionServerModel.getInstance().removeEntity(e);
                if(e.getChannel().isActive()){
                    e.getChannel().close();
                }
                //通知所有服务器玩家下线
                loginOut(e);
            }
        }
    }

    /**
     * 玩家下线
     * @param e
     */
    public static void loginOut(SessionEntity e){
        //通知所有服务器玩家下线
        for(String url : Register.getInstance().allServerUrl()){
            LoginOut loginOut = HttpProxyOutboundHandler.createProxy(LoginOut.class,url);
            loginOut.loginOut(e.getUid());
        }
        //最后才通知db服下线
        LoginOut loginOut5 = HttpProxyOutboundHandler.createProxy(LoginOut.class,ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        loginOut5.loginOut(e.getUid());
        SessionServerModel.getInstance().removeEntity(e);
    }
}
