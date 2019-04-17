package com.jianglei.smoothatyoperator;

import android.app.Activity;
import android.os.Build;

/**
 * @author jianglei on 4/17/19.
 */
public class SmoothAtyOperator {
    /**
     * 发起权限申请
     *
     * @param activity 发起的activity
     */
    public static JlPermission.Builder startPermission(Activity activity) {
        return JlPermission.start(activity);
    }


}
