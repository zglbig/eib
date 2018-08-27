package org.zgl.service.server.game2;
import org.zgl.dto.clinet.game2.Game2PlayerRoomDto;
import org.zgl.proxy.ClassDesc;
import org.zgl.proxy.MethodDesc;
import org.zgl.proxy.rule.IHttpSyncService;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/6
 * @文件描述：
 */
@ClassDesc("房间http操作")
public interface Game2HttpRoomOperation extends IHttpSyncService {
    @MethodDesc("进入房间")
    Game2PlayerRoomDto enterRoom(long uid, int scenesId);
}
