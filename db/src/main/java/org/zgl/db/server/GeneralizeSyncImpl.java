package org.zgl.db.server;

import org.springframework.stereotype.Controller;
import org.zgl.DateUtils;
import org.zgl.db.dao.entity.Generalize;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.dao.entity.Weath;
import org.zgl.db.dao.mapper.GeneralizeMapper;
import org.zgl.db.dao.mapper.PlayerMapper;
import org.zgl.db.dao.mapper.WeathMapper;
import org.zgl.db.logic.Operation;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.db.utils.SpringUtils;
import org.zgl.desc.ServerProxy;
import org.zgl.dto.ItemDto;
import org.zgl.dto.clinet.commond.GoldBaseDto;
import org.zgl.dto.clinet.hall.GeneralizeAwardDto;
import org.zgl.dto.clinet.hall.GeneralizeDto;
import org.zgl.dto.clinet.hall.GeneralizeListDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.service.server.db.GeneralizeSync;

import java.util.ArrayList;
import java.util.List;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：推广同步到大厅
 */
@ServerProxy
@Controller
public class GeneralizeSyncImpl extends Operation implements GeneralizeSync {
    @Override
    public GeneralizeListDto openGeneralizeList(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }

        GeneralizeMapper generalizeDao = SpringUtils.getBean(GeneralizeMapper.class);
        List<Generalize> generalizes = generalizeDao.queryGeneralizeByUid(uid);
        List<GeneralizeDto> generalizeDtos = new ArrayList<>(generalizes.size());
        long allAward = 0;
        for(Generalize d: generalizes){
            allAward += d.getAllAward()==null ? 0 : d.getAllAward();
            generalizeDtos.add(new GeneralizeDto(d.getTargetUserName(),d.getAward(),d.getCreateTime()));
        }
        //推广人的昵称
        String userName = "";
        if(player.getGeneralizeUid() != null && player.getGeneralizeUid() != 0){
            Player other = PlayerServerModel.getInstance().getPlayerByPlayerId(player.getGeneralizeUid());
            if(other != null){
                userName = other.getUserName();
            }else {
                PlayerMapper dao = SpringUtils.getBean(PlayerMapper.class);
                Player targetDb = dao.queryDBUserByUid(player.getGeneralizeUid());
                if(targetDb != null){
                    userName = targetDb.getUserName();
                }
            }
        }
        return new GeneralizeListDto(allAward,userName,generalizeDtos);
    }

    @Override
    public GeneralizeAwardDto invite(long uid, long targetUid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        PlayerMapper userDao = SpringUtils.getBean(PlayerMapper.class);

        Player other = PlayerServerModel.getInstance().getPlayerByPlayerId(targetUid);
        if(other == null) {
            other = userDao.queryDBUserByUid(targetUid);
            if(other == null) {
                new AppGeneralError(AppErrorCode.NOT_PLAYER);
            }
        }
        if(other.getGeneralizeUid() != null && other.getGeneralizeUid() != 0) {
            new AppGeneralError(AppErrorCode.DO_NOT_EACH_OHTER_INVITION);
        }
        if(other.getGeneralizeUid() != null && other.getGeneralizeUid().equals(player.getGeneralizeUid())) {
            new AppGeneralError(AppErrorCode.NOT_EACH_OHTER_INVITION);
        }
        Weath selfWeath = player.getWeath();
        Weath otherWeath = other.getWeath();
        selfWeath.insertGold(20000000);
        //更新数据库
        selfWeath.updateGold();
        player.setGeneralizeUid(targetUid);
        //自己的数据

        //推广人的数据
        otherWeath.insertGold(5000000);
        //更新数据库
        otherWeath.updateGold();

        Generalize generalize = new Generalize();
        generalize.setAllAward(0L);
        generalize.setAward(0L);
        generalize.setCreateTime(DateUtils.nowTime());
        generalize.setNum(1);
        generalize.setSelfUid(targetUid);
        generalize.setTargetUid(uid);
        generalize.setTargetUserName(other.getUserName());
        GeneralizeMapper mapper = SpringUtils.getBean(GeneralizeMapper.class);
        mapper.insertGeneralize(generalize);
        GeneralizeAwardDto dto = new GeneralizeAwardDto();
        dto.setSelfGetGold(20000000);
        dto.setSelfHoldGold(selfWeath.getGold());
        dto.setOtherGetGold(5000000);
        dto.setOtherHoldGold(otherWeath.getGold());
        return dto;
    }

    @Override
    public GoldBaseDto receiveAward(long uid) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        GeneralizeMapper generalizeDao = SpringUtils.getBean(GeneralizeMapper.class);
        List<Generalize> generalizes = generalizeDao.queryGeneralizeByUid(uid);
        Weath weath = player.getWeath();
        boolean isUpdate = false;
        long gold = 0;
        for (Generalize g : generalizes) {
            long award = g.getAward();
            if(award <= 0) {
                continue;
            }
            gold += award;
            weath.insertGold(award);
            g.setAward(0L);
            generalizeDao.updateGeneralize(g);
            isUpdate = true;
        }
        if(isUpdate){
            weath.updateGold();
        }
        return new GoldBaseDto(gold,weath.getGold());
    }
}
