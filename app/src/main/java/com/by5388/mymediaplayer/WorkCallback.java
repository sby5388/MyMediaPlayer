package com.by5388.mymediaplayer;

import java.io.File;
import java.util.List;

/**
 * @author by5388  on 2019/5/20.
 */
public interface WorkCallback {
    /**
     * 推送查询结果
     *
     * @param fileList file
     */
    void push(List<File> fileList);

    /**
     * 开始加载，如屏蔽了某些按键的点击
     */
    void startLoading();

    /**
     * 加载完成：如某些按钮点击可用了
     */
    void finishLoading();

    /**
     * 取消加载任务
     */
    void cancelLoading();

    /**
     * 提示
     *
     * @param text 提示
     */
    void toast(String text);

    /**
     * 提示
     *
     * @param textId 提示
     */
    void toast(int textId);
}
