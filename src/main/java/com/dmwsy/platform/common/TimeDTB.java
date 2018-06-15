package com.dmwsy.platform.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TimeDTB implements Serializable {

    protected List time = new ArrayList<Integer>();
    protected double sum = 0.0;;
    public static final int DAYLONG = 24 * 60;
    public static final int DAYSTART = 0;
    public static final int DAYEND = DAYLONG - 1;

    public double getSum() {
        return this.sum;
    }

    public TimeDTB() {
        for (int i = 0; i < DAYLONG; ++i) {
            this.time.add(0);
        }
    }

    public void addItem(Period period) {
        if (period.houra > 23) {
            int start = 60 * period.hourb + period.minuteb;
            int end = 60 * (period.houra - 24) + period.minutea;
            for (int i = start; i <= DAYEND; ++i) {
                time.set(i, (Integer) time.get(i) + 1);
                sum += 1;
            }
            for (int i = DAYSTART; i <= end; ++i) {
                time.set(i, (Integer) time.get(i) + 1);
                sum += 1;
            }
        } else {
            int start = 60 * period.hourb + period.minuteb;
            int end = 60 * period.houra + period.minutea;
            for (int i = start; i <= end; ++i) {
                time.set(i, (Integer) time.get(i) + 1);
                sum += 1;
            }
        }
    }

    public void delItem(Period period) {
        if (period.houra > 23) {
            int start = 60 * period.hourb + period.minuteb;
            int end = 60 * (period.houra - 24) + period.minutea;
            for (int i = start; i <= DAYEND; ++i) {
                time.set(i, (Integer) time.get(i) - 1);
                sum -= 1;
            }
            for (int i = DAYSTART; i <= end; ++i) {
                time.set(i, (Integer) time.get(i) - 1);
                sum -= 1;
            }
        } else {
            int start = 60 * period.hourb + period.minuteb;
            int end = 60 * period.houra + period.minutea;
            for (int i = start; i <= end; ++i) {
                time.set(i, (Integer) time.get(i) - 1);
                sum -= 1;
            }
        }
    }

    public double proportion(Period period) {
        double proportion = 0;
        if (period.houra > 23) {
            int start = 60 * period.hourb + period.minuteb;
            int end = 60 * (period.houra - 24) + period.minutea;
            for (int i = start; i <= DAYEND; ++i) {
                proportion += (Integer) time.get(i);
            }
            for (int i = DAYSTART; i <= end; ++i) {
                proportion += (Integer) time.get(i);
            }
        } else {
            int start = 60 * period.hourb + period.minuteb;
            int end = 60 * period.houra + period.minutea;
            for (int i = start; i <= end; ++i) {
                proportion += (Integer) time.get(i);
            }
        }

        return proportion / this.sum;
    }

    public String toString() {
        String ret = "";
        for (int i = 0; i <= 23; ++i) {
            double p = 0.0;
            for (int j = 0; j <= 59; ++j) {
                p += (Integer) time.get(i * 60 + j);
            }
            p = p / sum;
            ret += String.format("|%d: %f|", i, p);
        }

        return ret;
    }
}
