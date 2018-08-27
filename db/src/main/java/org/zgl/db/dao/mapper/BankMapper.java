package org.zgl.db.dao.mapper;
import org.zgl.db.dao.entity.Bank;
/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
public interface BankMapper {
    Bank queryBankUid(Long id);
    int updateBank(Bank sigin);
    int insertBank(Bank sigin);
    int deleteBankByUid(Long uid);
}
