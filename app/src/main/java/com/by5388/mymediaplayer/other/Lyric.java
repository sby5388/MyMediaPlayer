package com.by5388.mymediaplayer.other;

import android.util.Log;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator  on 2019/5/24.
 */
public class Lyric implements Serializable {
    // TODO: 2019/5/27 check fileName's suffix
    public static final String LYRIC_SUFFIX = ".lrc";
    /**
     * 行数
     */
    private int lineCount;
    /**
     * 时长：单位秒
     */
    private int duration;

    List<LyricText> mLyricTexts;

    Lyric(File file) {
        if (file == null || file.isDirectory() || file.exists() || !file.getName().toLowerCase().endsWith(LYRIC_SUFFIX)) {
            Log.e(TAG, "Lyric: ", new IllegalArgumentException());
            this();
        } else {

        }
    }

    Lyric() {
        lineCount = 0;
        duration = 0;
        mLyricTexts = new ArrayList<>();
    }

}
