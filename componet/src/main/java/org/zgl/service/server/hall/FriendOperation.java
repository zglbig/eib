package org.zgl.service.server.hall;

import org.zgl.dto.clinet.commond.BasePlayerDto;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
public interface FriendOperation extends IHttpSyncService {
    BasePlayerDto consent(long selfUid, long uid);
}
