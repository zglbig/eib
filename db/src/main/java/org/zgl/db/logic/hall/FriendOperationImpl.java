package org.zgl.db.logic.hall;

import org.springframework.stereotype.Component;
import org.zgl.db.dao.entity.Friends;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.dao.mapper.FriendsMapper;
import org.zgl.db.logic.Operation;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.db.utils.SpringUtils;
import org.zgl.desc.ServerProxy;
import org.zgl.dto.clinet.commond.BasePlayerDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.service.server.hall.FriendOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@ServerProxy
@Component
public class FriendOperationImpl extends Operation implements FriendOperation {
    @Override
    public BasePlayerDto consent(long selfUid,long otherUid) {
        Player self = PlayerServerModel.getInstance().getPlayerByPlayerId(selfUid);
        if(self == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        Player other = PlayerServerModel.getInstance().getPlayerByPlayerId(otherUid);
        if(other == null){
            new AppGeneralError(AppErrorCode.OTHER_NOT_ONLINE);
        }
        FriendsMapper friends = SpringUtils.getBean(FriendsMapper.class);
        //给自己好友列表加好友
        Friends f1 = friends(other,selfUid);
        friends.insertFriends(f1);
        //给别人好友列表加好友
        f1 = friends(self,otherUid);
        friends.insertFriends(f1);
        //给自己添加好友
        //给对方添加好友
        BasePlayerDto dto = new BasePlayerDto();
        dto.setUid(other.getUid());
        dto.setUserName(other.getUserName());
        dto.setHeadImgUrl(other.getHeadImgUrl());
        dto.setVipLv(other.getWeath().getVipLv());
        return dto;
    }
    private Friends friends(Player p,long selfUid){
        Friends friends = new Friends();
        friends.setUid(selfUid);
        friends.setFriendUid(p.getUid());
        friends.setUserName(p.getUserName());
        friends.setHeadUrl(p.getHeadImgUrl());
        friends.setVipLv(p.getWeath().getVipLv());
        friends.setExtend("{}");
        return friends;
    }
}
