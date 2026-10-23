package de.lachcrafter.tns.common.util;

import java.util.logging.Logger;

public class LoggerUtil {
    private static Logger logger;

    public static Logger getLogger() {

        return logger;
    }

    public static void setLogger(Logger logger) {

        LoggerUtil.logger = logger;
    }
}
