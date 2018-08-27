package org.zgl.type;

import java.util.HashMap;
import java.util.Map;

/**
 * @作者： big
 * @创建时间： 2018/6/21
 * @文件描述：
 */
public enum MutualEnum {
    /**客户端请求服务器*/
    CLIENT_TO_SERVER(-10),
    /**服务发送数据到客户端 网关也算*/
    SERVER_TO_CLIENT(-40),
    /**服务器制动通知客户端 通知全服*/
    SERVER_TO_CLIENT_NOTIFY_ALL(-50),
    /**服务器制动通知客户端 以任务的形式通知*/
    SERVER_TO_CLIENT_TASK(-60),
    /**注册链接*/
    REGIST(-100);
    private int id;

    MutualEnum(int id) {
        this.id = id;
    }
    private static final Map<Integer,MutualEnum> map;
    static {
        map = new HashMap<>();
        for(MutualEnum m : MutualEnum.values()){
            map.put(m.id,m);
        }
    }
    public short id(){
        return (short) id;
    }
    public static MutualEnum getMutualEnum(int id){
        return map.get(id);
    }
}
