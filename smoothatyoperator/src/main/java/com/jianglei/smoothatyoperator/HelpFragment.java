package com.jianglei.smoothatyoperator;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

/**
 * @author jianglei created on 18-12-10
 */
public class HelpFragment extends Fragment {
    public static final int PERMISSION_CODE = 100;
    private static final String FRAGMENT_TAG = "help_fragment";
    private ActivityCompat.OnRequestPermissionsResultCallback onRequestPermissionsResultCallback;
    /**
     * 在onAttach时是否需要请求权限
     */
    private boolean isNeedRequest;

    /**
     * 等待执行的操作
     */
    private Runnable waitingRunnable;


    public static HelpFragment getFragment(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        HelpFragment fragment;
        if (fm.findFragmentByTag(FRAGMENT_TAG) == null) {
            fragment = new HelpFragment();
            fm.beginTransaction().add(fragment, FRAGMENT_TAG).commit();
        } else {
            fragment = (HelpFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        }
        return fragment;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE && onRequestPermissionsResultCallback != null) {
            onRequestPermissionsResultCallback.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(final String[] permissions, ActivityCompat.OnRequestPermissionsResultCallback
            onRequestPermissionsResultCallback) {
        this.onRequestPermissionsResultCallback = onRequestPermissionsResultCallback;
        if (isAdded()) {
            requestPermissions(permissions, PERMISSION_CODE);
            isNeedRequest = false;
        } else {
            isNeedRequest = true;
            waitingRunnable = new Runnable() {
                @Override
                public void run() {
                    requestPermissions(permissions, PERMISSION_CODE);
                }
            };
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (isNeedRequest && waitingRunnable != null) {
            waitingRunnable.run();
        }
    }
}
