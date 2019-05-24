package com.by5388.mymediaplayer.other;

import android.widget.BaseAdapter;

/**
 * @author Administrator  on 2019/5/24.
 */
abstract class BaseLyricAdapter extends BaseAdapter implements ScrollingToTime {
    abstract void setLyric(Lyric lyric);
}
