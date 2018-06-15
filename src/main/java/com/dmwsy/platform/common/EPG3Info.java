package com.dmwsy.platform.common;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EPG3Info implements Info, Serializable {
    protected long id;
    protected String caname = "";
    protected String catype = "";
    protected Set words = new HashSet<String>();
    protected TvTimeDTB tvtimeDTB = new TvTimeDTB();
    protected WordDTB wordDTB = new WordDTB();
    protected double sum = 0;

    public EPG3Info() {
    }

    public EPG3Info(long id_, String caname_, String catype_) {
        this.id = id_;
        this.caname = caname_;
        this.catype = catype_;
    }

    public void addItem(List wordlist, int tvid, Period period) {
        // cd_name word
        for (Iterator<String> iter = wordlist.iterator(); iter.hasNext();) {
            String word = iter.next();
            this.wordDTB.addItem(word);
            words.add(word);
        }

        // tv-time cocurrency
        this.tvtimeDTB.addItem(tvid, period);

        sum += 1;
    }

    public double proportionTvTime(int tvid, Period period) {
        return this.tvtimeDTB.proportion(tvid, period);
    }

    public double proportionWord(String word) {
        return this.wordDTB.proportion(word);
    }

    public WordDTB getWordDTB() {
        return this.wordDTB;
    }

    public TvTimeDTB getTvTimeDTB() {
        return this.tvtimeDTB;
    }

    public double getSum() {
        return this.sum;
    }

    public String toString() {
        return String.format("%d | %s | %s", this.id, this.caname, this.catype);
    }

    public String[] getString() {
        String[] ret = new String[this.words.size()];
        int index = 0;
        for (Iterator<String> iter = this.words.iterator(); iter.hasNext();) {
            ret[index] = StringMachine.insertBlank(iter.next());
            index += 1;
        }
        // return (String[])this.words.toArray(new String[this.words.size()]);
        return ret;
    }

    public String[] getPinYin() {
        return new String[0];
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id_) {
        this.id = id_;
    }

    public String getCaType() {
        return this.catype;
    }

    public void setCaType(String catype_) {
        this.catype = catype_;
    }

    public String getCaName() {
        return this.caname;
    }

    public void setCaName(String caname_) {
        this.caname = caname_;
    }
}
