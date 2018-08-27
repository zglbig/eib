package org.zgl.gamelottery.logic.card;

import java.util.HashMap;
import java.util.Map;

public enum CardType {
    /**散牌*/
    HIGH_CARD(1,1,1F,0),
    /**对子*/
    PAIR(2,1,1000000,0),
    /**顺子*/
    STRAIGHT(3,2,3000000,0),
    /**同花*/
    SAME_COLOR(4,3,8000000,0),
    /**同花顺*/
    STRAIGHT_FLUSH(5,4,38000000,3),
    /**豹子*/
    LEOPARD(6,5,60000000,10),
    /**aaa*/
    AAA(7,5,300000000,25);
    private int id;
    /**万人场倍率*/
    private int rate;
    /**时时乐倍率*/
    private float ahRate;
    /**奖池倍率*/
    private int jackpot;
    private CardType(int id,int rate,float ahRate,int jackpot) {
        this.id = id;
        this.rate = rate;
        this.ahRate = ahRate;
        this.jackpot = jackpot;
    }
    public int id(){
        return id;
    }
    public int rate(){
        return rate;
    }
    public int jackpot(){return jackpot;}
    public float ahRate(){
        return ahRate;
    }
    private static final Map<Integer,CardType> map;
    static {
        map = new HashMap<>();
        for(CardType c:CardType.values()){
            map.putIfAbsent(c.id(),c);
        }
    }
    public static CardType getType(int id){
        return map.get(id);
    }
}
