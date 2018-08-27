package org.zgl.service.client.db;

import org.zgl.dto.clinet.db.VersionDto;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/20
 * @文件描述：版本检查更新
 */
public interface GameVersion extends IHttpSyncService {
    VersionDto versionChech();
}
