package com.jianglei.smoothatyoperator;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;

import com.jianglei.lifecycler.HelpFragment;

import java.io.Serializable;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * @author jianglei on 4/17/19.
 */
public class JlActivity {

    private Builder builder;
    private int lastCode;
    private SparseArray<OnActivityResultListener> resultListeners = new SparseArray<>();

    private JlActivity(Builder builder) {
        this.builder = builder;
    }

    public void startActivity(Activity activity, Class aty) {
        builder.intent.setClass(activity, aty);
        activity.startActivity(builder.intent);
    }

    public static Builder prepare() {
        return new Builder();
    }

    public void startActivityForResult(FragmentActivity activity, Class aty, OnActivityResultListener listener) {
        builder.intent.setClass(activity, aty);
        HelpFragment helpFragment = HelpFragment.getFragment(activity);
        int code = createRequestCode();
        resultListeners.append(code, listener);
        helpFragment.startActivityForResult(builder.intent,
                code,
                new HelpFragment.OnActivityResultListener() {
                    @Override
                    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                        OnActivityResultListener resultListener = resultListeners.get(requestCode);
                        if (resultCode == RESULT_OK) {
                            resultListener.onResultOk(data);
                        } else {
                            resultListener.onResultCancel();
                        }
                    }
                });
    }

    public void startActivity(Fragment fragment, Class aty) {
        if (fragment.getActivity() == null) {
            throw new IllegalArgumentException("You can't start an Activity from a detached fragment");
        }
        builder.intent.setClass(fragment.getActivity(), aty);
        fragment.startActivity(builder.intent);
    }

    private int createRequestCode() {
        int code = (int) (System.currentTimeMillis() / 1000 % 65535);
        if (code == lastCode) {
            code++;
        }
        lastCode = code;
        return code;
    }

    public static abstract class OnActivityResultListener {
        /**
         * 前一个页面成功返回时回调（resultCode为RESULT_OK时）
         *
         * @param data 携带的数据
         */
        public abstract void onResultOk(Intent data);

        /**
         * 前一个页面按返回键（resultCode为RESULT_CANCEL）返回回调
         */
        public void onResultCancel() {
        }
    }

    public static class Builder {
        private Intent intent;
        private Bundle bundle;

        public Builder() {
            intent = new Intent();
            bundle = new Bundle();

        }

        public JlActivity build() {
            intent.putExtras(bundle);
            return new JlActivity(this);
        }


        public Builder intent(Intent intent) {
            this.intent = intent;
            return this;
        }

        public Builder bundle(Bundle bundle) {
            this.bundle = bundle;
            return this;
        }

        public Builder putExtraToIntent(String name, boolean value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, boolean[] value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, byte value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, byte[] value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, char value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, char[] value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, short value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, short[] value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, int value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, int[] value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, long value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, long[] value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, float value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, float[] value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, double value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, double[] value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, String value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, String[] value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, CharSequence value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, CharSequence[] value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, Parcelable value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, Parcelable[] value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putParcelableArrayListExtraToIntent(String name, ArrayList<? extends Parcelable> value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putIntegerArrayListExtraToIntent(String name, ArrayList<Integer> value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putStringArrayListExtraToIntent(String name, ArrayList<String> value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putCharSequenceArrayListExtraToIntent(String name, ArrayList<CharSequence> value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putExtraToIntent(String name, Serializable value) {
            intent.putExtra(name, value);
            return this;
        }

        public Builder putAllToBundle(Bundle bundle) {
            bundle.putAll(bundle);
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public Builder putPersistableBundleToBundle(String key, PersistableBundle bundle) {
            bundle.putPersistableBundle(key, bundle);
            return this;
        }

        public Builder putDoubleArrayToBundle(String key, double[] value) {
            bundle.putDoubleArray(key, value);
            return this;
        }

        public Builder putDoubleToBundle(String key, double value) {
            bundle.putDouble(key, value);
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        public Builder putBinderToBundle(String key, IBinder value) {
            bundle.putBinder(key, value);
            return this;
        }

        public Builder putByteToBundle(String key, byte value) {
            bundle.putByte(key, value);
            return this;
        }

        public Builder putByteArrayToBundle(String key, byte[] value) {
            bundle.putByteArray(key, value);
            return this;
        }

        public Builder putCharToBundle(String key, char value) {
            bundle.putChar(key, value);
            return this;
        }

        public Builder putCharArrayToBundle(String key, char[] value) {
            bundle.putCharArray(key, value);
            return this;
        }

        public Builder putCharSequenceToBundle(String key, CharSequence value) {
            bundle.putCharSequence(key, value);
            return this;
        }

        public Builder putCharSequenceArrayToBundle(String key, CharSequence[] value) {
            bundle.putCharSequenceArray(key, value);
            return this;
        }

        public Builder putCharSequenceArrayListToBundle(String key, ArrayList<CharSequence> value) {
            bundle.putCharSequenceArrayList(key, value);
            return this;
        }

        public Builder putFloatToBundle(String key, float value) {
            bundle.putFloat(key, value);
            return this;
        }

        public Builder putFloatArrayToBundle(String key, float[] value) {
            bundle.putFloatArray(key, value);
            return this;
        }

        public Builder putIntegerArrayListToBundle(String key, ArrayList<Integer> value) {
            bundle.putIntegerArrayList(key, value);
            return this;
        }

        public Builder putIntToBundle(String key, int value) {
            bundle.putInt(key, value);
            return this;
        }

        public Builder putIntArrayToBundle(String key, int[] value) {
            bundle.putIntArray(key, value);
            return this;
        }

        public Builder putShortToBundle(String key, short value) {
            bundle.putShort(key, value);
            return this;
        }

        public Builder putShortArrayToBundle(String key, short[] value) {
            bundle.putShortArray(key, value);
            return this;
        }

        public Builder putParcelableToBundle(String key, Parcelable value) {
            bundle.putParcelable(key, value);
            return this;
        }

        public Builder putParcelableArrayToBundle(String key, Parcelable[] value) {
            bundle.putParcelableArray(key, value);
            return this;
        }

        public Builder putParcelableArrayListToBundle(String key, ArrayList<? extends Parcelable> value) {
            bundle.putParcelableArrayList(key, value);
            return this;
        }

        public Builder putStringToBundle(String key, String value) {
            bundle.putString(key, value);
            return this;
        }

        public Builder putStringArrayToBundle(String key, String[] value) {
            bundle.putStringArray(key, value);
            return this;
        }

        public Builder putStringArrayListToBundle(String key, ArrayList<String> value) {
            bundle.putStringArrayList(key, value);
            return this;
        }

        public Builder putSerializableToBundle(String key, Serializable value) {
            bundle.putSerializable(key, value);
            return this;
        }

        public Builder putBooleanToBundle(String key, boolean value) {
            bundle.putBoolean(key, value);
            return this;
        }

        public Builder putBooleanArrayToBundle(String key, boolean[] value) {
            bundle.putBooleanArray(key, value);
            return this;
        }

        public Builder putLongToBundle(String key, long value) {
            bundle.putLong(key, value);
            return this;
        }

        public Builder putLongArrayToBundle(String key, long[] value) {
            bundle.putLongArray(key, value);
            return this;
        }
    }

}
