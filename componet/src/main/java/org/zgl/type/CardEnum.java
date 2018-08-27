package org.zgl.type;

import java.util.HashMap;
import java.util.Map;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/21
 * @文件描述：
 */
public enum CardEnum {
    /**牌型*/
    highCard(1,"散牌"),
    pair(2,"对子"),
    straight(3,"孙子"),
    sameColor(4,"金花"),
    straightFlush(5,"顺金"),
    leopard(6,"豹子"),
    AAA(7,"AAA");
    private int id;
    private String desc;

    CardEnum(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
    private static final Map<Integer,CardEnum> map;
    static {
        map = new HashMap<>();
        for(CardEnum m : CardEnum.values()){
            map.put(m.id,m);
        }
    }
    public int id(){
        return id;
    }
    public String desc(){
        return desc;
    }
    public static CardEnum getEnum(int id){
        return map.get(id);
    }
}
