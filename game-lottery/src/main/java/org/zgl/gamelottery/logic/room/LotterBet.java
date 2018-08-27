package org.zgl.gamelottery.logic.room;

import org.zgl.gamelottery.logic.player.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**\
 *
 */
public class LotterBet implements Comparable<LotterBet> {
    private Map<Player, Long> players;
    private long allGold;
    /**哪个玩家赢了多少钱*/
    private int position;
    private PlayerSet playerSet;
    public LotterBet() {
        players = new ConcurrentHashMap<>();
    }

    public boolean removePlayer(Player player){
        if(players.containsKey(player)){
            players.remove(player);
            return true;
        }
        return false;
    }
    /**
     * 下注
     *
     * @param toPlayer
     * @param num
     */
    public void bet(Player toPlayer, long num) {
        allGold += num;
        if (players.containsKey(toPlayer)) {
            long number = players.get(toPlayer);
            num += number;
        }
        players.put(toPlayer, num);
    }

    public long allMoney() {
        return allGold;
    }

    public int getPosition() {
        return position;
    }

    public PlayerSet getPlayerSet() {
        return playerSet;
    }

    public void setPlayerSet(PlayerSet playerSet) {
        this.playerSet = playerSet;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    @Override
    public int compareTo(LotterBet o) {
        return (int) (o.allMoney() - this.allMoney());
    }
}
