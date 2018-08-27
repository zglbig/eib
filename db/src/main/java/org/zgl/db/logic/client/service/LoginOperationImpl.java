package org.zgl.db.logic.client.service;

import org.springframework.stereotype.Controller;
import org.zgl.DateUtils;
import org.zgl.IDFactory;
import org.zgl.db.dao.entity.Prop;
import org.zgl.db.dao.entity.Sigin;
import org.zgl.db.dao.mapper.PropMapper;
import org.zgl.db.dao.mapper.SiginMapper;
import org.zgl.db.logic.Operation;
import org.zgl.desc.ClinetProxy;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.dao.entity.Weath;
import org.zgl.db.dao.mapper.PlayerMapper;
import org.zgl.db.dao.mapper.WeathMapper;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.db.utils.SpringUtils;
import org.zgl.dto.clinet.db.LoginDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.service.client.db.LoginOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/31
 * @文件描述：
 */
@Controller
@ClinetProxy
public class LoginOperationImpl extends Operation implements LoginOperation {
    @Override
    public LoginDto regist(String account, String password,String userName, String headImgUrl, String sex, String version) {
        PlayerMapper playerDao = SpringUtils.getBean(PlayerMapper.class);
        Player player = playerDao.queryDBUserByAccount(account);
        LoginDto dto = null;
        if(player != null){
            if(PlayerServerModel.getInstance().isOnline(player.getUid())){
                //玩家已经在线
                new AppGeneralError(AppErrorCode.ACCOUNT_IS_ONLINE_ERR);
            }
            dto = player.getLoginDto();
            //账号已经注册
        }else {
            WeathMapper weathDao = SpringUtils.getBean(WeathMapper.class);
            Weath weath = new Weath();
            weath.initWeath();
            //插入并返回id
            weathDao.insertWeath(weath);

            SiginMapper siginMapper = SpringUtils.getBean(SiginMapper.class);
            Sigin sigin = new Sigin();
            sigin.init();
            siginMapper.insertSigin(sigin);

            PropMapper propMapper = SpringUtils.getBean(PropMapper.class);
            Prop prop = new Prop();
            prop.init();
            propMapper.insertProp(prop);
//            好友表
//            成长礼包表
//            任务表
//            签到表
            player = new Player();
            player.getPlayer(account,password,userName,headImgUrl,sex,addr);
            player.setWeath(weath);
            player.setProp(prop);
            player.setSigin(sigin);
            player.setCreateTime(DateUtils.nowTime());
            playerDao.insertDBUser(player);

            //拼接uid
            long uid = IDFactory.getId(player.getId());

            Weath weath1 = new Weath();
            weath1.setId(weath.getId());
            weath1.setUid(uid);
            weathDao.updateWeath(weath1);

            Sigin sigin1 = new Sigin();
            sigin1.setId(sigin.getId());
            sigin1.setUid(uid);
            siginMapper.updateSigin(sigin1);

            Prop prop1 = new Prop();
            prop1.setId(prop.getId());
            prop1.setUid(uid);
            propMapper.updateProp(prop1);

            //更新已经有uid的玩家信息
            Player player1 = new Player();
            player1.setId(player.getId());
            player1.setUid(uid);
            playerDao.updateDBUser(player1);
            //更新已经有玩家uid的财富表
            player.setUid(uid);

            dto = player.getLoginDto();
        }
        //添加到玩家在线列表
        PlayerServerModel.getInstance().putPlayer(player);
        return dto;
    }

    @Override
    public LoginDto login(String account, String password) {
        PlayerMapper playerDao = SpringUtils.getBean(PlayerMapper.class);
        Player player = playerDao.queryDBUserByAccount(account);
        LoginDto dto = null;
        if(player != null){
            if(PlayerServerModel.getInstance().isOnline(player.getUid())){
                //玩家已经在线
                new AppGeneralError(AppErrorCode.ACCOUNT_IS_ONLINE_ERR);
            }
            dto = player.getLoginDto();
        }else {
            //账号尚未注册
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_REGIST);
        }
        //添加到玩家在线列表
        PlayerServerModel.getInstance().putPlayer(player);
        return dto;
    }
}
