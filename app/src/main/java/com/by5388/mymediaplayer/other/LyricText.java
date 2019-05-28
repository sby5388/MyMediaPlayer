package com.by5388.mymediaplayer.other;

/**
 * @author Administrator  on 2019/5/24.
 */
final class LyricText {
    private final int showTime;
    private final String text;
    private final int lineIndex;

    LyricText(int lineIndex, int showTime, String text) {
        this.showTime = showTime;
        this.text = text;
        this.lineIndex = lineIndex;
    }

    public int getShowTime() {
        return showTime;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "LyricText{" +
                "showTime=" + showTime +
                ", text='" + text + '\'' +
                '}';
    }

    public int getLineIndex() {
        return lineIndex;
    }
}
