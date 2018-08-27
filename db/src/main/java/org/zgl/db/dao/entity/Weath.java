package org.zgl.db.dao.entity;

import org.zgl.db.dao.mapper.WeathMapper;
import org.zgl.db.utils.SpringUtils;
import org.zgl.dto.ItemDto;

import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/31
 * @文件描述：数据库表
 */
public class Weath {
    private Integer id;
    /**
     * 关联用户的uid
     */
    private Long uid;
    private Long gold;

    private Long diamond;
    private Long integral;
    private Integer vipLv;
    private Long vipExp;
    private Integer useAutoId;
    /**
     * 魅力值
     */
    private Long charmNum;
    private Boolean firstBuy;
    private Integer firstBuyTime;
    /**
     * 扩展对象 json
     */
    private String extend;
    private ReentrantReadWriteLock weathLock = new ReentrantReadWriteLock();

    public ReentrantReadWriteLock getWeathLock() {
        return weathLock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Long getDiamond() {
        return diamond;
    }

    public void setDiamond(Long diamond) {
        this.diamond = diamond;
    }

    public Long getIntegral() {
        return integral;
    }

    public void setIntegral(Long integral) {
        this.integral = integral;
    }

    public Integer getVipLv() {
        return vipLv;
    }

    public void setVipLv(Integer vipLv) {
        this.vipLv = vipLv;
    }

    public Long getVipExp() {
        return vipExp;
    }

    public void setVipExp(Long vipExp) {
        this.vipExp = vipExp;
    }

    public Integer getUseAutoId() {
        return useAutoId;
    }

    public void setUseAutoId(Integer useAutoId) {
        this.useAutoId = useAutoId;
    }

    public Long getCharmNum() {
        return charmNum;
    }

    public void setCharmNum(Long charmNum) {
        this.charmNum = charmNum;
    }

    public Boolean getFirstBuy() {
        return firstBuy;
    }

    public void setFirstBuy(Boolean firstBuy) {
        this.firstBuy = firstBuy;
    }

    public Integer getFirstBuyTime() {
        return firstBuyTime;
    }

    public void setFirstBuyTime(Integer firstBuyTime) {
        this.firstBuyTime = firstBuyTime;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }
    /**
     * 这里初始化主要是防止插入数据库的额时候数据为空取出来的时候就报错
     */
    public void initWeath() {
        this.uid = 0L;
        this.gold = 0L;
        this.diamond = 0L;
        this.integral = 0L;
        this.vipExp = 0L;
        this.vipLv = 0;
        this.useAutoId = 0;
        this.charmNum = 0L;
        this.firstBuy = false;
        this.firstBuyTime = 0;
    }
    public void insertCharm(long count){
        this.charmNum += count;
    }
    public List<ItemDto> insertWeath(List<ItemDto> items) {
        this.weathLock.writeLock().lock();
        try {
            for (ItemDto item : items) {
                switch (item.getItemId()) {
                    case 1:
                        this.gold += item.getItemCount();
                        item.setItemCount(this.gold);
                        break;
                    case 2:
                        this.diamond += item.getItemCount();
                        item.setItemCount(this.diamond);
                        break;
                    case 3:
                        this.integral += item.getItemCount();
                        item.setItemCount(this.integral);
                    case 4:
                        this.charmNum += item.getItemCount();
                        item.setItemCount(this.charmNum);
                        break;
                    case 10:
                        //座驾
                        break;
                    //vip要另外算
                    default:
                        break;
                }
            }
            return items;
        } finally {
            this.weathLock.writeLock().unlock();
        }
    }

    public List<ItemDto> reduceWeath(List<ItemDto> items) {
        this.weathLock.writeLock().lock();
        try {
            for (ItemDto item : items) {
                switch (item.getItemId()) {
                    case 1:
                        this.gold -= item.getItemCount();
                        item.setItemCount(this.gold);
                        break;
                    case 2:
                        this.diamond -= item.getItemCount();
                        item.setItemCount(this.diamond);
                        break;
                    case 3:
                        this.integral -= item.getItemCount();
                        item.setItemCount(this.integral);
                    case 4:
                        this.charmNum -= item.getItemCount();
                        item.setItemCount(this.charmNum);
                        break;
                    default:
                        break;
                }
            }
            return items;
        } finally {
            this.weathLock.writeLock().unlock();
        }
    }

    public boolean insertGold(long count) {
        weathLock.writeLock().lock();
        try {
            this.gold += count;
            return true;
        } finally {
            weathLock.writeLock().unlock();
        }
    }

    public boolean reduceGold(long count) {
        weathLock.writeLock().lock();
        try {
            if (this.gold >= count) {
                this.gold -= count;
                return true;
            } else {
                return false;
            }
        } finally {
            weathLock.writeLock().unlock();
        }
    }

    public boolean checkWeath(int productId, long count) {
        weathLock.writeLock().lock();
        try {
            switch (productId) {
                case 1:
                    if (this.gold < count) {
                        return false;
                    } else {
                        this.gold -= count;
                        return true;
                    }
                case 2:
                    if (this.diamond < count) {
                        return false;
                    } else {
                        this.diamond -= count;
                        return true;
                    }
                case 3:
                    if (this.integral < count) {
                        return false;
                    } else {
                        this.integral -= count;
                        return true;
                    }
                default:
                    return false;
            }
        } finally {
            weathLock.writeLock().unlock();
        }
    }
    public boolean updateGold(){
        WeathMapper mapper = SpringUtils.getBean(WeathMapper.class);
        Weath weath = new Weath();
        weath.setId(this.getId());
        weath.setGold(this.getGold());
        return mapper.updateWeath(weath) == 1;
    }
    public boolean updateWeath(){
        WeathMapper mapper = SpringUtils.getBean(WeathMapper.class);
        return mapper.updateWeath(this) == 1;
    }

    @Override
    public String toString() {
        return "Weath{" +
                "id=" + id +
                ", uid=" + uid +
                ", gold=" + gold +
                ", diamond=" + diamond +
                ", integral=" + integral +
                ", vipLv=" + vipLv +
                ", vipExp=" + vipExp +
                ", useAutoId=" + useAutoId +
                ", charmNum=" + charmNum +
                ", firstBuy=" + firstBuy +
                ", firstBuyTime=" + firstBuyTime +
                ", extend='" + extend + '\'' +
                ", weathLock=" + weathLock +
                '}';
    }
}
