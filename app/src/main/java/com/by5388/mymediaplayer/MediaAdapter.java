package com.by5388.mymediaplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * @author by5388  on 2019/5/20.
 */
final class MediaAdapter extends BaseAdapter {
    private List<File> mFileList;
    private final LayoutInflater mLayoutInflater;

    MediaAdapter(Context context, List<File> fileList) {
        mLayoutInflater = LayoutInflater.from(context);
        mFileList = fileList;
    }

    void setFileList(List<File> fileList) {
        mFileList = fileList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFileList.size();
    }

    @Override
    public File getItem(int position) {
        return mFileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_file, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bind(getItem(position));

        return convertView;
    }


    private static class ViewHolder {
        private final TextView mTextView;

        ViewHolder(View view) {
            mTextView = view.findViewById(R.id.textViewFileName);
        }

        void bind(File file) {
            // TODO: 2019/5/20
            mTextView.setText(file.getName());
        }
    }
}
