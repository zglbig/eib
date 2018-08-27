package org.zgl.db.server;

import org.springframework.stereotype.Component;
import org.zgl.config.ConfigFactory;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.logic.Operation;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.desc.ServerProxy;
import org.zgl.dto.server.*;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.service.server.commond.GamePlayerInfo;
import org.zgl.type.ScenesEnum;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/6
 * @文件描述：
 */
@Component
@ServerProxy
public class GamePlayerInfoImpl extends Operation implements GamePlayerInfo {
    @Override
    public Game1ServerPlayerDto getGame1Player(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN,"用户尚未登陆");
        }
        player.getScenesIds().add(ConfigFactory.getInstance().getSocketUrlCfg().getGame1Http());
        return player.game1PlayerInfo();
    }

    @Override
    public GateServerPlayerDto getGatePlayer(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        return player.gatePlayerInfo();
    }

    @Override
    public Game2ServerPlayerDto getGame2Player(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        player.getScenesIds().add(ConfigFactory.getInstance().getSocketUrlCfg().getGame2Http());
        return player.game2ServerPlayerInfo();
    }

    @Override
    public HallServerPlayerDto getHallPlayer(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        return player.hallServerPlayerInfo();
    }

    @Override
    public GameLotteryServerPlayerDto getLotteryPlayer(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        return new GameLotteryServerPlayerDto(player.getUid(),player.getUserName(),player.getWeath().getGold());
    }

    @Override
    public GameDiceServerPlayerDto getDicePlayer(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        player.getScenesIds().add(ConfigFactory.getInstance().getSocketUrlCfg().getGameDiceHttp());
        return player.gameDiceServerPlayerInfo();
    }

    @Override
    public boolean checkPlayerIsOnline(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        return player != null;
    }
}
