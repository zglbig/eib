package org.zgl.service.client.db;

import org.zgl.dto.clinet.hall.BankDto;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
public interface BankOperation extends IHttpSyncService {
    BankDto save(long uid, long gold);
    BankDto draw(long uid, long gold);
}
