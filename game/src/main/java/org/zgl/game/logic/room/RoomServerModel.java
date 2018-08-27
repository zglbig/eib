package org.zgl.game.logic.room;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/7/26
 * @文件描述：
 */
public class RoomServerModel {
    private static RoomServerModel instance;

    public static RoomServerModel getInstance() {
        if (instance == null) {
            instance = new RoomServerModel();
        }
        return instance;
    }

    /**
     * 场景对应的房间集合对应的房间
     */
    private Map<Integer, Map<Integer, Room>> roomMap = new ConcurrentHashMap<>();

    public Room getRoom(int scenesId, int roomId) {
        Map<Integer, Room> rooms = roomMap.getOrDefault(scenesId, null);
        if (rooms != null) {
            Room room = rooms.getOrDefault(roomId, null);
            if (room != null) {
                return room;
            }
        }
        return null;
    }

    public Room createRoom(long uid, int scenesId) {
        Room room = new Room(scenesId, 1, uid);
        return room;
    }

    public Room getRoom(int scenesId) {
        Map<Integer, Room> rooms = roomMap.getOrDefault(scenesId, null);
        Iterator<Room> iterator = rooms.values().iterator();
        while (iterator.hasNext()) {
            Room r = iterator.next();
            if (!r.isHasDestroy() && r.getPlayerNum() < 5) {
                return r;
            }
        }
        return null;
    }

    public Room removeRoom(int scenesId, int roomId) {
        return null;
    }
}
