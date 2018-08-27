package org.zgl.game.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Component
public class TimerTask {
    /**每天12点执行*/
    @Scheduled(cron = "0 0 12 * * *")
    private void roomTime(){
    }

    /**
     * 每300毫秒执行一次
     */
    @Scheduled(fixedRate = 300)
    private void mmTimer(){
    }
}
