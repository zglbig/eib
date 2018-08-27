package org.zgl.db.logic.hall;

import org.springframework.stereotype.Controller;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.logic.Operation;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.desc.ServerProxy;
import org.zgl.dto.ItemDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.service.server.db.GiftHandler;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/20
 * @文件描述：
 */
@Controller
@ServerProxy
public class GiftHandlerImpl extends Operation implements GiftHandler {
    @Override
    public ItemDto insertGift(long uid, int id, int count,long charm) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        player.getWeath().insertCharm(charm);
        return player.getProp().insertProp(id,count);
    }

    @Override
    public ItemDto residuceGift(long uid,int id, int count) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        return player.getProp().insertProp(id,-count);
    }
}
