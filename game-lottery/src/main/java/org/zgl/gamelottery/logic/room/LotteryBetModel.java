package org.zgl.gamelottery.logic.room;

import org.zgl.datable.CardDataTable;
import org.zgl.gamelottery.logic.operation.UserLog;
import org.zgl.gamelottery.logic.player.Player;
import org.zgl.type.CardEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LotteryBetModel {
    /**
     * 玩家和下注注码
     */
    private Map<Player, Integer> players;
    private AtomicInteger allGold;
    private int position;

    public LotteryBetModel() {
    }

    public LotteryBetModel(int position) {
        allGold = new AtomicInteger(0);
        players = new ConcurrentHashMap<>();
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public boolean removePlayerCacheByUid(long uid) {
        if (players.containsKey(uid)) {
            players.remove(uid);
            return true;
        }
        return false;
    }

    public long hasBet(long uid) {
        if (players.containsKey(uid)) {
            return players.get(uid);
        }
        return 0;
    }

    public List<Player> getAllPlayer() {
        return new ArrayList<>(players.keySet());
    }

    public void bet(Player player, int count/**下注的注数*/) {
        allGold.addAndGet(count);
        int num = count;
        if (players.containsKey(player)) {
            num += players.get(player);
        }
        players.put(player, num);
    }

    public long getAllGold() {
        long all = allGold.get() * GamblingParty.BET_COUNT;
        allGold.set(0);
        players.clear();
        return all;
    }
    public long settleAccount(float rate, Map<Long, Long> resultMap, List<Integer> cardIds,int cardType) {
        long allMo = (long) (allGold.get() * rate);
        allMo -= allGold.get() * GamblingParty.BET_COUNT;
        String cardDesc = cardDesc(cardIds);
        for (Map.Entry<Player, Integer> e : players.entrySet()) {
            Player p = e.getKey();
            //获得乐这么多钱
            long resultMoney = (long) (e.getValue() * rate);
            long lo = (long) (resultMoney - resultMoney * 0.05F);
            p.insertGold(lo);
            UserLog.userLog(p.getUid(),CardEnum.getEnum(cardType).desc(),resultMoney,"在天天乐"+position+"位置结算",cardDesc);
            long temp = resultMoney;
            long uid = p.getUid();
            if (resultMap.containsKey(uid)) {
                temp += resultMap.get(uid);
            }
            //这里记录是用来通知自己总的中乐多少钱
            resultMap.put(uid, temp);
        }
        clear();
        return allMo;
    }
    private String cardDesc(List<Integer> cardIds){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0;i<cardIds.size();i++){
            CardDataTable dataTable = CardDataTable.get(cardIds.get(i));
            sb.append(dataTable.getDesc());
            if(i < cardIds.size() -1){
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    private void clear() {
        allGold.set(0);
        players.clear();
    }
}
