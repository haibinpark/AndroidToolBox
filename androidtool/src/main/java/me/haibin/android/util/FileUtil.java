package me.haibin.android.util;

import android.app.Application;
import android.graphics.Path;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件工具类
 */

public class FileUtil {

    private static final File parentPath = Environment.getExternalStorageDirectory();

    /**
     * 初始化目录，如果没有，则创建目录
     * @return
     */
    public static String initPath(Application application){
        String storagePath = "";
        if(storagePath.equals("")){
            storagePath = parentPath.getAbsolutePath()+"/" + application.getPackageName();
            File f = new File(storagePath);
            if(!f.exists()){
                f.mkdir();
            }
        }
        return storagePath;
    }


    /**
     * 获得当前应用的绝对路径
     * @param application
     * @return
     */
    public static String getStorageAbstractPath(Application application){
        return parentPath.getAbsolutePath()+"/" + application.getPackageName();
    }

}
