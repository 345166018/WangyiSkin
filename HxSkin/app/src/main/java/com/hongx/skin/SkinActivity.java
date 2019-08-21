package com.hongx.skin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hongx.skincore.SkinManager;

/**
 * @author: yaichain18
 * @create: 2019-07-12 13:06
 */
public class SkinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);

    }

    public void change(View view) {
        String path = MyApplication.getApplication().getApkPath();
        SkinManager.getInstance().loadSkin(path);
    }

    public void restore(View view) {
        SkinManager.getInstance().loadSkin(null);
    }

}
