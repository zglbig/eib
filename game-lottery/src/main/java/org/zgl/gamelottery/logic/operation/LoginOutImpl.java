package org.zgl.gamelottery.logic.operation;

import org.springframework.stereotype.Controller;
import org.zgl.desc.ServerProxy;
import org.zgl.gamelottery.logic.player.Player;
import org.zgl.gamelottery.logic.player.PlayerServerModel;
import org.zgl.gamelottery.logic.room.Room;
import org.zgl.gamelottery.logic.room.RoomFactory;
import org.zgl.service.gate.LoginOut;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/20
 * @文件描述：
 */
@ServerProxy
@Controller
public class LoginOutImpl implements LoginOut {
    @Override
    public boolean loginOut(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            return false;
        }
        //通知其他玩家有人下线了
        Room room = RoomFactory.getInstance().getRoom();
        if(room == null){
            return false;
        }
        room.exit(player);

        PlayerServerModel.getInstance().removePlayer(player);
        return true;
    }
}
