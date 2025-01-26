package com.gekocaretaker.gekosmagic.util;

public abstract class EffectTime {
    public static final int MIN_100 = 120000;
    public static final int HOUR = 72000;
    public static final int MIN_45 = 54000;
    public static final int MIN_30 = 36000;
    public static final int MIN_20 = 24000;
    public static final int MIN_15 = 18000;
    public static final int MIN_10 = 12000;
    public static final int MIN_9 = 10800;
    public static final int MIN_8 = 9600;
    public static final int MIN_7 = 8400;
    public static final int MIN_6 = 7200;
    public static final int MIN_5 = 6000;
    public static final int MIN_4 = 4800;
    public static final int MIN_3 = 3600;
    public static final int MIN_2 = 2400;
    public static final int MIN_1p5 = 1800;
    public static final int MIN_1 = 1200;
    public static final int SEC_45 = 900;
    public static final int SEC_40 = 800;
    public static final int SEC_22p5 = 450;
    public static final int SEC_21p6 = 432;
    public static final int SEC_20 = 400;
    public static final int INSTANT = 1;

    public static int asHours(int hours) {
        return asMinutes(hours * 60);
    }

    public static int asMinutes(int minutes) {
        return asSeconds(minutes * 60);
    }

    public static int asSeconds(int seconds) {
        return seconds * 20;
    }

    private EffectTime() {}
}
