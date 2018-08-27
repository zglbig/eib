package gor.zgl.gamedice.logic.operation;

import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.service.server.db.UserLogOperation;
import org.zgl.type.ScenesEnum;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
public class UserLog {
    public static void userLog(long uid,String cardType,long exchangeGold,String desc,String cards){
        UserLogOperation log = HttpProxyOutboundHandler.createProxy(UserLogOperation.class,ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        log.submitLog(ScenesEnum.DICE.id(),cardType,uid,exchangeGold,desc,cards);
    }
}
