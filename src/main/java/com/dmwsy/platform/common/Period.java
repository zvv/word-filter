package com.dmwsy.platform.common;

import java.util.Date;

public class Period {

    public int hourb;
    public int minuteb;
    public int secondb;
    public int houra;
    public int minutea;
    public int seconda;

    private Period() {
    }

    public Period(Date dateb, Date datea) {
        this.hourb = dateb.getHours();
        this.minuteb = dateb.getMinutes();
        this.secondb = dateb.getSeconds();
        this.houra = datea.getHours();
        this.minutea = datea.getMinutes();
        this.seconda = datea.getSeconds();

        this.houra = this.houra < this.hourb ? 24 + this.houra : this.houra;
    }

    public Period(int hourb_, int minuteb_, int secondb_, int houra_, int minutea_, int seconda_) {
        this.hourb = hourb_;
        this.minuteb = minuteb_;
        this.secondb = secondb_;
        this.houra = houra_;
        this.minutea = minutea_;
        this.seconda = seconda_;

        this.houra = this.houra < this.hourb ? 24 + this.houra : this.houra;
    }

    public int elapseH() {
        return this.houra - this.hourb + 1;
    }

    public int elapseM() {
        return this.houra * 60 + this.minutea - this.hourb * 60 - this.minuteb + 1;
    }

    public int elapseS() {
        return this.houra * 3600 + this.minutea * 60 + this.seconda - this.hourb * 3600 - this.minuteb * 60
                - this.secondb + 1;
    }

    public static int comparea(Period tp1, Period tp2) {
        if (tp1.houra == tp2.houra) {
            if (tp1.minutea == tp2.minutea) {
                if (tp1.seconda == tp2.seconda) {
                    return 0;
                } else {
                    return tp1.seconda - tp2.seconda;
                }
            } else {
                return tp1.minutea - tp2.minutea;
            }
        } else {
            return tp1.houra - tp2.houra;
        }
    }

    public static int compareb(Period tp1, Period tp2) {
        if (tp1.hourb == tp2.hourb) {
            if (tp1.minuteb == tp2.minuteb) {
                if (tp1.secondb == tp2.secondb) {
                    return 0;
                } else {
                    return tp1.secondb - tp2.secondb;
                }
            } else {
                return tp1.minuteb - tp2.minuteb;
            }
        } else {
            return tp1.hourb - tp2.hourb;
        }
    }

    public static int compareba(Period tp1, Period tp2) {
        if (tp1.hourb == tp2.houra) {
            if (tp1.minuteb == tp2.minutea) {
                if (tp1.secondb == tp2.seconda) {
                    return 0;
                } else {
                    return tp1.secondb - tp2.seconda;
                }
            } else {
                return tp1.minuteb - tp2.minutea;
            }
        } else {
            return tp1.hourb - tp2.houra;
        }
    }

    public static Period intersection(Period tp1, Period tp2) {
        if (compareba(tp1, tp2) <= 0 && compareb(tp1, tp2) >= 0) {
            if (comparea(tp1, tp2) <= 0) {
                return tp1;
            } else {
                return new Period(tp1.hourb, tp1.minuteb, tp1.secondb, tp2.houra, tp2.minutea, tp2.seconda);
            }
        } else if (compareba(tp2, tp1) <= 0 && compareb(tp2, tp1) >= 0) {
            if (comparea(tp2, tp1) <= 0) {
                return tp2;
            } else {
                return new Period(tp2.hourb, tp2.minuteb, tp2.secondb, tp1.houra, tp1.minutea, tp1.seconda);
            }
        }

        return null;
    }

    public String toString() {
        return String.format("%02d:%02d:%02d~%02d:%02d:%02d", this.hourb, this.minuteb, this.secondb,
                this.houra > 23 ? this.houra - 24 : this.houra, this.minutea, this.seconda);
    }
}
