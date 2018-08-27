package org.zgl.db.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zgl.db.logic.hall.ranking.RankingManager;

/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/16
 * @文件描述：
 */
@Component
public class TimerTask {
    /**每天12点执行*/
//    @Scheduled(cron = "0 0 12 * * *")
    @Scheduled(cron = "0/1 * * * * *")
    private void roomTime(){
        RankingManager.getInstance().ranking();
    }

    /**
     * 每300毫秒执行一次
     */
    @Scheduled(fixedRate = 300)
    private void mmTimer(){
    }
}
