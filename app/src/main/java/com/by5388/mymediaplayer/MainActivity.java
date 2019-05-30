package com.by5388.mymediaplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.by5388.mymediaplayer.play.PlayActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity implements WorkCallback, AdapterView.OnItemClickListener {

    private IMainApi mApi;
    private ListView mListView;
    private MediaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }


    private void loadData() {
        mApi.getRootFile();
    }

    private void initView() {
        mListView = findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApi.goBack();
            }
        });
    }

    private void initData() {
        mApi = new WorkDataThread(new Handler(), this);
        mAdapter = new MediaAdapter(this, new ArrayList<File>());
    }

    @Override
    public void push(List<File> fileList) {
        mAdapter.setFileList(fileList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File file = mAdapter.getItem(position);
        if (file == null) {
            return;
        }
        if (file.isDirectory()) {
            mApi.getFileList(file);
        } else {
            // TODO: 2019/5/21 各种文件执行相应的操作
            Toast.makeText(this, file.getName(), Toast.LENGTH_SHORT).show();
//            openFileByIntent(file);
            toPlayUI(file);
        }
    }

    /**
     * 通过Intent 来打开文件
     */
    private void openFileByIntent(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.fromFile(file));
        Intent chooser = Intent.createChooser(intent, "打开文件:" + file.getName());
        startActivity(chooser);
    }

    private void toPlayUI(File file){
        try {
            final Intent intent = PlayActivity.newIntent(this, file);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startLoading() {
        // TODO: 2019/5/21
        Toast.makeText(this, "开始查询", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishLoading() {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(int textId) {
        Toast.makeText(this, textId, Toast.LENGTH_SHORT).show();
    }
}
