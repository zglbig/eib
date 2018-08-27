package org.zgl.hall.logic;

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
    public static void userLog(long uid, long exchangeGold, String desc) {
        UserLogOperation log = HttpProxyOutboundHandler.createProxy(UserLogOperation.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        log.submitLog(ScenesEnum.HALL.id(), "not card", uid, exchangeGold, desc, "not card");
    }
}
