package com.dmwsy.platform.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ADInfo implements Info, Serializable {
    protected long id;
    protected String name = "";
    protected String type = "";

    public ADInfo() {
    }

    public ADInfo(long id_, String name_, String type_) {
        this.id = id_;
        this.name = name_;
        this.type = type_;
    }

    public String toString() {
        return String.format("%d | %s | %s", this.id, this.name, this.type);
    }

    public String[] getString() {
        List ret = new ArrayList<String>();
        for (String unit : this.name.split("/")) {
            String str = StringMachine.trimPunctuation(unit, "");
            str = str.toLowerCase();
            str = StringMachine.insertBlank(str);
            ret.add(str);
        }

        return (String[]) ret.toArray(new String[ret.size()]);
    }

    public String[] getPinYin() {
        List ret = new ArrayList<String>();
        for (String unit : this.name.split("/")) {
            String str = StringMachine.trimPunctuation(unit, "");
            str = str.toLowerCase();
            List strs = Transformers.getPinYin(str);
            Iterator<String> iter = strs.iterator();
            while (iter.hasNext()) {
                String item = iter.next();
                if (!item.replaceAll(" ", "").equals(str)) {
                    ret.add(item);
                }
            }
        }

        return (String[]) ret.toArray(new String[ret.size()]);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id_) {
        this.id = id_;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name_) {
        this.name = name_;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type_) {
        this.type = type_;
    }
}
