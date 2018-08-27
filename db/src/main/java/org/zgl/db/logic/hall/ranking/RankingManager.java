package org.zgl.db.logic.hall.ranking;

import org.zgl.db.dao.entity.Player;
import org.zgl.db.dao.entity.Weath;
import org.zgl.db.dao.mapper.PlayerMapper;
import org.zgl.db.dao.mapper.WeathMapper;
import org.zgl.db.utils.SpringUtils;
import org.zgl.dto.clinet.db.RankingBaseDto;
import org.zgl.dto.clinet.db.RankingListDto;

import java.util.*;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
public class RankingManager {
    /**
     * 线程安全的懒汉式单利模式
     */
    private static class SingletonHolder {
        public final static RankingManager instance = new RankingManager();
    }
    public static RankingManager getInstance() {
        return SingletonHolder.instance;
    }
    private List<RankingBaseDto> gold;
    private List<RankingBaseDto> charm;

    private RankingManager() {
        this.gold = new ArrayList<>();
        this.charm = new ArrayList<>();
    }
    public void ranking(){
        PlayerMapper mapper = SpringUtils.getBean(PlayerMapper.class);
        List<Player> players = mapper.queryDBUserList(0);
        Map<Long,Player> playerMap = new HashMap<>(players.size());
        for(Player p:players){
            playerMap.put(p.getUid(),p);
            Weath w = p.getWeath();
            RankingBaseDto dto = new RankingBaseDto();
            dto.setUid(w.getUid());
            dto.setShowWeath(w.getGold());
            dto.setVipLv(w.getVipLv());
            dto.setUseAutoId(w.getUseAutoId());
            gold.add(dto);
            RankingBaseDto dto1 = new RankingBaseDto();
            dto1.setUid(w.getUid());
            dto1.setShowWeath(w.getCharmNum());
            dto1.setVipLv(w.getVipLv());
            dto1.setUseAutoId(w.getUseAutoId());
            charm.add(dto1);
        }
        Collections.sort(gold);
        Collections.sort(charm);
        int gSize = gold.size() > 20 ? 20 : gold.size();
        int cSize = charm.size() > 20 ? 20 : charm.size();
        gold = gold.subList(0,gSize);
        charm = charm.subList(0,cSize);
        for(RankingBaseDto d:gold){
            Player p = playerMap.get(d.getUid());
            d.setHeadImgUrl(p.getHeadImgUrl());
            d.setUserName(p.getUserName());
        }
        for(RankingBaseDto c:charm){
            Player p = playerMap.get(c.getUid());
            c.setHeadImgUrl(p.getHeadImgUrl());
            c.setUserName(p.getUserName());
        }
    }
    public RankingListDto gold(){
        return new RankingListDto(gold);
    }
    public RankingListDto charm(){
        return new RankingListDto(charm);
    }
}
