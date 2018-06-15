package com.dmwsy.platform.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TvTimeDTB implements Serializable {

    protected Map tvtime = new HashMap<Integer, TimeDTB>();
    protected double sum = 0.0;

    public TvTimeDTB() {
    }

    public void addItem(int tvid, Period period) {
        if (this.tvtime.containsKey(tvid)) {
            TimeDTB dtb = (TimeDTB) this.tvtime.get(tvid);
            dtb.addItem(period);
        } else {
            TimeDTB dtb = new TimeDTB();
            dtb.addItem(period);
            this.tvtime.put(tvid, dtb);
        }

        sum += period.elapseM();
    }

    public double getSum() {
        return this.sum;
    }

    public double proportion(int tvid, Period period) {
        if (this.tvtime.containsKey(tvid)) {
            TimeDTB dtb = (TimeDTB) this.tvtime.get(tvid);
            double p = dtb.proportion(period);
            return p * dtb.getSum() / sum;
        }

        return 0;
    }

    public String toString() {
        String ret = "";
        for (Iterator<Integer> iter = this.tvtime.keySet().iterator(); iter.hasNext();) {
            int key = iter.next();
            ret += String.format("%d: %s\n", key, ((TimeDTB) this.tvtime.get(key)).toString());
        }
        ret.trim();

        return ret;
    }
}
