package com.by5388.mymediaplayer;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;

/**
 * @author by5388  on 2019/5/20.
 */
public final class MediaPlayerService extends IntentService {
    private static final String TAG = "MediaPlayerService";
    private static final String ACTION_PLAY = "play";
    private MediaPlayer mMediaPlayer;


    public static Intent newIntentToPlay(Context context, File file) {
        Intent intent = new Intent(context, MediaPlayerService.class);
        intent.setAction(ACTION_PLAY);
        intent.setData(Uri.fromFile(file));
        return intent;
    }





    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return;
        }
        final String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }

        switch (action) {
            case ACTION_PLAY:

                break;
            default:
                break;
        }

    }

    public MediaPlayerService() {
        super(TAG);
        resetMediaPlayer();
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        
    }

    private void resetMediaPlayer() {
    }

}
