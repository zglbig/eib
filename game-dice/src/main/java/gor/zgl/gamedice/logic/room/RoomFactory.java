package gor.zgl.gamedice.logic.room;

import org.zgl.type.ScenesEnum;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/18
 * @文件描述：
 */
public class RoomFactory {
    /**
     * 线程安全的懒汉式单利模式
     */
    private static class SingletonHolder {
        public final static RoomFactory instance = new RoomFactory();
    }
    private static RoomFactory instance;
    public static RoomFactory getInstance() {
        return SingletonHolder.instance;
    }
    private final Room room;
    private RoomFactory() {
        room = new Room(ScenesEnum.DICE.id(),0,-111);
    }
    public Room getRoom(){
        return room;
    }
}
