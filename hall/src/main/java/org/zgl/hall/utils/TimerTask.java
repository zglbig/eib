package org.zgl.hall.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zgl.hall.logic.friend.FriendModelManager;
import org.zgl.hall.logic.pay.PayManager;
import org.zgl.hall.logic.redpacket.friend.FriendRedPacketManager;
import org.zgl.hall.logic.redpacket.hall.HallRedPacketManager;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Component
public class TimerTask {
    @Scheduled(cron = "0/1 * * * * *")
    private void roomTime() {
        try {
            PayManager.getInstance().timeOutForm();
            FriendModelManager.getInstance().checkTimeOut();
            FriendRedPacketManager.getInstance().timeOut();
            HallRedPacketManager.getInstance().timeOut();
        } catch (Exception e) {
            LoggerUtils.getLogicLog().error("任务调度失败", e);
        }
    }

    /**
     * 每300毫秒执行一次
     */
    @Scheduled(fixedRate = 300)
    private void mmTimer() {
    }
}
