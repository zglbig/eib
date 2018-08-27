package org.zgl.hall.logic.friend;

import org.springframework.stereotype.Component;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.commond.BasePlayerDto;
import org.zgl.dto.clinet.commond.ChatDto;
import org.zgl.dto.clinet.hall.FriendListDto;
import org.zgl.dto.server.HallServerPlayerDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.hall.logic.Operation;
import org.zgl.hall.logic.player.Player;
import org.zgl.hall.logic.player.PlayerServerModel;
import org.zgl.hall.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.game2.Game2PlayerOperationRoomNotify;
import org.zgl.service.client.hall.HallFriendNotify;
import org.zgl.service.client.hall.HallFriendOperation;
import org.zgl.service.server.commond.GamePlayerInfo;
import org.zgl.service.server.hall.FriendOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@Component
@ClinetProxy
public class HallFriendOperationImpl extends Operation implements HallFriendOperation {
    @Override
    public boolean addFriend(long targetUid) {
        if (player.hasFriend(targetUid)) {
            new AppGeneralError(AppErrorCode.THIS_PLAYER_IS_YOU_FRIEND);
        }
        Player friend = PlayerServerModel.getInstance().getPlayerByPlayerId(targetUid);
        if (friend == null) {
            GamePlayerInfo info = HttpProxyOutboundHandler.createProxy(GamePlayerInfo.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
            HallServerPlayerDto dto = info.getHallPlayer(targetUid);
            if (dto == null) {
                new AppGeneralError(AppErrorCode.OTHER_NOT_ONLINE);
            }
            friend = new Player().getPlayer(dto);
            PlayerServerModel.getInstance().putPlayer(friend);
        }
        FriendModelManager.getInstance().putMap(player.getUid(), targetUid);
        List<Long> uids = new ArrayList<>(1);
        uids.add(targetUid);
        HallFriendNotify notify = TcpProxyOutboundHandler.createProxy(HallFriendNotify.class, uids);
        notify.hasFriendRequest(targetUid, player.getUserName());
        return true;
    }

    @Override
    public boolean removeFriend(long targetUid) {
        if (player.hasFriend(targetUid)) {
            new AppGeneralError(AppErrorCode.PLAYER_NOT_IS_FRIEND);
        }
        return false;
    }

    @Override
    public BasePlayerDto consent(long targetUid) {
        //同意好友請求
        FriendModelManager.getInstance().remove(player.getUid(), targetUid);
        FriendOperation friendOperation = HttpProxyOutboundHandler.createProxy(FriendOperation.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        Player friend = PlayerServerModel.getInstance().getPlayerByPlayerId(targetUid);
        if (friend == null) {
            GamePlayerInfo info = HttpProxyOutboundHandler.createProxy(GamePlayerInfo.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
            HallServerPlayerDto dto = info.getHallPlayer(targetUid);
            if (dto == null) {
                new AppGeneralError(AppErrorCode.OTHER_NOT_ONLINE);
            }
            friend = new Player().getPlayer(dto);
            PlayerServerModel.getInstance().putPlayer(friend);
        }
        List<Long> uids = new ArrayList<>(1);
        uids.add(targetUid);
        HallFriendNotify notify = TcpProxyOutboundHandler.createProxy(HallFriendNotify.class, uids);
        BasePlayerDto dto = new BasePlayerDto();
        dto.setUserName(player.getUserName());
        dto.setUid(player.getUid());
        dto.setHeadImgUrl(player.getHeadImgUrl());
        dto.setVipLv(player.getVipLv());
        //通知自己的基本信息
        if (!player.hasFriend(targetUid)) {
            player.addFriend(dto);
        }
        notify.friendConsent(dto);
        //返回对方的基本信息
        BasePlayerDto selfb = friendOperation.consent(player.getUid(), targetUid);
        if (!friend.hasFriend(player.getUid())) {
            friend.addFriend(selfb);
        }
        return selfb;
    }

    @Override
    public FriendListDto friendList() {
        return new FriendListDto(player.getFriend());
    }

    @Override
    public boolean friendChat(long targetUid, int msgType, String msg) {
        if (!player.hasFriend(targetUid)) {
            new AppGeneralError(AppErrorCode.PLAYER_NOT_IS_FRIEND_CAN_NOT_CHAT);
        }
        ChatDto dto = new ChatDto();
        dto.setMsg(msg);
        dto.setMsgType(msgType);
        //1男 2女
        dto.setUid(player.getUid());
        dto.setHeadIcon(player.getHeadImgUrl());
        dto.setSex(player.getSex());
        dto.setUsername(player.getUserName());
        dto.setVipLv(player.getVipLv());
        List<Long> uids = new ArrayList<>(1);
        uids.add(targetUid);
        Game2PlayerOperationRoomNotify notifyOne = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class, uids);
        notifyOne.receiveChatMsg(dto);
        return true;
    }
}
