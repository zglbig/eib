package org.zgl.db.logic.client.service;

import org.springframework.stereotype.Controller;
import org.zgl.db.dao.entity.Bank;
import org.zgl.db.dao.entity.Player;
import org.zgl.db.dao.entity.Weath;
import org.zgl.db.dao.mapper.BankMapper;
import org.zgl.db.logic.Operation;
import org.zgl.db.player.PlayerServerModel;
import org.zgl.db.utils.SpringUtils;
import org.zgl.desc.ClinetProxy;
import org.zgl.dto.clinet.hall.BankDto;
import org.zgl.error.AppErrorCode;
import org.zgl.error.AppGeneralError;
import org.zgl.service.client.db.BankOperation;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
@ClinetProxy
@Controller
public class BankOperationImpl extends Operation implements BankOperation {
    @Override
    public BankDto save(long uid, long gold) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        Weath weath = player.getWeath();
        if(!weath.reduceGold(gold)){
            new AppGeneralError(AppErrorCode.GOLD_COUNT_INSUFFICIENT);
        }
        BankMapper mapper = SpringUtils.getBean(BankMapper.class);
        Bank bank = mapper.queryBankUid(uid);
        if(bank == null){
            bank = new Bank();
            bank.setUid(uid);
            bank.setGold(gold);
            mapper.insertBank(bank);
        }else {
            long goldTemp = bank.getGold();
            bank.setGold(gold + goldTemp);
            bank.updateBank();
        }
        weath.updateGold();
        return new BankDto(gold,weath.getGold(),bank.getGold());
    }

    @Override
    public BankDto draw(long uid, long gold) {
        Player player = PlayerServerModel.getInstance().getPlayerByPlayerId(uid);
        if(player == null){
            new AppGeneralError(AppErrorCode.ACCOUNT_IS_NOT_LOGIN);
        }
        Weath weath = player.getWeath();
        BankMapper mapper = SpringUtils.getBean(BankMapper.class);
        Bank bank = mapper.queryBankUid(uid);
        if(bank == null){
            new AppGeneralError(AppErrorCode.BANK_NOT_GOLD);
        }
        if(bank.getGold() < gold){
            new AppGeneralError(AppErrorCode.BANK_GOLD_INSUFFICIENT,bank.getGold(),gold);
        }
        long goldTemp = bank.getGold();
        goldTemp -= gold;
        bank.setGold(goldTemp);
        weath.insertGold(gold);
        bank.updateBank();
        weath.updateGold();
        return new BankDto(gold,weath.getGold(),bank.getGold());
    }
}
