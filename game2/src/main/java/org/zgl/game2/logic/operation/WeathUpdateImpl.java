package org.zgl.game2.logic.operation;

import org.springframework.stereotype.Controller;
import org.zgl.desc.ServerProxy;
import org.zgl.dto.ItemListDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.game2.logic.player.Player;
import org.zgl.game2.logic.player.PlayerServerModel;
import org.zgl.game2.logic.room.Room;
import org.zgl.game2.logic.room.RoomFactory;
import org.zgl.game2.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.game2.Game2PlayerOperationRoomNotify;
import org.zgl.service.server.commond.WeathUpdate;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/15
 * @文件描述：
 */
@ServerProxy
@Controller
public class WeathUpdateImpl implements WeathUpdate {
    @Override
    public void updateWeath(long uid, ItemListDto itemList) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.THIS_ROOM_IS_NOT_PLAYER);
        }
        Room room = RoomFactory.getInstance().getRoom();
        if(room == null){
            new AppGeneralError(AppErrorCode.THIS_ROOM_IS_NOT_PLAYER);
        }
        player.updateWeath(itemList.getItems());
        Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUid());
        //同步房间其他成员
        notify.playerWeathUpdate(uid,itemList);
    }
}