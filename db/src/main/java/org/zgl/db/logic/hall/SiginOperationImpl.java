package org.zgl.db.logic.hall;

import org.springframework.stereotype.Component;
import org.zgl.DateUtils;
import org.zgl.build.excel.read.StaticConfigMessage;
import org.zgl.datable.SiginDialDataTable;
import org.zgl.datable.SignInDataTable;
import org.zgl.datable.VipDataTable;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.dao.entity.Sigin;
import org.zgl.db.dao.entity.Weath;
import org.zgl.db.dao.mapper.SiginMapper;
import org.zgl.db.logic.Operation;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.db.utils.SpringUtils;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.ItemDto;
import org.zgl.dto.ItemListDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.service.client.db.SiginOperation;
import org.zgl.weightRandom.IWeihtRandom;
import org.zgl.weightRandom.WeightRandom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
@Component
@ClinetProxy
public class SiginOperationImpl extends Operation implements SiginOperation {
    @Override
    public ItemListDto sigin(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        Sigin sigin = player.getSigin();
        SiginMapper mapper = SpringUtils.getBean(SiginMapper.class);
        if(sigin == null){
            sigin = mapper.querySiginUid(uid);
            player.setSigin(sigin);
        }
        long todayTime = DateUtils.currentTime();
        int todayTemp = (int) (todayTime / (1000 * 3600 * 24));
        int modelDayTemp = (int) (sigin.getLastTimeSiginTime()/ (1000 * 3600 * 24));
        if (modelDayTemp >= todayTemp) {
            new AppGeneralError(AppErrorCode.TODAY_IS_SIGIN);
        }
        int day = sigin.getSiginDay();
        if (todayTemp - modelDayTemp != 1) {
            day = 0;
        }
        if (day >= 6) {
            day = 5;
        }
        day++;
        SignInDataTable dataTable = SignInDataTable.get(day);
        if (dataTable == null) {
            new AppGeneralError(AppErrorCode.SERVER_ERROR);
        }
        Weath weath = player.getWeath();
        //vip加成
        int vipSignIn = 0;
        VipDataTable vipDataTable = VipDataTable.get(weath.getVipLv());
        if (vipDataTable != null) {
            vipSignIn = vipDataTable.getSingIn();
        }
        long gold = dataTable.getGold();
        sigin.setSiginDay(day);
        sigin.setLastTimeSiginTime(todayTime);
        List<ItemDto> items = new ArrayList<>();
        items.add(new ItemDto(1,gold + vipSignIn * gold));
        items.add(new ItemDto(22,dataTable.getChangeCard()));
        items = weath.insertWeath(items);
        //更新签到
        sigin.updateSigin();
        weath.updateWeath();
        return new ItemListDto(items);
    }

    @Override
    public ItemListDto dial(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        Sigin sigin = player.getSigin();
        SiginMapper mapper = SpringUtils.getBean(SiginMapper.class);
        if(sigin == null){
            sigin = mapper.querySiginUid(uid);
        }
        if(sigin.getDialUseNum() <= 0){
            new AppGeneralError(AppErrorCode.SIGIN_DIAL_COUNT_IS_NULL);
        }
        //获取所有奖项
        Map<Serializable,Object> map = StaticConfigMessage.getInstance().getMap(SiginDialDataTable.class);
        //权重随机并返回获奖位置
        List<IWeihtRandom> iWeihtRandoms = new ArrayList<>(map.size());
        for(Object iwr : map.values()){
            iWeihtRandoms.add((IWeihtRandom) iwr);
        }
        int position = WeightRandom.awardPosition(iWeihtRandoms);
        SiginDialDataTable dataTable = SiginDialDataTable.get(position);
        Weath weath = player.getWeath();
        List<ItemDto> items = new ArrayList<>();
        items.add(new ItemDto(dataTable.getAwardId(),dataTable.getCount()));
        items = weath.insertWeath(items);
        sigin.setDialUseNum(sigin.getDialUseNum() - 1);
        weath.updateWeath();
        sigin.updateSigin();
        return new ItemListDto(items);
    }
}
