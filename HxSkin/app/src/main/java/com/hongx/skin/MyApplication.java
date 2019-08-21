package com.hongx.skin;

import android.app.Application;

import com.hongx.skincore.SkinManager;

/**
 * @author: yaichain18
 * @create: 2019-07-11 17:16
 */
public class MyApplication extends Application {


    private static MyApplication myApplication = null;

    public static MyApplication getApplication(){
        if (myApplication == null){
            myApplication = new MyApplication();
        }
        return myApplication;
    }

    String apkPath;

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

}
