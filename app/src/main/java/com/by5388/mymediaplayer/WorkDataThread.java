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


    public WorkDataThread(Handler uiHandler, WorkCallback workCallback) {
        super(TAG);
        mUiHandler = uiHandler;
        mWorkCallback = workCallback;
        rootFile = Environment.getExternalStorageDirectory();
        start();
        getLooper();
    }

    @Override
    protected void onLooperPrepared() {
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

            if (file == null || file.isFile()) {
                return;
            }
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                return;
            }
            final List<File> list = new ArrayList<>();
            Collections.addAll(list, files);
            final WorkDataThread workDataThread = mWeakReference.get();
            if (workDataThread == null) {
                return;
            }
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
        mWorkHandler.obtainMessage(GET_FILE, file).sendToTarget();
    }
}
