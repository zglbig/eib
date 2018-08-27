package org.zgl.type;

import java.util.HashMap;
import java.util.Map;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/24
 * @文件描述：
 */
public enum ScenesEnum {
    /**大厅*/
    HALL(0),
    /**经典初级场*/
    CLASSICAL_PRIMARY(1),
    /**经典中级场*/
    CLASSICAL_INTERMEDITE(2),
    /**经典高级场*/
    CLASSICAL_ADVANCED(3),
    /**骰子*/
    DICE(4),
    /**万人*/
    THOUSANDS_OF(5),
    /**千王初级场*/
    GREAT_PRETENDERS_PRIMARY(6),
    /**千王中级场*/
    GREAT_PRETENDERS_INTERMEDITE(7),
    /**千王高级场*/
    GREAT_PRETENDERS_ADVANCED(8),
    /**彩票*/
    LOTTERY(9);
    private int id;
    ScenesEnum(int id) {
        this.id = id;
    }
    private static final Map<Integer,ScenesEnum> map;
    static {
        map = new HashMap<>();
        for(ScenesEnum m : ScenesEnum.values()){
            map.put(m.id,m);
        }
    }
    public int id(){
        return id;
    }
    public static ScenesEnum getEnum(int id){
        return map.get(id);
    }
}