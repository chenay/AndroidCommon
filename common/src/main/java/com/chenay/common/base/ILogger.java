package com.chenay.common.base;


public interface ILogger {
    boolean isShowLog = false;
    boolean isShowStackTrace = false;
    String defaultTag ="ILogger";

    void showLog(boolean isShowLog);

    void showStackTrace(boolean isShowStackTrace);

    void debug(String tag, String message);

    void info(String tag, String message);

    void warning(String tag, String message);

    void error(String tag, String message);

    void monitor(String message);

    boolean isMonitorMode();

    String getDefaultTag();
}
