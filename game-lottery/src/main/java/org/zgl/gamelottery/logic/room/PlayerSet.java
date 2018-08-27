package org.zgl.gamelottery.logic.room;
import org.zgl.gamelottery.logic.player.Player;

import java.util.*;

/**
 * @author
 */
public class PlayerSet {
    private Map<Long,Player> players;
    private final Room room;

    public PlayerSet(Room room) {
        this.room = room;
        this.players = new HashMap<>();
    }

    public void enter(Player player) {
        if(players.containsKey(player.getUid())){
            players.put(player.getUid(),player);
        }
    }

    public void exit(Player player) {
        if(players.containsKey(player.getUid())){
            players.remove(player.getUid());
        }
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }

    public List<Player> getPlayersNotPlayer(Player player) {
        Map<Long,Player> playerMap = new HashMap<>(players);
        long uid = player.getUid();
        if(playerMap.containsKey(uid)){
            playerMap.remove(uid);
        }
        return new ArrayList<>(playerMap.values());
    }
    public List<Long> getPlayerUidListByOnline(){
        List<Long> uids = new ArrayList<>(players.size());
        for(Player p : players.values()){
            if(!p.isExit()){
                uids.add(p.getUid());
            }
        }
        return uids;
    }
    public List<Long> getAllPlayerUid(){
        return new ArrayList<>(players.keySet());
    }
    public Player getPlayerByUid(long uid){
        return players.getOrDefault(uid,null);
    }
    public boolean hasPlayer(long uid) {
        return players.containsKey(uid);
    }

    public int playerNum() {
        return players.size();
    }
    public void end(){
        Iterator<Map.Entry<Long,Player>> iterator = players.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Long,Player> map = iterator.next();
            if(map.getValue().isExit()){
                iterator.remove();
            }
        }
    }
}
