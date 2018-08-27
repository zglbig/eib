package gor.zgl.gamedice.logic.room;

import gor.zgl.gamedice.logic.operation.UserLog;
import gor.zgl.gamedice.logic.player.Player;
import gor.zgl.gamedice.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.dice.GameDicePlayerOperationRoomNotify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 骰子位置记录
 */
public class DiceBetModel implements Comparable<DiceBetModel>{
    private Map<Player, Long> players;
    private long allMoney;
    private int position;
    private Map<Long, Long> resultMap;
    private PlayerSet playerSet;
    public DiceBetModel() {
        players = new ConcurrentHashMap<>();
        resultMap = new HashMap<>();
    }
    public boolean removePlayer(Player player){
        if (players.containsKey(player)) {
            players.remove(player);
            return true;
        }
        return false;
    }
    public long hasBet(Player player) {
        if (players.containsKey(player)) {
            return players.get(player);
        }
        return 0;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getAllMoney() {
        return allMoney;
    }

    public List<Player> getBetAllPlayer() {
        return new ArrayList<>(players.keySet());
    }

    public void bet(Player dicePlayer, long num) {
        allMoney += num;
        if (players.containsKey(dicePlayer)) {
            long number = players.get(dicePlayer);
            num += number;
        }
        players.put(dicePlayer, num);
    }

    /**
     * 根据玩家清除玩家下的注并将钱返还
     *
     * @param player
     */
    public long clearPlayerBet(Player player) {
        if (players.containsKey(player)) {
            long money = players.remove(player);

            return money;
        }
        return 0;
    }

    public void loseSettle() {
        for (Map.Entry<Player, Long> e : players.entrySet()) {
            Player p = e.getKey();
            //通知自己输的金币
           //清除当局下注金额
            p.clearNowBetCount();
        }
    }

    public Map<Long, Long> getResultMap() {
        return resultMap;
    }

    public long settleAccounts(float rate) {
        long allMoneytTemp = 0;
        allMoneytTemp = (long) (allMoney * rate);
        for (Map.Entry<Player, Long> e : players.entrySet()) {
            Player p = e.getKey();
            /**这里的5是牌的倍率*/
            long money = (long) (e.getValue() * rate);
            //扣除百分之5的平台费
            money = (long) (money - money * 0.5f);
            p.insertGold(money + e.getValue());

            //日志
            UserLog.userLog(p.getUid(),"not card",money,"在骰子场"+position+"位置结算",position+"");

            List<Long> uids = new ArrayList<>(1);
            uids.add(p.getUid());
            //通知自己赢得金币
            GameDicePlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameDicePlayerOperationRoomNotify.class,uids);
            notify.settleAccountsWeathUpdate(e.getValue(),money,p.getGold(),position,rate);
            //有位置
            if(p.getRoomPosition() != PlayerSet.DEFAULT_POSITION){
                GameDicePlayerOperationRoomNotify notify1 = TcpProxyOutboundHandler.createProxy(GameDicePlayerOperationRoomNotify.class,playerSet.getAllPlayerUidByNotPlayer(p.getUid()));
                notify1.positionPlayerWeathUpdate(p.getUid(),money,p.getGold());
            }
            p.clearNowBetCount();
            resultMap.put(p.getUid(), money);
        }
        return allMoneytTemp;
    }

    public PlayerSet getPlayerSet() {
        return playerSet;
    }

    public void setPlayerSet(PlayerSet playerSet) {
        this.playerSet = playerSet;
    }

    public void clear() {
        players.clear();
        resultMap.clear();
    }

    @Override
    public int compareTo(DiceBetModel o) {
        //重小到大排序
        long compareGold = o.getAllMoney() - allMoney;
        if(compareGold > 0){
            return -1;
        }else if(compareGold == 0){
            return 0;
        }else if(compareGold < 0){
            return 1;
        }
        return 0;
    }

}
