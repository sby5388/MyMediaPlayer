package com.by5388.mymediaplayer.play;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.by5388.mymediaplayer.R;
import com.by5388.mymediaplayer.other.LyricAdapter;
import com.by5388.mymediaplayer.other.ScrollingToLine;
import com.by5388.mymediaplayer.temp3.ByMediaPlayer;
import com.by5388.mymediaplayer.temp3.IByMediaPlayer;

import java.io.File;
import java.io.FileNotFoundException;

public class PlayActivity extends AppCompatActivity implements ScrollingToLine, IPlayView {
    private static final String TAG = "PlayActivity";
    /**
     * 传递参数：音频文件列表
     */
    private static final String ARRAY_FILE_PATH = "file";
    private AppCompatSeekBar mSeekBar;
    private ListView mListView;
    private LyricAdapter mAdapter;
    private File mFile;

    private IByMediaPlayer mMediaPlayer;
    private boolean mReadyToPlay = false;
    // TODO: 2019/5/30 看到一个Demo:是用Timer工具来实现seekBar更新的方式 ，其实也是可以使用一个Handler

    private boolean isPlaying = false;

    //1.4个按键能控制播放音乐
    //2.实现seekBar的显示与播放进度相吻合，seekBar拖动时能播放
    //3.实现ListView的自动滚动


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initData();
        initView();
        loadData();

    }

    private void loadData() {
        final Intent intent = getIntent();
        final String fileName = intent.getStringExtra(ARRAY_FILE_PATH);
        if (TextUtils.isEmpty(fileName)) {
            Log.e(TAG, "loadData: ", new FileNotFoundException(fileName));
            return;
        }
        mFile = new File(fileName);
        mMediaPlayer.init(mFile);
        mReadyToPlay = true;
    }

    private void initData() {
        mMediaPlayer = new ByMediaPlayer();
    }


    private void initView() {
        mSeekBar = findViewById(R.id.seekBar);
        mSeekBar.setMax(0);
        mSeekBar.setProgress(0);
        // TODO: 2019/5/30 需要设置拖拽监听器
        findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首次播放
                if (mReadyToPlay) {
                    mMediaPlayer.start();
                    mReadyToPlay = false;
                    isPlaying = true;
                    return;
                }
                if (isPlaying) {
                    mMediaPlayer.pause();
                    isPlaying = false;
                } else {
                    mMediaPlayer.resume();
                    isPlaying = true;
                }
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.stop();
            }
        });
    }

    @Override
    public void scrollToLine(int line) {
        // TODO: 2019/5/30 to update listView
    }


    public static Intent newIntent(Context context, File file) {
        final Intent intent = new Intent(context, PlayActivity.class);
        if (file != null && file.exists()) {
            intent.putExtra(ARRAY_FILE_PATH, file.getAbsolutePath());
        }
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    @Override
    public void setSeekBarMax(int max) {
        mSeekBar.setMax(max);
    }

    @Override
    public void updateSeekBarProgress(int progress) {
        // TODO: 2019/5/30 还有一个高版本的支持动画版本
        mSeekBar.setProgress(progress);
    }
}
