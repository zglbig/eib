package gor.zgl.gamedice.logic.operation;

import org.springframework.stereotype.Component;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.dice.DiceRoomInfiDto;
import org.zgl.dto.server.GameDiceServerPlayerDto;
import org.zgl.gamedice.logic.player.Player;
import org.zgl.gamedice.logic.player.PlayerServerModel;
import org.zgl.gamedice.logic.room.Room;
import org.zgl.gamedice.logic.room.RoomFactory;
import org.zgl.service.server.commond.GamePlayerInfo;
import org.zgl.service.server.dice.GameDiceHttpRoomOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
@Component
@ClinetProxy
public class GameDiceHttpRoomOperationImpl implements GameDiceHttpRoomOperation {
    @Override
    public DiceRoomInfiDto enter(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            GamePlayerInfo info = HttpProxyOutboundHandler.createProxy(GamePlayerInfo.class,ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
            GameDiceServerPlayerDto dto = info.getDicePlayer(uid);
            player = new Player(dto);
        }
        Room room = RoomFactory.getInstance().getRoom();
        PlayerServerModel.getInstance().putPlayer(player);
        return room.enter(player);
    }
}
