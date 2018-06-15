package com.dmwsy.platform.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 抽象栏目载体，存储id、类型和名称信息。
 * 
 * @author zhou.ming@kuyun.com
 * @version 0.0
 */
public class EPGInfo implements Info, Serializable {
    protected long id;
    protected String catype = "";
    protected String caname = "";
    protected List tvlist = new ArrayList<Long>();

    /**
     * 默认构造函数
     */
    public EPGInfo() {
    }

    /**
     * 构造函数
     * 
     * @param id_
     *            抽象栏目id
     * @param caname_
     *            抽象栏目名称
     * @param catype_
     *            抽象栏目类型
     */
    public EPGInfo(long id_, String caname_, String catype_) {
        this.id = id_;
        this.caname = caname_;
        this.catype = catype_;
    }

    public void addTVId(long id) {
        this.tvlist.add(id);
    }

    public List getTVId() {
        return this.tvlist;
    }

    public void setTVId(ArrayList<Long> list) {
        this.tvlist = (ArrayList<Long>) list.clone();
    }

    public String toString() {
        String ret = String.format("%d | %s | %s", this.id, this.caname, this.catype);
        for (int i = 0; i < tvlist.size(); ++i) {
            ret += String.format(" @%d", tvlist.get(i));
        }

        return ret;
    }

    public String[] getString() {
        String str_ca = StringMachine.trimPunctuation(this.caname, "");
        str_ca = StringMachine.trimSeparator(str_ca, "");
        str_ca = str_ca.toLowerCase();
        str_ca = StringMachine.insertBlank(str_ca);

        return new String[] { str_ca };
    }

    public String[] getPinYin() {
        String str = StringMachine.trimPunctuation(this.caname, "");
        str = StringMachine.trimSeparator(str, "");
        str = str.toLowerCase();
        List strs = Transformers.getPinYin(str);
        List ret = new ArrayList<String>();
        Iterator<String> iter = strs.iterator();
        while (iter.hasNext()) {
            String item = iter.next();
            if (!item.replaceAll(" ", "").equals(str)) {
                ret.add(item);
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
