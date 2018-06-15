package com.dmwsy.platform.common;

import java.util.Date;

public class ColumnInfo {
    public long id = -1;
    public String cd_name = "";
    public String cd_type = "";
    public Date start_time = null;
    public Date end_time = null;
    public int tv_id = -1;
    public String sys_tag = "";
    public String cd_desc = "";

    public String toString() {
        String ret = String
                .format("[id:%d, cd_name:\"%s\", cd_type:\"%s\", start_time:\"%s\",end_time:\"%s\", tv_id:%d, sys_tag:\"%s\", cd_desc:\"%s\"]",
                        this.id, this.cd_name, this.cd_type, this.start_time.toString(), this.end_time.toString(),
                        this.tv_id, this.sys_tag, this.cd_desc);

        return ret;
    }
}
