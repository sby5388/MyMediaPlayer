package com.by5388.mymediaplayer.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @author Administrator  on 2019/5/24.
 */
public class LyricBroadReceiver extends BroadcastReceiver {

    private static final String ACTION_UPDATE = "com.by5388.mymediaplayer.other.LyricBroadReceiver.update";
    private static final String PARAM_POSITION = "PARAM_POSITION";
    private static final int DEFAULT_POSITION = -1;

    private UpdateCallback mCallback;

    public LyricBroadReceiver(UpdateCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (ACTION_UPDATE.equals(action)) {
            final int time = intent.getIntExtra(PARAM_POSITION, DEFAULT_POSITION);
            if (DEFAULT_POSITION != time) {
                mCallback.scrollTo(time);
            }
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LyricBroadReceiver.class);
    }


    public static IntentFilter getIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE);
        return intentFilter;
    }


    public static Intent sendUpdateBroadcast(int time) {
        Intent intent = new Intent(ACTION_UPDATE);
        intent.putExtra(PARAM_POSITION, time);
        return intent;
    }


    public interface UpdateCallback {
        /**
         * 广播接收时间
         *
         * @param time 时间
         */
        void scrollTo(int time);
    }


}
