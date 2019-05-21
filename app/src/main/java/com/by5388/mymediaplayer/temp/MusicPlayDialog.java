package com.by5388.mymediaplayer.temp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.by5388.mymediaplayer.R;

import java.io.File;
import java.io.IOException;

/**
 * 音乐播放对话框
 *
 * @author Administrator  on 2019/5/18.
 */
public class MusicPlayDialog extends DialogFragment {
    public static final String TAG = "MusicPlayDialog";
    // TODO: 2019/5/18 甚至有一个播放队列 来实现播放的循环等

    private static final String FILE_PATH = "file_path";
    private TextView mTextView;
    private ToggleButton mToggleButton;
    private File mFile;
    private DialogInterface.OnClickListener mOnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                play();
            } else {
                pause();
            }
        }
    };

    private void play() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            // TODO: 2019/5/18 这里依然需要重新创建一个实例
            mMediaPlayer = new MediaPlayer();
        }

        if (mMediaPlayer == null) {
            Log.e(TAG, "play: mMediaPlayer == null");
            return;
        }
        // TODO: 2019/5/18 播放，停止，再次播放时 闪退
        if (mMediaPlayer.isPlaying()) {
            Log.e(TAG, "play: mMediaPlayer.isPlaying()");
            return;
        }
        try {
            mMediaPlayer.setDataSource(mFile.getAbsolutePath());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            mMediaPlayer.release();
        }
    }

    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            // TODO: 2019/5/18 音频文件播放完成回调
            Log.d(TAG, "onCompletion: ");
        }
    };


    static MusicPlayDialog newInstance(final File file) {
        Bundle args = new Bundle();
        args.putSerializable(FILE_PATH, file);
        MusicPlayDialog musicPlayDialog = new MusicPlayDialog();
        musicPlayDialog.setArguments(args);
        return musicPlayDialog;
    }

    public MusicPlayDialog() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
    }

    /**
     * 播放音乐
     *
     * @param musicFile 需要播放的文件
     */
    void playMusic(File musicFile) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        File file = (File) getArguments().getSerializable(FILE_PATH);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_music_player, null);
        // TODO: 2019/5/18 对话框的高度、宽度应该与Window,,,成某一个比例
        mTextView = view.findViewById(R.id.textViewName);
        mToggleButton = view.findViewById(R.id.toggleButton);
        mToggleButton.setOnCheckedChangeListener(mOnCheckedChangeListener);
        if (file != null) {
            mFile = file;
            mTextView.setText(mFile.getName());
        }
        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setPositiveButton(R.string.play_dialog, mOnClickListener)
                .setNegativeButton(R.string.hide_dialog, mOnClickListener)
                .setNeutralButton(R.string.stop_dialog, mOnClickListener)
                .create();

    }

    // TODO: 2019/5/18 应该通过一个后台服务来播放音频

    private static void temp(MediaPlayer mediaPlayer) throws IOException {
        if (mediaPlayer == null) {
            return;
        }
        //开始播放或者恢复播放
        mediaPlayer.start();
        //停止
        mediaPlayer.stop();
        //暂停
        mediaPlayer.pause();


        //媒体时长，毫秒
        int duration = mediaPlayer.getDuration();
        //当前播放位置，毫秒
        int currentPosition = mediaPlayer.getCurrentPosition();
        //设置播放位置，类似于拖动进度的效果
        mediaPlayer.seekTo(currentPosition);
        //是否处于循环的状态
        boolean looping = mediaPlayer.isLooping();
        //设置循环
        mediaPlayer.setLooping(looping);
        //是否处于播放的状态
        boolean playing = mediaPlayer.isPlaying();
        //以同步的方式装载流媒体文件
        mediaPlayer.prepare();
        //以异步的方式装载流媒体文件 TODO 与上面方式的差别
        mediaPlayer.prepareAsync();
        // TODO: 2019/5/18 在使用MediaPlayer播放一段流媒体的时候，
        // TODO 需要使用prepare()或prepareAsync()方法把流媒体装载进MediaPlayer，才可以调用start()方法播放流媒体。

        //回收媒体资源
        mediaPlayer.release();

        //设置当前流媒体播放完毕后，下一个执行的MediaPlayer
        mediaPlayer.setNextMediaPlayer(new MediaPlayer());
        //设置播放完成时回调。可以在这里设置下一曲？
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });
        //设置播放异常时回调函数
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        //当加载流媒体完成时的回调函数
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        //当拖动完成时的回调函数
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {

            }
        });
        //设置音频类型
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


    }
}
