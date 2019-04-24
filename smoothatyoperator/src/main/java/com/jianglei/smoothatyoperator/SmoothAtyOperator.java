package com.jianglei.smoothatyoperator;

import android.support.v4.app.FragmentActivity;

/**
 * @author jianglei on 4/17/19.
 */
public class SmoothAtyOperator {
    /**
     * 发起权限申请
     *
     * @param activity 发起的activity
     */
    public static JlPermission.Builder startPermission(FragmentActivity activity) {
        return JlPermission.start(activity);
    }

    /**
     * 发起activity跳转请求
     */
    public static JlActivity.Builder prepareActivity() {
        return JlActivity.prepare();
    }


}
