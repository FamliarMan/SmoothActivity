package com.jianglei.smoothatyoperator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import com.jianglei.lifecycler.HelpFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianglei created on 18-12-10
 */
public class JlPermission {
    private String[] permissions;
    private FragmentActivity activity;

    private JlPermission(Builder builder) {
        this.permissions = builder.permissions.toArray(new String[builder.permissions.size()]);
        this.activity = builder.activity;
    }

    @SuppressLint("NewApi")
    public void request(@NonNull final OnPermissionResultListener onPermissionResultListener) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onPermissionResultListener.onGranted(permissions);
            return;
        }
        final List<String> grantedPermissions = new ArrayList<>();
        final List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
                grantedPermissions.add(permission);
            } else {
                deniedPermissions.add(permission);
            }
        }
        if (deniedPermissions.size() == 0) {
            onPermissionResultListener.onGranted(grantedPermissions.toArray(new String[grantedPermissions.size()]));
            return;
        }
        HelpFragment fragment = HelpFragment.getFragment(activity);
        fragment.requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]),
                new ActivityCompat.OnRequestPermissionsResultCallback() {
                    @Override
                    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                        deniedPermissions.clear();
                        for (int i = 0; i < grantResults.length; ++i) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                grantedPermissions.add(permissions[i]);
                            } else {
                                deniedPermissions.add(permissions[i]);
                            }
                        }
                        if (grantedPermissions.size() != 0) {
                            onPermissionResultListener.onGranted(grantedPermissions.toArray(new String[grantedPermissions.size()]));
                        }
                        if (deniedPermissions.size() != 0) {
                            onPermissionResultListener.onDenied(deniedPermissions.toArray(
                                    new String[deniedPermissions.size()]));
                        }
                    }
                });
    }


    public static Builder start(FragmentActivity activity) {
        if (activity == null) {
            throw new IllegalStateException("You can't apply for a addPermission from a null activity");
        }
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
                && activity.isDestroyed())) {
            throw new IllegalStateException("You can't apply for a addPermission from a destroyed activity");

        }
        return new Builder(activity);
    }


    public static class Builder {
        private List<String> permissions = new ArrayList<>();
        private FragmentActivity activity;

        public Builder(FragmentActivity activity) {
            this.activity = activity;
        }

        public Builder addPermission(String permission) {
            permissions.add(permission);
            return this;
        }

        public JlPermission build() {
            return new JlPermission(this);
        }
    }

}
