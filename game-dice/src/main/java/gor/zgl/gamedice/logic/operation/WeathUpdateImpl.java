package gor.zgl.gamedice.logic.operation;

import org.springframework.stereotype.Controller;
import org.zgl.desc.ServerProxy;
import org.zgl.dto.ItemListDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.gamedice.logic.player.Player;
import org.zgl.gamedice.logic.player.PlayerServerModel;
import org.zgl.gamedice.logic.room.Room;
import org.zgl.gamedice.logic.room.RoomFactory;
import org.zgl.gamedice.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.dice.GameDicePlayerOperationRoomNotify;
import org.zgl.service.server.commond.WeathUpdate;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
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
        GameDicePlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameDicePlayerOperationRoomNotify.class,room.getPlayerSet().getAllPlayerUid());
        //同步房间其他成员
        notify.playerWeathUpdate(uid,itemList);
    }
}
