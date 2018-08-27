package org.zgl.game.logic.operation;

import org.springframework.stereotype.Component;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.game1.Game1PlayerRoomDto;
import org.zgl.dto.server.Game1ServerPlayerDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.game.logic.player.Player;
import org.zgl.game.logic.player.PlayerServerModel;
import org.zgl.game.logic.room.Room;
import org.zgl.game.logic.room.RoomServerModel;
import org.zgl.service.server.commond.GamePlayerInfo;
import org.zgl.service.server.game1.Game1HttpRoomOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/6
 * @文件描述：
 */
@Component
@ClinetProxy
public class Game1HttpRoomOperationImpl implements Game1HttpRoomOperation {
    @Override
    public Game1PlayerRoomDto enterRoom(long uid, int scenesId) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player != null){
            //该账号已经登陆
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_ONLINE_ERR);
        }
        //从db服务器中获取该玩家数据如果没有则是该账号尚未登陆
        GamePlayerInfo playerInfo = HttpProxyOutboundHandler.createProxy(GamePlayerInfo.class,ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        Game1ServerPlayerDto playerDto = playerInfo.getGame1Player(uid);
        if(playerDto == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        player = new Player(playerDto);
        player.setScenesId(scenesId);
        Room room = RoomServerModel.getInstance().getRoom(scenesId);
        if(room == null){
            room = RoomServerModel.getInstance().createRoom(uid,scenesId);
        }
        PlayerServerModel.getInstance().putPlayer(player);
        return room.enter(player);
    }
}
