package com.hongx.skin;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hongx.skin.util.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    String apkName = "app_skin-debug.apk";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            Utils.extractAssets(newBase, apkName);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File extractFile = this.getFileStreamPath(apkName);
        String apkPath = extractFile.getAbsolutePath();
        Log.d("MainActivity123456", "apkPath:" + apkPath);
        MyApplication.getApplication().setApkPath(apkPath);



    }

    public void skinSelect(View view) {
        startActivity(new Intent(this, SkinActivity.class));
    }
}
