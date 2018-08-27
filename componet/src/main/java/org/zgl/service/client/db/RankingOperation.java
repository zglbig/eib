package org.zgl.service.client.db;

import org.zgl.dto.clinet.db.RankingListDto;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
public interface RankingOperation extends IHttpSyncService {
    RankingListDto goldRanking();
    RankingListDto charmRanking();
}
