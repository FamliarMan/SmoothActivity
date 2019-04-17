package com.jianglei.smoothatyoperator;

/**
 * @author jianglei created on 18-12-10
 */
public interface OnPermissionResultListener {
    /**
     * 权限申请成功,如果权限全部被拒绝，这个方法不会被回调
     */
    void onGranted(String[] permissions);

    /**
     * 权限被拒绝,如果权限全部同意了，这个方法不会被回调
     */
    void onDenied(String[] permissions);
}
