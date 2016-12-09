package me.haibin.android.model;

/**
 * 应用信息模型类.
 */

public class ApplicationModel {

    private String packageName; //包名
    private String versionName; //版本名称
    private int versionCode; //版本代码


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
