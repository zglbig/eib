package org.zgl.db.server;

import org.springframework.stereotype.Controller;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.logic.Operation;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.desc.ServerProxy;
import org.zgl.service.gate.LoginOut;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */
@Controller
@ServerProxy
public class LoginOutImpl extends Operation implements LoginOut {
    @Override
    public boolean loginOut(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            return false;
        }
        PlayerServerModel.getInstance().removePlayer(player);
        return true;
    }
}
