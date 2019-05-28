package com.by5388.mymediaplayer.other;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator  on 2019/5/28.
 */
public class TimeTest {
    @Test
    public void test() {
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(258006);
        System.out.println(minutes);
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(258006) - TimeUnit.MINUTES.toSeconds(minutes));
    }
}
