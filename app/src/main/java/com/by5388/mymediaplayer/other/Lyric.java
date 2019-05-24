package com.by5388.mymediaplayer.other;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator  on 2019/5/24.
 */
public class Lyric implements Serializable {
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

    }

    Lyric() {
        lineCount = 0;
        duration = 0;
        mLyricTexts = new ArrayList<>();
    }

}
