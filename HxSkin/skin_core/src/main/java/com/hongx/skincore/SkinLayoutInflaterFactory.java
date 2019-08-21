package com.hongx.skincore;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.hongx.skincore.logger.L;
import com.hongx.skincore.utils.SkinThemeUtils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class SkinLayoutInflaterFactory implements LayoutInflater.Factory2, Observer {
    private static final String[] mClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };
    //记录对应View的构造函数
    private static final Map<String, Constructor<? extends View>> mConstructorMap
            = new HashMap<>();
    private static final Class<?>[] mConstructorSignature = new Class[]{
            Context.class, AttributeSet.class};

    // 当选择新皮肤后需要替换View与之对应的属性
    // 页面属性管理器
    private SkinAttribute skinAttribute;

    private Activity activity;

    public SkinLayoutInflaterFactory(Activity activity, Typeface typeface) {
        this.activity = activity;
        skinAttribute = new SkinAttribute(typeface);
    }


    /**
     * 创建对应布局并返回
     *
     * @param parent  当前TAG 父布局
     * @param name    在布局中的TAG 如:TextView, android.support.v7.widget.Toolbar
     * @param context 上下文
     * @param attrs   对应布局TAG中的属性 如: android:text android:src
     * @return View    null则由系统创建
     */
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        //换肤就是在需要时候替换 View的属性(src、background等)
        //所以这里创建 View,从而修改View属性
        View view = createViewFromTag(name, context, attrs);
        if (null == view) {
            view = createView(name, context, attrs);
        }
        if (null != view) {
            L.e(String.format("检查[%s]:" + name, context.getClass().getName()));
            //加载属性
            skinAttribute.load(view, attrs);
        }
        return view;
    }


    private View createViewFromTag(String name, Context context, AttributeSet
            attrs) {
        //如果包含 . 则不是SDK中的view 可能是自定义view包括support库中的View
        if (-1 != name.indexOf('.')) {
            return null;
        }
        for (int i = 0; i < mClassPrefixList.length; i++) {
            return createView(mClassPrefixList[i] +
                    name, context, attrs);

        }
        return null;
    }

    private View createView(String name, Context context, AttributeSet
            attrs) {
        Constructor<? extends View> constructor = findConstructor(context, name);
        try {
            return constructor.newInstance(context, attrs);
        } catch (Exception e) {
        }
        return null;
    }

    private Constructor<? extends View> findConstructor(Context context, String name) {
        Constructor<? extends View> constructor = mConstructorMap.get(name);
        if (null == constructor) {
            try {
                Class<? extends View> clazz = context.getClassLoader().loadClass
                        (name).asSubclass(View.class);
                constructor = clazz.getConstructor(mConstructorSignature);
                mConstructorMap.put(name, constructor);
            } catch (Exception e) {
            }
        }
        return constructor;
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        SkinThemeUtils.updateStatusBarColor(activity);
        Typeface typeface = SkinThemeUtils.getSkinTypeface(activity);
        skinAttribute.setTypeface(typeface);
        skinAttribute.applySkin();
    }
}
