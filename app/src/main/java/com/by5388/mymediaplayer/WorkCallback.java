package com.by5388.mymediaplayer;

import java.io.File;
import java.util.List;

/**
 * @author by5388  on 2019/5/20.
 */
public interface WorkCallback {
    void push(List<File> fileList);
}
