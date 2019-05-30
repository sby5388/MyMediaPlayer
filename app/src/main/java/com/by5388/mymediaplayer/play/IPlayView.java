package com.by5388.mymediaplayer.play;

/**
 * @author Administrator  on 2019/5/30.
 */
public interface IPlayView {
    /**
     * SeekBar maxValue
     *
     * @param max 最大值:总时长
     */
    void setSeekBarMax(int max);

    /**
     * update progress 当前的进度
     *
     * @param progress progress
     */
    void updateSeekBarProgress(int progress);


}
