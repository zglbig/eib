package gor.zgl.gamedice.logic.room;

import gor.zgl.gamedice.logic.player.Player;
import gor.zgl.gamedice.socket.http.TcpProxyOutboundHandler;
import org.zgl.dto.clinet.dice.GameDicePositionPlayerInfoDto;
import org.zgl.service.client.dice.GameDicePlayerOperationRoomNotify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public class PlayerSet {
    public static final int DEFAULT_POSITION = -11111;
    private static final int MAX_POSITION = 4;
    private Map<Long, Player> players;
    private Room room;
    private Player[] positions;
    /**
     * 位置计数器
     */
    private int positionCount;

    public PlayerSet(Room room) {
        this.room = room;
        this.players = new ConcurrentHashMap<>();
        this.positions = new Player[MAX_POSITION];
    }

    public void enter(Player p) {
        p.setRoomPosition(DEFAULT_POSITION);
        int pos = position();
        if (pos != -1) {
            upPosition(p, pos);
        }
        players.put(p.getUid(), p);
    }

    public void exit(Player p) {
        downPosition(p);
        if(players.containsKey(p.getUid())){
            players.remove(p.getUid());
        }
    }

    public List<Long> getAllPlayerUid() {
        return new ArrayList<>(players.keySet());
    }

    public List<Player> getAllPlayer() {
        return new ArrayList<>(players.values());
    }

    public List<Long> getAllPlayerUidByNotPlayer(long uid) {
        Map<Long, Player> map = new HashMap<>(players);
        if (map.containsKey(uid)) {
            map.remove(uid);
        }
        return new ArrayList<>(map.keySet());
    }

    public boolean upPosition(Player player, int position) {
        if (player.getRoomPosition() == DEFAULT_POSITION) {
            if (positions[position] == null) {
                positions[position] = player;
                positionCount++;
                player.setRoomPosition(position);
                GameDicePlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameDicePlayerOperationRoomNotify.class, getAllPlayerUidByNotPlayer(player.getUid()));
                notify.playerPositionUp(baseInfo(player,position));
                return true;
            }
        }
        return false;
    }

    private int position() {
        for (int i = 0; i < positions.length; i++) {
            Player p = positions[i];
            if (p == null) {
                return i;
            }
        }
        return -1;
    }

    public boolean downPosition(Player player) {
        if (player.getRoomPosition() == DEFAULT_POSITION) {
            return false;
        }
        int playerRoomPosition = player.getRoomPosition();
        if (playerRoomPosition > MAX_POSITION || playerRoomPosition < 0) {
            return false;
        }
        Player tempPlayer = positions[player.getRoomPosition()];
        if (tempPlayer == null) {
            return false;
        }
        if (tempPlayer.getUid() == player.getUid()) {
            positions[playerRoomPosition] = null;
            positionCount--;
            player.setRoomPosition(DEFAULT_POSITION);
            GameDicePlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameDicePlayerOperationRoomNotify.class, getAllPlayerUidByNotPlayer(player.getUid()));
            notify.playerPositionDown(player.getUid());
            return true;
        }
        return false;
    }

    public boolean kicking(long uid, Player player) {
        for (int i = 0; i < positions.length; i++) {
            Player p = positions[i];
            if (p != null && p.getUid() == uid) {
                positions[i] = null;
                positionCount--;
                GameDicePlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(GameDicePlayerOperationRoomNotify.class, getAllPlayerUid());
                notify.kicking(player.getUserName(), p.getUserName(), p.getUid());
                return true;
            }
        }
        return false;
    }

    public List<GameDicePositionPlayerInfoDto> getPositionPlayer() {
        List<GameDicePositionPlayerInfoDto> dtos = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            Player p = positions[i];
            if (p != null) {
                dtos.add(baseInfo(p,i));
            }
        }
        return dtos;
    }
    public GameDicePositionPlayerInfoDto baseInfo(Player p,int position){
        GameDicePositionPlayerInfoDto dto = new GameDicePositionPlayerInfoDto();
        dto.setUid(p.getUid());
        dto.setGold(p.getGold());
        dto.setHeadUrl(p.getHeadImg());
        dto.setPosition(position);
        dto.setUseAutoId(p.getUseAutoId());
        dto.setVipLv(p.getVipLv());
        dto.setUserName(p.getUserName());
        return dto;
    }
}
