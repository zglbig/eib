package org.zgl.db.logic.hall.ranking;

import org.springframework.stereotype.Controller;
import org.zgl.db.logic.Operation;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.db.RankingListDto;
import org.zgl.service.client.db.RankingOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@ClinetProxy
@Controller
public class RankingOperationImpl extends Operation implements RankingOperation {

    @Override
    public RankingListDto goldRanking() {
        return RankingManager.getInstance().gold();
    }

    @Override
    public RankingListDto charmRanking() {
        return RankingManager.getInstance().charm();
    }
}
