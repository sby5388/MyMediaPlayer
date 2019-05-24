package com.by5388.mymediaplayer.other;

/**
 * @author Administrator  on 2019/5/24.
 */
final class LyricText {
    private final int showTime;
    private final String text;

    LyricText(int showTime, String text) {
        this.showTime = showTime;
        this.text = text;
    }

    public int getShowTime() {
        return showTime;
    }

    public String getText() {
        return text;
    }
}
