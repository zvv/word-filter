package com.dmwsy.platform.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SQLMachine {

    private static final Logger LOGGER = Logger.getLogger("SQLMachine");

    protected String SQLAddr;
    protected String SQLPort;
    protected String SQLBase;
    protected Connection conn;
    protected Statement st;

    protected Map tv = new HashMap<Integer, String>();

    public static final String TABLE_NAME_COLUMN = "kytv_resource_column_tvstation";
    public static final String FIELD_NAME_CAID = "ca_id";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_CANAME = "column_name";
    public static final String FIELD_NAME_CATYPE = "column_type";
    public static final String FIELD_NAME_TYPE = "name";
    public static final String FIELD_NAME_CDNAME = "cd_name";
    public static final String FIELD_NAME_STIME = "start_time";
    public static final String FIELD_NAME_ETIME = "end_time";
    public static final String FIELD_NAME_DESC = "cd_desc";
    public static final String FIELD_NAME_TAG = "sys_tag";
    public static final String FIELD_NAME_TVID = "tv_id";
    public static final String FIELD_NAME_CDID = "cd_id";

    public SQLMachine(String tvpath) {
        this.SQLAddr = "172.21.62.114";
        this.SQLPort = "3308";
        this.SQLBase = "kytv";
        String url = String.format("jdbc:mysql://%s:%s/%s", this.SQLAddr, this.SQLPort, this.SQLBase);

        try {
            FileReader fr = new FileReader(tvpath);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] infos = line.split("\t");
                tv.put(Integer.parseInt(infos[0]), infos[1]);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, "ky", "m7MSbiJU52");
            this.st = this.conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SQLMachine() {
        this.SQLAddr = "172.21.62.114";
        this.SQLPort = "3308";
        this.SQLBase = "kytv";
        String url = String.format("jdbc:mysql://%s:%s/%s", this.SQLAddr, this.SQLPort, this.SQLBase);

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, "ky", "m7MSbiJU52");
            this.st = this.conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String query) {
        ResultSet rs = null;

        if (query == null) {
            return rs;
        }

        try {
            rs = this.st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public List getEPGData() {
        String query = String.format("select id,column_name,column_type from kytv_resource_column_abs where status=1");
        List list = new ArrayList<EPGInfo>();
        try {
            ResultSet rs = this.st.executeQuery(query);
            while (rs.next()) {
                BigDecimal id = rs.getBigDecimal(FIELD_NAME_ID);
                String caname = rs.getString(FIELD_NAME_CANAME);
                String catype = rs.getString(FIELD_NAME_CATYPE);

                if (id != null && caname != null && catype != null) {
                    EPGInfo info = new EPGInfo(id.longValue(), caname, catype);
                    list.add(info);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List getCaIdSpecial() {
        String query = "select id,column_name,column_type from kytv_resource_column_abs where column_type in (\",3,\",\",4,\")";
        List list = new ArrayList<EPG2Info>();
        try {
            ResultSet rs = this.st.executeQuery(query);
            while (rs.next()) {
                BigDecimal id = rs.getBigDecimal(FIELD_NAME_ID);
                String caname = rs.getString(FIELD_NAME_CANAME);
                String catype = rs.getString(FIELD_NAME_CATYPE);

                if (id != null && caname != null && catype != null) {
                    EPG2Info info = new EPG2Info(id.longValue(), caname, catype);
                    list.add(info);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List distinctCaId() {
        String query = "select distinct ca_id from kytv_resource_column_tvstation where status=1 and tv_id in ";
        String in = "";
        for (Iterator<Integer> iter = this.tv.keySet().iterator(); iter.hasNext();) {
            in += String.format("%d,", iter.next());
        }
        in = in.substring(0, in.length() - 1);
        query = String.format("%s(%s)", query, in);
        System.out.println(query);

        List ret = new ArrayList<Long>();
        try {
            ResultSet rs = this.st.executeQuery(query);
            while (rs.next()) {
                BigDecimal id = rs.getBigDecimal(1);
                if (id != null) {
                    ret.add(id.longValue());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public Map distinctCaIdAndTVId() {
        String query = "select distinct ca_id,tv_id from kytv_resource_column_tvstation where status=1";

        Map ret = new HashMap<Long, ArrayList<Long>>();
        try {
            ResultSet rs = this.st.executeQuery(query);
            while (rs.next()) {
                BigDecimal ca_id = rs.getBigDecimal(1);
                BigDecimal tv_id = rs.getBigDecimal(2);
                if (ca_id != null && tv_id != null) {
                    if (!ret.containsKey(ca_id.longValue())) {
                        ret.put(ca_id.longValue(), new ArrayList<Long>());
                    }
                    ((List) ret.get(ca_id.longValue())).add(tv_id.longValue());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public List distinctTVIdByCaId(Long ca_id) {
        String query = String.format(
                "select distinct tv_id from kytv_resource_column_tvstation where status=1 and ca_id=%d", ca_id);

        ArrayList<Long> ret = new ArrayList<Long>();
        try {
            ResultSet rs = this.st.executeQuery(query);
            while (rs.next()) {
                BigDecimal tv_id = rs.getBigDecimal(1);
                if (tv_id != null) {
                    ret.add(tv_id.longValue());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public ResultSet getCaById(Long ca_id) {
        String query = String.format("select * from kytv_resource_column_abs where id=%d and status=1", ca_id);
        ResultSet rs = null;
        try {
            rs = this.st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet findColumnByCaId(Long ca_id) {
        String query = String.format("select * from kytv_resource_column_tvstation where ca_id=%d", ca_id);
        ;
        ResultSet rs = null;
        try {
            rs = this.st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public String getTypeById(Long id) {
        String query = String.format("select * from kytv_resource_column_type where id=%d", id);
        String ret = null;
        try {
            ResultSet rs = this.st.executeQuery(query);
            if (rs.next()) {
                ret = rs.getString(FIELD_NAME_TYPE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public String getTypeByString(String ca_type) {
        if (ca_type == null || ca_type.equals("")) {
            return null;
        }

        ca_type = ca_type.substring(1, ca_type.length() - 1);
        String[] ids = ca_type.split(",");
        if (ids.length == 1) {
            return getTypeById(Long.parseLong(ids[0]));
        } else {
            return null;
        }
    }
}
