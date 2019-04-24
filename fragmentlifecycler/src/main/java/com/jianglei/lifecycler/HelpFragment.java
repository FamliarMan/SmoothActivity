package com.jianglei.lifecycler;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianglei created on 18-12-10
 */
public class HelpFragment extends Fragment {
    public static final int PERMISSION_CODE = 100;
    private static final String FRAGMENT_TAG = "help_fragment";
    private ActivityCompat.OnRequestPermissionsResultCallback onRequestPermissionsResultCallback;
    private OnActivityResultListener onActivityResultListener;
    private List<OnLifecyclerListener> lifecyclerListeners = new ArrayList<>();
    /**
     * 在onAttach时是否需要请求权限,有时候
     * fragment没有立即attach到fragment上时，有些行为是无法触发的，得在
     * attach后执行
     */
    private boolean needExecuteRunnable;

    /**
     * 等待执行的操作
     */
    private Runnable waitingRunnable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (OnLifecyclerListener listener : lifecyclerListeners) {
            listener.onCreate();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        for (OnLifecyclerListener listener : lifecyclerListeners) {
            listener.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (OnLifecyclerListener listener : lifecyclerListeners) {
            listener.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (OnLifecyclerListener listener : lifecyclerListeners) {
            listener.onPause(getActivity() == null || getActivity().isFinishing());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (OnLifecyclerListener listener : lifecyclerListeners) {
            listener.onStop(getActivity() == null || getActivity().isFinishing());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (OnLifecyclerListener listener : lifecyclerListeners) {
            listener.onDestroy();

        }
    }

    public static HelpFragment getFragment(FragmentActivity activity) {
        FragmentManager fm = activity.getSupportFragmentManager();
        HelpFragment fragment;
        if (fm.findFragmentByTag(FRAGMENT_TAG) == null) {
            fragment = new HelpFragment();
            fm.beginTransaction().add(fragment, FRAGMENT_TAG).commit();
        } else {
            fragment = (HelpFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        }
        return fragment;
    }

    public static HelpFragment getFragment(Fragment fragment) {
        FragmentManager fm = fragment.getChildFragmentManager();
        HelpFragment helpFragment;
        if (fm.findFragmentByTag(FRAGMENT_TAG) == null) {
            helpFragment = new HelpFragment();
            fm.beginTransaction().add(helpFragment, FRAGMENT_TAG).commit();
        } else {
            helpFragment = (HelpFragment) fm.findFragmentByTag(FRAGMENT_TAG);
        }
        return helpFragment;
    }

    @Override
    public void startActivityForResult(final Intent intent, final int requestCode) {
        if (isAdded()) {
            needExecuteRunnable = false;
            super.startActivityForResult(intent, requestCode);
        } else {
            needExecuteRunnable = true;
            waitingRunnable = new Runnable() {
                @Override
                public void run() {
                    HelpFragment.super.startActivityForResult(intent, requestCode);
                }
            };
        }
    }

    public void startActivityForResult(Intent intent, int requestCode, OnActivityResultListener listener) {
        onActivityResultListener = listener;
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE && onRequestPermissionsResultCallback != null) {
            onRequestPermissionsResultCallback.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (onActivityResultListener != null) {
            onActivityResultListener.onActivityResult(requestCode, resultCode, data);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(final String[] permissions, ActivityCompat.OnRequestPermissionsResultCallback
            onRequestPermissionsResultCallback) {
        this.onRequestPermissionsResultCallback = onRequestPermissionsResultCallback;
        if (isAdded()) {
            requestPermissions(permissions, PERMISSION_CODE);
            needExecuteRunnable = false;
        } else {
            needExecuteRunnable = true;
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
        if (needExecuteRunnable && waitingRunnable != null) {
            waitingRunnable.run();
        }
    }

    public void setOnActivityResultListener(OnActivityResultListener onActivityResultListener) {
        this.onActivityResultListener = onActivityResultListener;
    }

    public void addLifecyclerListener(OnLifecyclerListener lifecyclerListener) {
        lifecyclerListeners.add(lifecyclerListener);
    }

    public interface OnActivityResultListener {

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    public interface OnLifecyclerListener {
        void onCreate();

        void onStart();

        void onResume();

        /**
         * activity onPause时执行
         *
         * @param isFinishing 当前是否是关闭activity
         */
        void onPause(boolean isFinishing);

        /**
         * activity onStop时执行
         *
         * @param isFinishing 当前是否是关闭activity
         */
        void onStop(boolean isFinishing);

        void onDestroy();
    }
}
