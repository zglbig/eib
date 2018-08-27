package org.zgl.hall.logic.generalize;

import org.springframework.stereotype.Controller;
import org.zgl.HttpProxyOutboundHandler;
import org.zgl.config.ConfigFactory;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.commond.GoldBaseDto;
import org.zgl.dto.clinet.hall.GeneralizeAwardDto;
import org.zgl.dto.clinet.hall.GeneralizeListDto;
import org.zgl.hall.logic.Operation;
import org.zgl.hall.logic.player.Player;
import org.zgl.hall.logic.player.PlayerServerModel;
import org.zgl.hall.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.hall.HallGeneralizeNotify;
import org.zgl.service.server.commond.GamePlayerInfo;
import org.zgl.service.server.db.GeneralizeSync;
import org.zgl.service.server.hall.GeneralizeOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：推广系统
 */
@ClinetProxy
@Controller
public class GeneralizeOperationImpl extends Operation implements GeneralizeOperation {
    @Override
    public GeneralizeListDto openGeneralizeList() {
        GeneralizeSync sync = HttpProxyOutboundHandler.createProxy(GeneralizeSync.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        return sync.openGeneralizeList(player.getUid());
    }

    @Override
    public GeneralizeAwardDto invite(long targetUid) {
        Player other = PlayerServerModel.getInstance().getPlayerByPlayerId(targetUid);
        boolean isOnline = false;
        if (other == null) {
            GamePlayerInfo info = HttpProxyOutboundHandler.createProxy(GamePlayerInfo.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
            isOnline = info.checkPlayerIsOnline(targetUid);
        }
        GeneralizeSync sync = HttpProxyOutboundHandler.createProxy(GeneralizeSync.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        GeneralizeAwardDto dto = sync.invite(player.getUid(), targetUid);
        if (isOnline || other != null) {
            List<Long> uids = new ArrayList<>();
            uids.add(targetUid);
            //通知推广人有玩家填了你的邀请码
            HallGeneralizeNotify notify = TcpProxyOutboundHandler.createProxy(HallGeneralizeNotify.class, uids);
            notify.notifyInvite(dto);
        }
        return dto;
    }

    @Override
    public GoldBaseDto receiveAward() {
        GeneralizeSync sync = HttpProxyOutboundHandler.createProxy(GeneralizeSync.class, ConfigFactory.getInstance().getSocketUrlCfg().getDbHttp());
        return sync.receiveAward(player.getUid());
    }
}
