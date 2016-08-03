package com.yummyspots.util;

import android.util.Log;

import com.yummyspots.BuildConfig;



public class Logger {
    private static final String DEFAULT_TAG = "EATING LVIV";

    public static Logger getLogger(String tag) {
        return new Logger(tag);
    }

    private String tag;

    private Logger(String tag) {
        this.tag = tag;
        if (this.tag == null) {
            this.tag = DEFAULT_TAG;
        }
    }

    public void debug(String message) {
        publishLog(Log.DEBUG, message, null);
    }

    public void info(String message) {
        publishLog(Log.INFO, message, null);
    }

    public void warn(String message) {
        publishLog(Log.WARN, message, null);
    }

    public void warn(String message, Throwable throwable) {
        publishLog(Log.WARN, message, throwable);
    }

    public void error(String message) {
        publishLog(Log.ERROR, message, null);
    }

    public void error(String message, Throwable throwable) {
        publishLog(Log.WARN, message, throwable);
    }

    private void publishLog(int level, String message, Throwable t) {
        if (BuildConfig.DEBUG) {
            switch (level) {
                case Log.DEBUG:
                    Log.d(tag, message);
                    break;
                case Log.INFO:
                    Log.i(tag, message);
                    break;
                case Log.WARN:
                    if (t == null) {
                        Log.w(tag, message);
                    } else {
                        Log.w(tag, message, t);
                    }
                    break;
                case Log.ERROR:
                    if (t == null) {
                        Log.e(tag, message);
                    } else {
                        Log.e(tag, message, t);
                    }
                    break;
            }
        }
    }
}
