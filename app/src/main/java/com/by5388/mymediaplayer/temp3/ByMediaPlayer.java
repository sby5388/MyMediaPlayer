package com.by5388.mymediaplayer.temp3;

import android.media.MediaPlayer;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * TODO 在构造方法中传递 相应的文件
 *
 * @author Administrator  on 2019/5/29.
 */
public final class ByMediaPlayer implements IByMediaPlayer, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener {
    private static final String TAG = "ByMediaPlayer";
    /**
     * 实际的播放器
     */
    private MediaPlayer mMediaPlayer;
    /**
     * 当前的播放位置
     */
    private int mCurrentPosition;

    /**
     * 播放的准备
     */
    private boolean mReadyPlay = false;

    @Override
    public void init(File file) {
        checkStatus();
        try {
            mMediaPlayer.setDataSource(file.getAbsolutePath());
            mReadyPlay = true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "init: ", e);
            mReadyPlay = false;
        }
    }

    /**
     * 检查当前播放器的状态，是否需要重置
     */
    private void checkStatus() {
        if (mMediaPlayer != null) {
            // TODO: 2019/5/29 释放所有的资源！不知道顺序是否正确
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
        }
        mMediaPlayer = new MediaPlayer();
    }

    @Override
    public void start() {
        if (mReadyPlay) {
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(this);
        } else {
            Log.e(TAG, "start: Not Ready to start()", new Exception());
        }
    }


    @Override
    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            final int currentPosition = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.pause();
            mCurrentPosition = currentPosition;
        }
    }

    @Override
    public void resume() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            seekTo(mCurrentPosition);
        } else {
            mCurrentPosition = 0;
        }
    }


    @Override
    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }

    @Override
    public void seekTo(int position) {
        if (mMediaPlayer == null) {
            Log.e(TAG, "seekTo: ", new Exception("mMediaPlayer == null"));
            return;
        }
        if (mReadyPlay) {
            // TODO: 2019/5/29 这里还有另一个重载的函数！
            mMediaPlayer.seekTo(position);
            mMediaPlayer.setOnSeekCompleteListener(this);
        }


    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
