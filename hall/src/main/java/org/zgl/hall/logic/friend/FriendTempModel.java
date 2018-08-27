package org.zgl.hall.logic.friend;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/10
 * @文件描述：
 */
public class FriendTempModel {
    private Map<Long, Long> addTemp = new HashMap<>();

    public FriendTempModel() {
    }

    public FriendTempModel(Map<Long, Long> addTemp) {
        this.addTemp = addTemp;
    }

    public void putMap(long uid) {
        addTemp.put(uid, System.currentTimeMillis());
    }

    public long remove(long uid) {
        if (addTemp.containsKey(uid)) {
            return addTemp.remove(uid);
        }
        return 0;
    }

    public void timeOut() {
        Iterator<Map.Entry<Long, Long>> iterator = addTemp.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Long> map = iterator.next();
            if (System.currentTimeMillis() - map.getValue() > 30000) {
                iterator.remove();
            }
        }
    }

    public boolean isNull() {
        return addTemp.size() == 0;
    }

}
