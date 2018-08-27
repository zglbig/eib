package org.zgl.gate.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zgl.gate.online.OnlieManager;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Component
public class TimerTask {
    @Scheduled(cron = "0/1 * * * * *")
    private void roomTime(){
        try {
            OnlieManager.onlineHandler();
        }catch (Exception e){
            LoggerUtils.getLogicLog().error("房间任务调度失败",e);
        }
    }

    /**
     * 每300毫秒执行一次
     */
    @Scheduled(fixedRate = 300)
    private void mmTimer(){
    }
}
