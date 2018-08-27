package org.zgl.service.client.err;

import org.zgl.proxy.rule.ITcpAsyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */
public interface AppError extends ITcpAsyncService {
    void error(int errorCode, String msg);
}
