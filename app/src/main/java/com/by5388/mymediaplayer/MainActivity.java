package com.by5388.mymediaplayer;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
            Toast.makeText(this, file.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
