package org.zgl.service.server.commond;

import org.zgl.dto.server.*;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/6
 * @文件描述：
 */

public interface GamePlayerInfo extends IHttpSyncService {
    Game1ServerPlayerDto getGame1Player(long uid);
    GateServerPlayerDto getGatePlayer(long uid);
    Game2ServerPlayerDto getGame2Player(long uid);
    HallServerPlayerDto getHallPlayer(long uid);
    GameLotteryServerPlayerDto getLotteryPlayer(long uid);
    GameDiceServerPlayerDto getDicePlayer(long uid);
    boolean checkPlayerIsOnline(long uid);
}
