package org.zgl.service.server.hall;

import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：活动
 */
public interface ActivityOperation extends ITcpAsyncService {
    void pay(int productId);
}
