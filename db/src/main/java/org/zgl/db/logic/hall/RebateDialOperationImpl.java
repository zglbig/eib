package org.zgl.db.logic.hall;

import org.springframework.stereotype.Component;
import org.zgl.build.excel.read.StaticConfigMessage;
import org.zgl.datable.RebateDialDataTable;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.dao.entity.RebateDial;
import org.zgl.db.dao.entity.Weath;
import org.zgl.db.dao.mapper.RebateDialMapper;
import org.zgl.db.logic.Operation;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.db.server.PlayerWeathUpdateImpl;
import org.zgl.db.server.UserLogOperationImpl;
import org.zgl.db.utils.SpringUtils;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.ItemDto;
import org.zgl.dto.ItemListDto;
import org.zgl.dto.clinet.db.RebateDialDto;
import org.zgl.dto.clinet.db.RebateDialInfoDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.service.client.hall.RebateDialOperation;
import org.zgl.weightRandom.IWeihtRandom;
import org.zgl.weightRandom.WeightRandom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：返利轮盘
 */
@ClinetProxy
@Component
public class RebateDialOperationImpl extends Operation implements RebateDialOperation {
    @Override
    public RebateDialDto luckyDraw(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        RebateDialMapper mapper = SpringUtils.getBean(RebateDialMapper.class);
        RebateDial dial = mapper.queryRebateDialUid(uid);
        if(dial == null){
            new AppGeneralError(AppErrorCode.REBATE_DIAL_NOT_AWARD);
        }
        if(!dial.canAward()){
            new AppGeneralError(AppErrorCode.REBATE_DIAL_NOT_AWARD);
        }
        Weath weath = player.getWeath();
        //获取所有奖项
        Map<Serializable,Object> map = StaticConfigMessage.getInstance().getMap(RebateDialDataTable.class);
        //权重随机并返回获奖位置
        List<IWeihtRandom> iWeihtRandoms = new ArrayList<>(map.size());
        for(Object iwr : map.values()){
            iWeihtRandoms.add((IWeihtRandom) iwr);
        }
        int position = WeightRandom.awardPosition(iWeihtRandoms);
        //获取奖项位置的奖励物品
        RebateDialDataTable dataTable = RebateDialDataTable.get(position);
        dial.award();
        dial.update();
        List<ItemDto> items = new ArrayList<>();
        items.add(new ItemDto(1,dataTable.getNum()));
        items = weath.insertWeath(items);
        weath.updateWeath();
        RebateDialDto dialDto = new RebateDialDto(position,dataTable.getAwardId(),items);
        //用户日志
        userLog(uid,dataTable.getNum());
        //这里还要同步到其他房间这个人金币变化了
        PlayerWeathUpdateImpl.updateWeathByOtherScenes(player,new ItemListDto(items));
        return dialDto;
    }

    @Override
    public RebateDialInfoDto dialInfo(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        RebateDial dial = player.getRebateDial();
        if(dial == null){
            RebateDialMapper mapper = SpringUtils.getBean(RebateDialMapper.class);
            dial = mapper.queryRebateDialUid(uid);
            if(dial != null){
                player.setRebateDial(dial);
            }
        }
        RebateDialInfoDto dto = null;
        if(dial != null){
            dial.reset();
            dto = new RebateDialInfoDto();
            dto.setAhRoonBetNum(dial.getLottery());
            dto.setTopUpNum(dial.getTopUp());
            dto.setDiceRoomNum(dial.getDice());
            dto.setToRoomBetNum(dial.getToRoom());
            dto.setCanGetCount(dial.awardCount());
            dto.setGetCounted(dial.luckyDrawed());
            dto.setBetAllCount(RebateDial.DIAL_COUNT_LIMIT);
        }else {
            dto = new RebateDialInfoDto().init();
        }
        return dto;
    }
    private void userLog(long uid,long exhcangeGold){
        UserLogOperationImpl impl = new UserLogOperationImpl();
        impl.submitLog(0,"not card",uid,exhcangeGold,"返利轮盘获得","not card");
    }
}
