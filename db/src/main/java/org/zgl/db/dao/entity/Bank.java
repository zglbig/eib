package org.zgl.db.dao.entity;

import org.zgl.db.dao.mapper.BankMapper;
import org.zgl.db.utils.SpringUtils;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：银行
 */
public class Bank {
    private Long uid;
    private Long gold;
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }
    public boolean updateBank(){
        BankMapper mapper = SpringUtils.getBean(BankMapper.class);
        return mapper.updateBank(this) == 1;
    }
}
