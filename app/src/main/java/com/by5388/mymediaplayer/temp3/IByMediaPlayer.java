package com.by5388.mymediaplayer.temp3;

import java.io.File;

/**
 * @author Administrator  on 2019/5/29.
 */
public interface IByMediaPlayer {

    // TODO: 2019/5/29 网络播放器的实现：不仅能够播放本地的，还要能够播放Uri/URL

    /**
     * 播放
     */
    void start();

    /**
     * 暂停
     */
    void pause();

    /**
     * 恢复
     */
    void resume();

    /**
     * 停止
     */
    void stop();

    /**
     * 初始化
     *
     * @param file 需要播放的文件
     */
    void init(File file);

    /**
     * 跳转到指定位置
     *
     * @param position 毫秒
     */
    void seekTo(int position);


}
