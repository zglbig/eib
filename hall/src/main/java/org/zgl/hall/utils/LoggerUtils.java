package org.zgl.hall.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ： 白泽
 * 时间： 2017/11/15.
 * 描述：
 */
public class LoggerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerUtils.class);

    public LoggerUtils() {
    }

    public static Logger getPlatformLog() {
        return LOGGER;
    }

    public static Logger getLogicLog() {
        return LOGGER;
    }

}
