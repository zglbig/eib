package org.zgl.game2.logic.operation;

import org.springframework.stereotype.Component;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.game2.Game2PlayerRoomDto;
import org.zgl.dto.server.Game2ServerPlayerDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.game2.logic.player.Player;
import org.zgl.game2.logic.player.PlayerServerModel;
import org.zgl.game2.logic.room.Room;
import org.zgl.game2.logic.room.RoomFactory;
import org.zgl.service.server.commond.GamePlayerInfo;
import org.zgl.service.server.game2.Game2HttpRoomOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/6
 * @文件描述：
 */
@Component
@ClinetProxy
public class Game2HttpRoomOperationImpl implements Game2HttpRoomOperation {
    @Override
    public Game2PlayerRoomDto enterRoom(long uid, int scenesId) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player != null){
            //该账号已经登陆
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_ONLINE_ERR);
        }
        //从db服务器中获取该玩家数据如果没有则是该账号尚未登陆
        GamePlayerInfo playerInfo = HttpProxyOutboundHandler.createProxy(GamePlayerInfo.class,ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        Game2ServerPlayerDto playerDto = playerInfo.getGame2Player(uid);
        if(playerDto == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        player = new Player(playerDto);
        player.setScenesId(scenesId);
        Room room = RoomFactory.getInstance().getRoom();
        if(room == null){
           //房间异常
        }
        PlayerServerModel.getInstance().putPlayer(player);
        return room.enter(player);
    }
}
