package org.zgl.hall.logic.friend;

import org.springframework.stereotype.Controller;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.datable.CommodityDataTable;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.hall.PawnshopDto;
import org.zgl.dto.server.HallServerPlayerDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.hall.logic.Operation;
import org.zgl.hall.logic.UserLog;
import org.zgl.hall.logic.player.Player;
import org.zgl.hall.logic.player.PlayerServerModel;
import org.zgl.hall.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.hall.HallGiftNotify;
import org.zgl.service.server.commond.GamePlayerInfo;
import org.zgl.service.server.commond.PlayerWeathUpdate;
import org.zgl.service.server.hall.GiftOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/20
 * @文件描述：
 */
@Controller
@ClinetProxy
public class GiveGiftOperationImpl extends Operation implements GiftOperation {
    @Override
    public long giveGifts(long targetUid, int giftId, int count) {
        gift(targetUid, giftId, count);
        //这里还要加人品值
        //通知收到礼物
        List<Long> uids = new ArrayList<>(1);
        uids.add(targetUid);
        HallGiftNotify notify = TcpProxyOutboundHandler.createProxy(HallGiftNotify.class, uids);
        notify.getPresents(giftId, count, player.getUid(), player.getUserName());
        return player.getGold();
    }

    @Override
    public long thank(long targetUid) {
        gift(targetUid, 20, 1);
        //这里还要加人品值
        //通知收到礼物
        List<Long> uids = new ArrayList<>(1);
        uids.add(targetUid);
        HallGiftNotify notify = TcpProxyOutboundHandler.createProxy(HallGiftNotify.class, uids);
        notify.thank(20, 1, player.getUid(), player.getUserName());
        return player.getGold();
    }

    @Override
    public PawnshopDto pawnshop(int id, int count) {
        //检查有没有这么多礼物
        PlayerWeathUpdate update = HttpProxyOutboundHandler.createProxy(PlayerWeathUpdate.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        PawnshopDto dto = update.pawnshop(player.getUid(), id, count);
        //检查礼物售价在减去百分之20得到最终价格
        return dto;
    }

    private void gift(long targetUid, int giftId, int count) {
        CommodityDataTable dataTable = CommodityDataTable.get(giftId);
        if (dataTable == null) {
            new AppGeneralError(AppErrorCode.GIFT_NOT_ERROR);
        }
        Player other = PlayerServerModel.getInstance().getPlayerByPlayerId(targetUid);
        if (other == null) {
            GamePlayerInfo info = HttpProxyOutboundHandler.createProxy(GamePlayerInfo.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
            HallServerPlayerDto dto = info.getHallPlayer(targetUid);
            if (dto == null) {
                new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
            }
            other = new Player().getPlayer(dto);
            PlayerServerModel.getInstance().putPlayer(other);
        }
        if (!player.reduceGold(dataTable.getSelling())) {
            new AppGeneralError(AppErrorCode.GOLD_NOT_INSUFFICIENT);
        }
        UserLog.userLog(player.getUid(), dataTable.getSelling(), "给uid为" + other.getUid() + "昵称为" + other.getUserName() + "发了" + count + "个" + dataTable.getName() + "的礼物");
        other.insertGift(giftId, count * dataTable.getCount(), Long.valueOf(dataTable.getEffect()));
        UserLog.userLog(player.getUid(), dataTable.getSelling(), "收到uid为" + player.getUid() + "昵称为" + player.getUserName() + "发发" + count + "个" + dataTable.getName() + "的礼物");
    }
}
