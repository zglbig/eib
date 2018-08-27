package org.zgl.service.server.db;

import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */

public interface UserLogOperation extends IHttpSyncService {
    void submitLog(int scenesId, String cardType, long uid, long exchangeGold, String desc, String cards);
}
