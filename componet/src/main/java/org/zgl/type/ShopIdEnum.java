package org.zgl.type;

import java.util.HashMap;
import java.util.Map;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/17
 * @文件描述：
 */
public enum ShopIdEnum {
    /**没有*/
    NENO(0),
    /**金币*/
    GOLD(1),
    /**钻石*/
    DIAMOND(2),
    /**座驾*/
    AUTOS(10),
    /**vip*/
    VIP(20),
    /**道具*/
    PROP(30),
    /**礼物*/
    GIFT(40),
    VIP_LV(700),
    VIP_EXP(800);
    private int id;

    private ShopIdEnum(int id) {
        this.id = id;
    }
    public int id(){
        return id;
    }
    private static final Map<Integer,ShopIdEnum> map;
    static {
        map = new HashMap<>();
        for(ShopIdEnum c:ShopIdEnum.values()){
            map.putIfAbsent(c.id,c);
        }
    }
    public static ShopIdEnum getType(int id){
        return map.get(id);
    }
}
