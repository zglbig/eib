package org.zgl.db.server;

import org.springframework.stereotype.Controller;
import org.zgl.DateUtils;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.dao.entity.UserLog;
import org.zgl.db.dao.mapper.UserLogMapper;
import org.zgl.db.logic.Operation;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.db.utils.SpringUtils;
import org.zgl.desc.ServerProxy;
import org.zgl.service.server.db.UserLogOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@ServerProxy
@Controller
public class UserLogOperationImpl extends Operation  implements UserLogOperation {
    @Override
    public void submitLog(int scenesId, String cardType, long uid, long exchangeGold, String desc, String cards) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            return;
        }
        UserLog log = new UserLog();
        log.setCard(cards);
        log.setOperationDesc(desc);
        log.setOperationTime(DateUtils.currentDay());
        log.setPosition(scenesId);
        log.setOperationTimeStr(DateUtils.nowTime());
        log.setUid(uid);
        log.setHoldGold(player.getWeath().getGold());
        log.setExchangeGold(exchangeGold);
        log.setCardType(cardType);
        UserLogMapper mapper = SpringUtils.getBean(UserLogMapper.class);
        mapper.insertUserLog(log);
    }
}
