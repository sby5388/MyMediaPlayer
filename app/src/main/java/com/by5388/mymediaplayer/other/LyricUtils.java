package com.by5388.mymediaplayer.other;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator  on 2019/5/24.
 */
class LyricUtils {
    private static final char START_TAG = '[';
    private static final char END_TAG = ']';
    private static final String TIME_SPILT = ":";
    private static final int SHOW_TIME_STRING_ARRAY_LENGTH = 3;


    static void initLyric(@NonNull Lyric lyric, @NonNull File file) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        ) {

            String line;
            int duration = 0;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    final int indexStart = line.indexOf(START_TAG);
                    final int indexEnd = line.indexOf(END_TAG);
                    final String time = line.substring(indexStart + 1, indexEnd);

                    if (isShowTime(time)) {
                        final String text = line.substring(indexEnd + 1);
                        final int showTime = getShowTime(time);
                        if (showTime != 0) {
                            //最后一个作为长度
                            duration = showTime;
                        }
                        lyric.mLyricTexts.add(new LyricText(count,showTime, text ));
                        count++;
                    }
                }
            }
            lyric.lineCount = count;
            lyric.duration = duration;
        } catch (IOException e) {
            lyric.initWithNothing();
        }


    }

    /**
     * 时间检测：2个:作为分隔符，分成三个部分
     * 合法的时间格式
     * 如何高效地把字符转化成时间
     */
    private static int getShowTime(String timeText) {
        final String replace = timeText.replace(":", "").replace(".", "");
        int retval = 0;
        try {
            // TODO: 2019/5/28 这里的处理不好
            int integer = Integer.parseInt(replace);
            final int minutes = integer >= 10000 ? (integer / 10000) : 0;
            final int seconds = integer % 10000 / 100;
            final int milliSeconds = integer % 100;
            retval = (int) (TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds) + TimeUnit.MILLISECONDS.toMillis(milliSeconds));
        } catch (NumberFormatException e) {
            System.err.println(e.getLocalizedMessage());
        }
        return retval;
    }

    private static boolean isShowTime(String time) {
        boolean isShowTime = true;
        if (time == null || time.isEmpty()) {
            isShowTime = false;
        } else {
            // 2019/5/28 第二个是逗号，非冒号
            time = time.replace('.', ':');
            final String[] timeArrays = time.split(TIME_SPILT);
            if (timeArrays.length != SHOW_TIME_STRING_ARRAY_LENGTH) {
                isShowTime = false;
            }
        }
        return isShowTime;

    }
}
