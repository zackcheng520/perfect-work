package com.perfect.treasurehouse.utils;

import android.util.Log;

/**
 * Created by Zack on 2016/12/22.
 */

public class TLog {
    private final static boolean DEBUG = true;
    private final static String TAG = "TREASURE_HOUSE";
    private final static String ERROR_INFO = "EEROR_UNKNOW_CLASS";

    public static void v(String msg) {
        if (DEBUG) {
            String info = getThreadInfo();
            Log.v(TAG, info + msg);
        }
    }

    public static void d(String msg) {
        String info = getThreadInfo();
        if (DEBUG) {
            Log.d(TAG, info + msg);
        } else {
            Log.v(TAG, info + msg);
        }
    }

    public static void i(String msg) {
        String info = getThreadInfo();
        if (DEBUG) {
            Log.i(TAG, info + msg);
        } else {
            Log.d(TAG, info + msg);
        }
    }

    public static void w(String msg) {
        String info = getThreadInfo();
        if (DEBUG) {
            Log.w(TAG, info + msg);
        } else {
            Log.i(TAG, info + msg);
        }
    }

    public static void e(String msg) {
        String info = getThreadInfo();
        if (DEBUG) {
            Log.e(TAG, info + msg);
            throw new TLogException("msg");
        } else {
            Log.w(TAG, info + msg);
        }
    }

    private static String getThreadInfo() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (trace.length > 4) {
            return getFileName(trace[4]) + ":" + trace[4].getMethodName() + "() ";
        } else {
            return ERROR_INFO;
        }
    }

    private static String getFileName(StackTraceElement element) {
        String fileName = element.getFileName();
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    private static String getFileName() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        return getFileName(trace[3]);
    }

    private static class TLogException extends RuntimeException{
        private TLogException(String info){
            super(info);
        }
    }
}
