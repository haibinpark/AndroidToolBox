package me.haibin.android.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import java.util.List;

import me.haibin.android.model.ApplicationModel;

/**
 * 应用工具类
 */

public class ApplicationUtil {

    public static ApplicationModel getApplicationInfo(Application application) {
        try {
            ApplicationModel applicationModel = new ApplicationModel();
            String packageName = application.getPackageName();
            applicationModel.setPackageName(packageName);
            applicationModel.setVersionName(
                    application.getPackageManager().getPackageInfo(
                            packageName, 0).versionName
            );
            applicationModel.setVersionCode(
                    application.getPackageManager()
                            .getPackageInfo(packageName, 0).versionCode
            );
            return applicationModel;
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 获取栈顶Activity类名,一般只能获取自己应用的，5.0之后对这一权限做了极大的限制
     * <p>
     *     需要添加权限: android.permission.GET_TASKS
     * </p>
     */
    public static String getTopActivityName(Context context)
    {
        String name = "";

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            List<ActivityManager.AppTask> appTasks = manager.getAppTasks();
            if(appTasks != null && appTasks.size() > 0)
            {
                name = appTasks.get(0).getTaskInfo().topActivity.getClassName();
            }
        }
        else
        {
            List<ActivityManager.RunningTaskInfo> taskInfos = manager.getRunningTasks(1);
            if(taskInfos != null && taskInfos.size() > 0)
            {
                name = taskInfos.get(0).topActivity.getClassName();
            }
        }

        return name;
    }


}
