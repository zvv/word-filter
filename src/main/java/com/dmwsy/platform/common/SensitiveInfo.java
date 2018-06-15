package com.dmwsy.platform.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SensitiveInfo implements Info, Serializable {
    protected long id;
    protected String term = "";

    public SensitiveInfo() {
    }

    public SensitiveInfo(long id_, String term_) {
        this.id = id_;
        this.term = term_;
    }

    public String toString() {
        return String.format("%d | %s", this.id, this.term);
    }

    public String[] getString() {
        String str_ca = StringMachine.trimPunctuation(this.term, "");
        str_ca = StringMachine.trimSeparator(str_ca, "");
        str_ca = str_ca.toLowerCase();
        str_ca = StringMachine.insertBlank(str_ca);

        return new String[] { str_ca };
    }

    public String[] getPinYin() {

        Transformers transformers = new Transformers();

        String str = StringMachine.trimPunctuation(this.term, "");
        str = StringMachine.trimSeparator(str, "");
        str = str.toLowerCase();
        List strs = transformers.getPinYinM(str);
        List ret = new ArrayList<String>();
        Iterator<String> iter = strs.iterator();
        while (iter.hasNext()) {
            String item = iter.next();
            if (!item.replaceAll(" ", "").equals(str)) {
                ret.add(item);
            }
        }

        return (String[]) ret.toArray(new String[ret.size()]);

        // return new String[0];
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id_) {
        this.id = id_;
    }

    public String getTerm() {
        return this.term;
    }

    public void setTerm(String term_) {
        this.term = term_;
    }

    public static void main(String[] args) {
        SensitiveInfo a = new SensitiveInfo(1, "毛泽东");
        System.out.println(a.getString().toString());
    }
}
