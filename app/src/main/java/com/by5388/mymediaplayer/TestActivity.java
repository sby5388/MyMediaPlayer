package com.by5388.mymediaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;

import com.by5388.mymediaplayer.other.Lyric;
import com.by5388.mymediaplayer.other.LyricFragment;

import java.io.File;
import java.util.concurrent.Executor;

/**
 * @author Administrator  on 2019/5/28.
 */
public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    private static final String TAG_LYRIC_FRAGMENT = "lyricFragment";
    private LyricFragment mLyricFragment;
    private Executor mExecutor = MediaPlayerApp.getsThreadPoolExecutor();
    private Handler mHandler = new Handler();
    private MediaPlayer mMediaPlayer;
    private MediaController.MediaPlayerControl mMediaPlayerControl;
    private MediaController mMediaController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mLyricFragment = LyricFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.container, mLyricFragment, TAG_LYRIC_FRAGMENT).commit();
        initData();
    }

    private void initData() {
//        mMediaPlayer = new MediaPlayer();
    }

    private void refreshData(Lyric lyric) {
        mLyricFragment = LyricFragment.newInstance(lyric);
        updateFragment(mLyricFragment);
    }


    private void showUpdate(File file) {
        mExecutor.execute(new GenerateLyric(file));
    }

    private class GenerateLyric implements Runnable {
        private File mFile;

        GenerateLyric(File file) {
            mFile = file;
        }

        @Override
        public void run() {
            final Lyric lyric = new Lyric(mFile);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    refreshData(lyric);
                }
            });
        }
    }

    private void updateFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .show(fragment)
                .commit();
    }

}
