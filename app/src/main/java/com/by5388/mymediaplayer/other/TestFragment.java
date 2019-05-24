package com.by5388.mymediaplayer.other;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.by5388.mymediaplayer.R;

/**
 * @author Administrator  on 2019/5/24.
 */
public final class TestFragment extends Fragment
        implements ScrollingToLine, LyricBroadReceiver.UpdateCallback {
    private ListView mListView;
    private BaseLyricAdapter mAdapter;
    private static final String BUNDLE_LYRIC = "bundleLyric";
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter = LyricBroadReceiver.getIntentFilter();

    public static TestFragment newInstance(Lyric lyric) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        // TODO: 2019/5/24
        args.putSerializable(BUNDLE_LYRIC, lyric);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void scrollToLine(int line) {
        if (line < mAdapter.getCount()) {
            mListView.smoothScrollToPosition(line);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mListView = view.findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiver = new LyricBroadReceiver(this);
        // TODO: 2019/5/24
        final Bundle args = getArguments();
        Lyric lyric = null;
        if (args != null) {
            lyric = (Lyric) args.getSerializable(BUNDLE_LYRIC);
        }
        if (null == lyric) {
            lyric = new Lyric();
        }
        mAdapter = new LyricAdapter(getContext(), lyric, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        final Context context = getContext();
        if (context != null) {
            context.unregisterReceiver(mReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: 2019/5/24
        final Context context = getContext();
        if (context != null) {
            context.registerReceiver(mReceiver, mIntentFilter);
        }

    }

    @Override
    public void scrollTo(int time) {
        mAdapter.scrollToTime(time);
    }


}
