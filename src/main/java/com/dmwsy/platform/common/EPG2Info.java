package com.dmwsy.platform.common;

import java.io.Serializable;

public class EPG2Info implements Info, Serializable {
    protected long id;
    protected String catype = "";
    protected String caname = "";

    public EPG2Info() {
    }

    public EPG2Info(long id_, String caname_, String catype_) {
        this.id = id_;
        this.caname = caname_;
        this.catype = catype_;
    }

    public String toString() {
        return String.format("%d | %s | %s", this.id, this.caname, this.catype);
    }

    public String[] getString() {
        String str_ca = StringMachine.trimSeparator(this.caname, "");
        str_ca = StringMachine.insertBlank(str_ca);

        return new String[] { str_ca };
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
