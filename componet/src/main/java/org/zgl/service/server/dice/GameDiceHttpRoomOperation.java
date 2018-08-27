package org.zgl.service.server.dice;

import org.zgl.dto.clinet.dice.DiceRoomInfiDto;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public interface GameDiceHttpRoomOperation extends IHttpSyncService {
    DiceRoomInfiDto enter(long uid);
}
