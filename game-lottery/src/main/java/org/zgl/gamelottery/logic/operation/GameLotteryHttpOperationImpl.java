package org.zgl.gamelottery.logic.operation;

import org.springframework.stereotype.Component;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.lottery.LotteryRoomInfoDto;
import org.zgl.dto.server.GameLotteryServerPlayerDto;
import org.zgl.gamelottery.logic.player.Player;
import org.zgl.gamelottery.logic.player.PlayerServerModel;
import org.zgl.gamelottery.logic.room.Room;
import org.zgl.gamelottery.logic.room.RoomFactory;
import org.zgl.service.server.commond.GamePlayerInfo;
import org.zgl.service.server.lottery.GameLotteryHttpOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ClinetProxy
@Component
public class GameLotteryHttpOperationImpl implements GameLotteryHttpOperation {
    @Override
    public LotteryRoomInfoDto enter(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            GamePlayerInfo info = HttpProxyOutboundHandler.createProxy(GamePlayerInfo.class,ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
            GameLotteryServerPlayerDto dto = info.getLotteryPlayer(uid);
            player = new Player(dto);
        }
        Room room = RoomFactory.getInstance().getRoom();
        PlayerServerModel.getInstance().putPlayer(player);
        return room.enter(player);
    }

    @Override
    public boolean exit(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            return false;
        }
        player.setExit(true);
        return true;
    }
}
