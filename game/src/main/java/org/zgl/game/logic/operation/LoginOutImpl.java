package org.zgl.game.logic.operation;

import org.springframework.stereotype.Controller;
import org.zgl.desc.ServerProxy;
import org.zgl.game.logic.player.Player;
import org.zgl.game.logic.player.PlayerServerModel;
import org.zgl.game.logic.room.Room;
import org.zgl.game.logic.room.RoomServerModel;
import org.zgl.service.gate.LoginOut;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
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
        //通知其他玩家有人下线了
        Room room = RoomServerModel.getInstance().getRoom(player.getScenesId(),player.getRoomId());
        if(room == null){
            return false;
        }
        room.exit(player);

        PlayerServerModel.getInstance().removePlayer(player);
        return true;
    }
}