package com.by5388.mymediaplayer.other;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * @author Administrator  on 2019/5/28.
 */
public class LyricTest {
    private File mFile;
    private Lyric mSubject;

    @Before
    public void setUp() throws Exception {
        mFile = new File("src/main/assets/608604843.lrc");
        System.out.println(mFile.getAbsolutePath());
        if (!mFile.exists()) {
            throw new RuntimeException("file not exist");
        }
        mSubject = new Lyric(mFile);
    }

    @Test
    public void init() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
//        System.out.println(mSubject.toString());
        final List<LyricText> lyricTexts = mSubject.mLyricTexts;
        for (LyricText lyricText : lyricTexts) {
            System.out.println(lyricText.getShowTime() + " : " + lyricText.getText());
        }
    }

}