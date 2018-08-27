package org.zgl.game2.logic.operation;

import org.springframework.stereotype.Controller;
import org.zgl.desc.ServerProxy;
import org.zgl.game2.logic.player.Player;
import org.zgl.game2.logic.player.PlayerServerModel;
import org.zgl.game2.logic.room.Room;
import org.zgl.game2.logic.room.RoomFactory;
import org.zgl.service.gate.LoginOut;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Controller
@ServerProxy
public class LoginOutImpl implements LoginOut {
    @Override
    public boolean loginOut(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            return false;
        }
        Room room = player.getRoom();
        if(room == null){
            room = RoomFactory.getInstance().getRoom();
            if(room == null){
                return false;
            }
        }
        room.exit(player);
        PlayerServerModel.getInstance().removePlayer(player);
        return false;
    }
}
