package com.by5388.mymediaplayer.other;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by5388.mymediaplayer.R;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * @author Administrator  on 2019/5/24.
 */
public class LyricAdapter extends BaseLyricAdapter {
    private static final String TAG = "LyricAdapter";
    private static final int SCROLL_LYRIC = 10;

    private final LayoutInflater mInflater;
    private Lyric mLyric;
    private final ScrollingToLine mScrollingToLine;
    private int currentIndex = 0;

    private Handler mHandler;

    LyricAdapter(Context context, Lyric lyric, ScrollingToLine scrollingToLine) {
        mInflater = LayoutInflater.from(context);
        mLyric = lyric;
        // TODO: 2019/5/24
        mHandler = new LyricHandler(this);
        mScrollingToLine = scrollingToLine;

    }

    @Override
    public void scrollToTime(int time) {
        int currentIndex = this.currentIndex;
        final List<LyricText> lyricTexts = mLyric.mLyricTexts;
        for (int i = currentIndex; i < lyricTexts.size(); i++) {
            // TODO: 2019/5/28
            if (lyricTexts.get(i).getShowTime() >= time) {
                final Message message = mHandler.obtainMessage(SCROLL_LYRIC);
                message.arg1 = lyricTexts.get(i).getLineIndex();
                message.sendToTarget();
                this.currentIndex = i;
                break;
            }
        }

    }

    @Override
    void setLyric(Lyric lyric) {
        // TODO: 2019/5/24
        this.mLyric = lyric;

    }

    @Override
    public int getCount() {
        return mLyric.mLyricTexts.size();
    }

    @Override
    public LyricText getItem(int position) {
        return mLyric.mLyricTexts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_lyric, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTextView.setText(getItem(position).getText());

        return convertView;
    }

    static class ViewHolder {
        private final TextView mTextView;

        ViewHolder(View view) {
            mTextView = view.findViewById(R.id.showText);
        }
    }

    private static class LyricHandler extends Handler {
        private WeakReference<LyricAdapter> mReference;

        LyricHandler(LyricAdapter adapter) {
            mReference = new WeakReference<>(adapter);
        }

        @Override
        public void handleMessage(Message msg) {
            final LyricAdapter adapter = mReference.get();
            if (adapter == null) {
                Log.e(TAG, "handleMessage: adapter == null");
                return;
            }
            switch (msg.what) {
                case SCROLL_LYRIC:
                    final int line = msg.arg1;
                    // TODO: 2019/5/24
                    adapter.mScrollingToLine.scrollToLine(line);
                    break;
                default:
                    break;
            }
        }
    }

}
