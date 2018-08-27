package org.zgl.db.logic.client.service;

import org.springframework.stereotype.Controller;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.dao.entity.Prop;
import org.zgl.db.logic.Operation;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.hall.GiftDto;
import org.zgl.dto.clinet.hall.PlayerInfoDto;
import org.zgl.dto.clinet.hall.PropDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.service.client.hall.HallPlayerInfoOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@Controller
@ClinetProxy
public class HallPlayerInfoOperationImpl extends Operation implements HallPlayerInfoOperation {
    @Override
    public PlayerInfoDto playerInfo(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        PlayerInfoDto dto = new PlayerInfoDto();
        dto.setUid(player.getUid());
        dto.setCharm(player.getWeath().getCharmNum());
        dto.setContactWay(player.getContactWay());
        dto.setDiamond(player.getWeath().getDiamond());
        dto.setGold(player.getWeath().getGold());
        dto.setVipLv(player.getWeath().getVipLv());
        dto.setHeadImgUrl(player.getHeadImgUrl());
        dto.setUserName(player.getUserName());
        dto.setSigin(player.getSignature());
        return dto;
    }
    @Override
    public GiftDto playerGiftInfo(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        Prop prop = player.getProp();
        GiftDto dto = new GiftDto();
        dto.setFlower(prop.getFlower());
        dto.setAircraft(prop.getAircraft());
        dto.setBlueSuccubus(prop.getBlueSuccubus());
        dto.setBomb(prop.getBomb());
        dto.setEgg(prop.getEgg());
        dto.setDiamondRing(prop.getDiamondRing());
        dto.setSportsCar(prop.getSportsCar());
        dto.setTower(prop.getTower());
        dto.setStreamer(prop.getStreamer());
        return dto;
    }
    @Override
    public PropDto playerPropInfo(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        Prop prop = player.getProp();
        PropDto dto = new PropDto(prop.getExchangeCard(),prop.getKickingCard(),prop.getTrumpetCard());
        return dto;
    }
}
