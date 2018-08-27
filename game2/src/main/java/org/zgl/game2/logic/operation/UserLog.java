package org.zgl.game2.logic.operation;

import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.service.server.db.UserLogOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
public class UserLog {
    public static void userLog(int scenesId,long uid,String cardType,long exchangeGold,String desc,String cards){
        UserLogOperation log = HttpProxyOutboundHandler.createProxy(UserLogOperation.class,ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        log.submitLog(scenesId,cardType,uid,exchangeGold,desc,cards);
    }
}
