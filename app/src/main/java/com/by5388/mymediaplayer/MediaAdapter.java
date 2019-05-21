package com.by5388.mymediaplayer;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * @author by5388  on 2019/5/20.
 */
final class MediaAdapter extends BaseAdapter {
    private List<File> mFileList;
    private final LayoutInflater mLayoutInflater;
    private static HashMap<String, Integer> mIcons = new HashMap<>();
    private static final String FILE_MP3 = ".mp3";
    private static final String FILE_M4A = ".m4a";
    private static final String FILE_DOC = ".doc";
    private static final String FILE_DOCX = ".docx";
    private static final String FILE_PDF = ".pdf";
    private static final String FILE_XML = ".xml";
    private static final String FILE_XLS = ".xls";
    private static final String FILE_XLSX = ".xlsx";
    private static final String FILE_JPG = ".jpg";
    private static final String FILE_JPEG = ".jpeg";
    private static final String FILE_PNG = ".png";
    private static final String FILE_GIF = ".gif";

    private static final String UN_KNOW = ".";

    MediaAdapter(Context context, List<File> fileList) {
        mLayoutInflater = LayoutInflater.from(context);
        mFileList = fileList;
        initIcons();
    }

    private void initIcons() {
        if (mIcons.isEmpty()) {
            mIcons.put(FILE_MP3, R.drawable.ic_music);
            mIcons.put(FILE_M4A, R.drawable.ic_music);
            mIcons.put(FILE_DOC, R.drawable.ic_doc);
            mIcons.put(FILE_DOCX, R.drawable.ic_doc);
            // TODO: 2019/5/21 add icon for this fileType
        }
    }

    @DrawableRes
    private static int getIcon(@NonNull File file) {
        final String lowerName = file.getName().toLowerCase();
        if (file.isFile() && mIcons.containsKey(lowerName)) {
            return mIcons.get(lowerName);
        }
        return getDirectoryIcon();
    }

    @DrawableRes
    private static int getDirectoryIcon() {
        return R.drawable.ic_file;
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
        private final ImageView mImageView;

        ViewHolder(View view) {
            mTextView = view.findViewById(R.id.textViewFileName);
            mImageView = view.findViewById(R.id.imageView_file_item);
        }

        void bind(@NonNull File file) {
            // TODO: 2019/5/20
            mTextView.setText(file.getName());
            mImageView.setImageResource(getIcon(file));
        }
    }


}
