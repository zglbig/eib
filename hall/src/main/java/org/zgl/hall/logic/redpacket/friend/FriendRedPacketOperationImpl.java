package org.zgl.hall.logic.redpacket.friend;

import org.springframework.stereotype.Component;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.hall.RedPacketDto;
import org.zgl.dto.server.HallServerPlayerDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.hall.logic.Operation;
import org.zgl.hall.logic.UserLog;
import org.zgl.hall.logic.player.Player;
import org.zgl.hall.logic.player.PlayerServerModel;
import org.zgl.hall.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.hall.HallRedPacketNotify;
import org.zgl.service.server.commond.GamePlayerInfo;
import org.zgl.service.server.hall.FriendRedPacketOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
@Component
@ClinetProxy
public class FriendRedPacketOperationImpl extends Operation implements FriendRedPacketOperation {
    @Override
    public RedPacketDto giveRedEnvelopes(long targetUid, long gold) {
        //查看好友是否在线
        Player other = PlayerServerModel.getInstance().getPlayerByPlayerId(targetUid);
        if (other == null) {
            //到大厅去取
            GamePlayerInfo info = HttpProxyOutboundHandler.createProxy(GamePlayerInfo.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
            HallServerPlayerDto dto = info.getHallPlayer(targetUid);
            if (dto == null) {
                new AppGeneralError(AppErrorCode.OTHER_NOT_ONLINE);
            }
            other = new Player().getPlayer(dto);
            PlayerServerModel.getInstance().putPlayer(other);
        }
        //如果在线就通知他收到红包了
        if (!player.reduceGold(gold)) {
            new AppGeneralError(AppErrorCode.GOLD_NOT_INSUFFICIENT);
        }
        UserLog.userLog(player.getUid(), gold, "给uid为" + other.getUid() + "昵称为" + other.getUserName() + "发红包");

        RedPacketModel model = new RedPacketModel(player.getUid(), player.getUserName(), targetUid, other.getUserName(), gold);
        FriendRedPacketManager.getInstance().putMap(model);
        List<Long> uids = new ArrayList<>(1);
        uids.add(other.getUid());
        HallRedPacketNotify notify = TcpProxyOutboundHandler.createProxy(HallRedPacketNotify.class, uids);
        notify.recieveRedPacket(player.getUserName(), model.getId());
        return new RedPacketDto(player.getGold(), gold, "目标对象用户名");
    }

    @Override
    public RedPacketDto bonus(int id) {
        RedPacketModel model = FriendRedPacketManager.getInstance().getModel(player.getUid(), id);
        if (model == null) {
            new AppGeneralError(AppErrorCode.RED_PACKET_TIME_OUT);
        }
        player.insertGold(model.getGold());
        UserLog.userLog(player.getUid(), model.getGold(), "获得uid为" + model.getUid() + "昵称为" + model.getUserName() + "发的红包");
        FriendRedPacketManager.getInstance().remove(player.getUid(), id);

        List<Long> uids = new ArrayList<>(1);
        uids.add(model.getUid());
        HallRedPacketNotify notify = TcpProxyOutboundHandler.createProxy(HallRedPacketNotify.class, uids);
        notify.friendBonus(player.getUserName(), model.getId());
        //通知自己领取了谁的红包
        return new RedPacketDto(player.getGold(), model.getGold(), model.getUserName());
    }
}
