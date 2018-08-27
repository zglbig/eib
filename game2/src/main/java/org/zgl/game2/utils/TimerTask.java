package org.zgl.game2.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zgl.game2.logic.room.Room;
import org.zgl.game2.logic.room.RoomFactory;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Component
public class TimerTask {
    private Room room = RoomFactory.getInstance().getRoom();
    @Scheduled(cron = "0/1 * * * * *")
    private void roomTime(){
        try {
            room.timer();
        }catch (Exception e){
            LoggerUtils.getLogicLog().error("房间任务调度失败");
        }
    }

    /**
     * 每300毫秒执行一次
     */
    @Scheduled(fixedRate = 300)
    private void mmTimer(){
    }
}
