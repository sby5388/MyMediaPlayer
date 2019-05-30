package com.by5388.mymediaplayer.other;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator  on 2019/5/24.
 */
public final class Lyric implements Serializable {
    private static final String TAG = "Lyric";
    // TODO: 2019/5/27 check fileName's suffix
    private static final String LYRIC_SUFFIX = ".lrc";
    /**
     * 行数
     */
    int lineCount;
    /**
     * 时长：单位秒
     */
    int duration;

    String ti = "";
    String ar = "";
    String al = "";
    String by = "";

    private int offset;

    List<LyricText> mLyricTexts;

    public Lyric(File file) {
        initWithNothing();
        if (file == null || file.isDirectory() || !file.exists() || !file.getName().toLowerCase().endsWith(LYRIC_SUFFIX)) {
            System.err.println(new IllegalArgumentException("Lyric(File file)"));
        } else {
            LyricUtils.initLyric(this, file);
        }
    }

    public Lyric() {
        initWithNothing();
    }

    void initWithNothing() {
        lineCount = 0;
        duration = 0;
        mLyricTexts = new ArrayList<>();
    }


    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTi() {
        return ti;
    }

    public void setTi(String ti) {
        this.ti = ti;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public String getAl() {
        return al;
    }

    public void setAl(String al) {
        this.al = al;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<LyricText> getLyricTexts() {
        return mLyricTexts;
    }

    public void setLyricTexts(List<LyricText> lyricTexts) {
        mLyricTexts = lyricTexts;
    }

    @Override
    public String toString() {
        return "Lyric{" +
                "lineCount=" + lineCount +
                ", duration=" + duration +
                ", ti='" + ti + '\'' +
                ", ar='" + ar + '\'' +
                ", al='" + al + '\'' +
                ", by='" + by + '\'' +
                ", offset=" + offset +
                ", mLyricTexts=" + mLyricTexts +
                '}';
    }
}
