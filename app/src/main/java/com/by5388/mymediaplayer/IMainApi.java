package com.by5388.mymediaplayer;

import java.io.File;

/**
 * @author by5388  on 2019/5/20.
 */
public interface IMainApi {
    void getRootFile();

    void getFileList(File file);

    /**
     * 返回上一级
     */
    void goBack();
}
