package org.zgl.service.gate;

import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */
public interface LoginOut extends IHttpSyncService {
    /**
     * 下线
     * @param uid
     * @return
     */
    boolean loginOut(long uid);
}
