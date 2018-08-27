package org.zgl.hall.logic.redpacket.hall;

import org.springframework.stereotype.Controller;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.ItemDto;
import org.zgl.dto.clinet.hall.HallRedEnvelopePlayerDto;
import org.zgl.dto.clinet.hall.HallRedEnvelopePlayerListDto;
import org.zgl.dto.clinet.hall.HallRedPacketDto;
import org.zgl.dto.clinet.hall.HallRedPacketListDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.hall.logic.Operation;
import org.zgl.service.server.hall.HallRedPacketOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：
 */
@ClinetProxy
@Controller
public class HallRedPacketOperationImpl extends Operation implements HallRedPacketOperation {
    @Override
    public HallRedPacketListDto openHallList() {
        List<HallRedPacketDto> list = HallRedPacketManager.getInstance().getHallRedPacketList();
        int size = list.size() > 10 ? 10 : list.size();
        list.subList(0, size);
        return new HallRedPacketListDto(list);
    }

    @Override
    public ItemDto redEnvelope(short redType, int count, String desc) {
        long gold = count;
        player.reduceGold(gold);
        HallRedPacketDto dto = new HallRedPacketDto();
        long createTime = System.currentTimeMillis();
        dto.setId(createTime);
        dto.setRedPacketType(redType);
        dto.setUserName(player.getUserName());
        dto.setHeadImgUrl(player.getHeadImgUrl());
        dto.setCreateTime(createTime);
        dto.setDesc(desc);
        dto.setFinish(false);
        HallRedPacketModel model = new HallRedPacketModel();
        model.setDto(dto);
        model.setCount(count);
        model.setAllGold(gold);
        model.initCount();
        HallRedPacketManager.getInstance().put(model);
        return new ItemDto(1, player.getGold());
    }

    @Override
    public ItemDto recieve(long id) {
        HallRedPacketModel model = HallRedPacketManager.getInstance().getModel(id);
        if (model == null || model.getResidueCount().size() <= 0) {
            new AppGeneralError(AppErrorCode.RED_PACKET_RECEIVE_OVER);
        }
        HallRedPacketDto dto = model.getDto();
        if (dto.isFinish()) {
            new AppGeneralError(AppErrorCode.RED_PACKET_RECEIVE_OVER);
        }
        long gold = model.getGold();
        player.insertGold(gold);
        HallRedEnvelopePlayerDto dto1 = new HallRedEnvelopePlayerDto(player.getUserName(), player.getHeadImgUrl(), gold);
        List<HallRedEnvelopePlayerDto> list = model.getList();
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(dto1);
        return new ItemDto(1, player.getGold());
    }

    @Override
    public HallRedEnvelopePlayerListDto openOneRed(long id) {
        HallRedPacketModel model = HallRedPacketManager.getInstance().getModel(id);
        if (model == null || model.getResidueCount().size() <= 0) {
            new AppGeneralError(AppErrorCode.RED_PACKET_RECEIVE_OVER);
        }
        return new HallRedEnvelopePlayerListDto(model.getList());
    }
}
