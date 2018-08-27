package org.zgl.game2.logic.room;

import org.zgl.datable.CardDataTable;
import org.zgl.dto.clinet.commond.GameRankingDto;
import org.zgl.dto.clinet.game2.Game2JackpotDto;
import org.zgl.game2.logic.card.CardType;
import org.zgl.game2.logic.operation.UserLog;
import org.zgl.game2.logic.player.Player;
import org.zgl.game2.socket.http.TcpProxyOutboundHandler;
import org.zgl.service.client.game2.Game2PlayerOperationRoomNotify;
import org.zgl.type.CardEnum;
import org.zgl.type.ScenesEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**\
 *
 */
public class Game2Bet implements Comparable<Game2Bet> {
    private Map<Player, Long> players;
    private long allMoney;
    /**哪个玩家赢了多少钱*/
    private Map<Long,Long> resultMap;
    private int position;
    private PlayerSet playerSet;
    public Game2Bet() {
        players = new ConcurrentHashMap<>();
        resultMap = new HashMap<>();
    }
    public long jackpot(){
        return jackpotx;
    }
    /**
     * 25 10 3
     * @param cardType
     * @return
     */
    private long jackpotx = 0;
    public List<Game2JackpotDto> jackpot(CardType cardType, long jackpot){
        List<Game2JackpotDto> jackpotDtos = new ArrayList<>(players.size());
        float rate = (float) cardType.jackpot() / 100;
        float resultJackpot = jackpot* rate ;
        jackpotx = (long) (jackpot - resultJackpot);
        int playerSize = players.size();
        //平分奖池
        for(Map.Entry<Player,Long> e:players.entrySet()){

//日志
//            HandCard card1 = handCardMap.get(0);
//            UserLog.userLog(room.getScenesId(),banker.getUid(),CardEnum.getEnum(card1.getCardType()).desc(),alm,"在"+room.getRoomName()+"坐庄结算",Arrays.toString(card1.getCardIds()));
        }
        return jackpotDtos;
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
        allMoney += num;
        if (players.containsKey(toPlayer)) {
            long number = players.get(toPlayer);
            num += number;
        }
        players.put(toPlayer, num);
    }

    public long allMoney() {
        return allMoney;
    }
    public long settleAccounts(int cardType, Integer[] cardIds, Map<Long,GameRankingDto> ranking){
        String cardDesc = cardDexc(cardIds);
        for(Map.Entry<Player,Long> e:players.entrySet()){
            long gold = e.getValue();
            Player p = e.getKey();
            gold = (long) (gold - gold * 0.5);
            p.insertGold(gold);
            //日志
            UserLog.userLog(ScenesEnum.THOUSANDS_OF.id(),p.getUid(),CardEnum.getEnum(cardType).desc(),gold,"在万人场"+position+"位置下注结算结算",cardDesc);
            List<Long> uids = new ArrayList<>(1);
            uids.add(p.getUid());
            Game2PlayerOperationRoomNotify notify = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,uids);
            notify.playerSettleAccounts(p.getGold(),gold);
            if(p.getRoomPosition() != PlayerSet.DEFAULT_POS){
                Game2PlayerOperationRoomNotify notify1 = TcpProxyOutboundHandler.createProxy(Game2PlayerOperationRoomNotify.class,playerSet.getAllPlayerUid());
                notify1.positionPlayerSettleAccounts(p.getGold(),gold);
            }
            //计算本剧排行
            long win = e.getValue();
            GameRankingDto dto = ranking.get(p.getUid());
            if(dto == null){
                dto = new GameRankingDto(0,p.getGold(),p.getUserName());
                ranking.put(p.getUid(),dto);
            }
            dto.insertWinGold(win);
        }
        return allMoney;
    }
    private String cardDexc(Integer[] ids){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i = 0;i<ids.length;i++){
            CardDataTable dataTable = CardDataTable.get(ids[i]);
            sb.append(dataTable.getDesc());
            if(i < ids.length - 1){
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    public Map<Long,Long> getResultMap(){
        return resultMap;
    }

    public void exit(Player player) {
        if (players.containsKey(player)) {
            players.remove(player);
        }
    }
    public void clear() {
        allMoney = 0;
        players.clear();
        resultMap.clear();
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
    public int compareTo(Game2Bet o) {
        return (int) (o.allMoney() - this.allMoney());
    }
}
