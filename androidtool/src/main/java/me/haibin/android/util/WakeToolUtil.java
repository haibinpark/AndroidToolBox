package me.haibin.android.util;

import android.app.Application;
import android.os.PowerManager;


/**
 * 点亮屏幕工具类
 */
public class WakeToolUtil {


    private static PowerManager.WakeLock wakeLock;


    /**
     * 亮屏的操作
     */
    public static void wakeUp(Application application) {
        PowerManager pm = (PowerManager) application.getSystemService(
                Application.POWER_SERVICE
        );
        wakeLock = pm.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.SCREEN_DIM_WAKE_LOCK,
                "target"
        );

        //实现点亮屏幕
        wakeLock.acquire();
    }


    /**
     * 释放息屏
     */
    public static void sleep() {
        if (null ==wakeLock)
            return;
        //释放后息屏
        wakeLock.release();
    }
}
