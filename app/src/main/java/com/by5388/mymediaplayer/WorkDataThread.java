package com.by5388.mymediaplayer;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 工作线程
 *
 * @author by5388  on 2019/5/20.
 */
public class WorkDataThread extends HandlerThread implements IMainApi {

    private static final int GET_FILE = 1;
    private static final String TAG = WorkDataThread.class.getSimpleName();
    private final Handler mUiHandler;
    private final WorkCallback mWorkCallback;
    private Handler mWorkHandler;
    private final File rootFile;

    /**
     * 当前所显示的文件夹
     */
    private File mCurrentDirectory;


    WorkDataThread(Handler uiHandler, WorkCallback workCallback) {
        super(TAG);
        mUiHandler = uiHandler;
        mWorkCallback = workCallback;
        rootFile = Environment.getExternalStorageDirectory();
        start();
        getLooper();
    }

    @Override
    protected void onLooperPrepared() {
        //在子线程中实例的，可以执行耗时操作
        mWorkHandler = new WorkHandler(this);
    }


    private static class WorkHandler extends Handler {
        private final WeakReference<WorkDataThread> mWeakReference;

        WorkHandler(WorkDataThread thread) {
            mWeakReference = new WeakReference<>(thread);
        }

        @Override
        public void handleMessage(Message msg) {
            WorkDataThread thread = mWeakReference.get();
            if (thread == null) {
                return;
            }
            // TODO: 2019/5/20
            switch (msg.what) {
                case GET_FILE:
                    File file = (File) msg.obj;
                    getSubFileList(file);
                    break;

                default:
                    break;
            }
            removeMessages(msg.what);
        }

        private void getSubFileList(File file) {
            final WorkDataThread workDataThread = mWeakReference.get();
            if (workDataThread == null) {
                return;
            }
            if (file == null || file.isFile()) {
                return;
            }
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                workDataThread.mUiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        workDataThread.mWorkCallback.toast("这是一个空文件夹!");
                    }
                });
                return;
            }
            final List<File> list = new ArrayList<>();
            Collections.addAll(list, files);
            Collections.sort(list);

            workDataThread.mUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    workDataThread.mWorkCallback.push(list);
                }
            });

        }
    }

    @Override
    public void getRootFile() {
        getFileList(rootFile);
    }

    @Override
    public void getFileList(File file) {
        if (file == null) {
            return;
        }
        mWorkHandler.obtainMessage(GET_FILE, file).sendToTarget();
        // TODO: 2019/5/21 退出去时，如何保持有序性
        mCurrentDirectory = file;
    }

    @Override
    public void goBack() {
        // TODO: 2019/5/21
        final File file = mCurrentDirectory;
        if (file != null) {
            File parentFile = file.getParentFile();
            if (parentFile != null && parentFile.exists()) {
                getFileList(parentFile);
            }
        }

    }
}
