package com.dmwsy.platform.common;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class StringUtils {
    private static SecureRandom random = new SecureRandom();

    public static int string2int(String str, int defaultValue) {
        if (str == null || str.length() == 0) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static List<String> int2list(List<Integer> intList) {
        List<String> list = new ArrayList<String>();
        for (Integer value : intList) {
            String v = String.valueOf(value);
            if (v != null) {
                list.add(v);
            }
        }
        return list;
    }

    public static List<String> long2list(List<Long> intList) {
        List<String> list = new ArrayList<String>();
        for (Long value : intList) {
            String v = String.valueOf(value);
            if (v != null) {
                list.add(v);
            }
        }
        return list;
    }

    public static String randomStr(int len) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static long string2long(String str, long defaultValue) {
        if (str == null || str.length() == 0) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long object2long(Object obj, long def) {
        if (obj == null) {
            return def;
        }
        try {
            if (obj instanceof Integer) {
                return (long) (Integer) obj;
            } else if (obj instanceof Long) {
                return ((Long) obj).longValue();
            } else if (obj instanceof String) {
                return Long.parseLong((String) obj);
            } else {
                return def;
            }
        } catch (Exception e) {
            return def;
        }
    }

    public static Integer convertStr2Int(String str) {
        Integer ints = null;
        if (str != null) {
            try {
                ints = new Integer(str);
            } catch (NumberFormatException e) {
            }
        }
        return ints;
    }

    public static Float convertStr2Float(String str) {
        Float f = null;
        if (str != null) {
            try {
                f = Float.valueOf(str);
            } catch (NumberFormatException e) {

            }
        }
        return f;
    }

    public static Double convertStr2Double(String str) {
        Double d = null;
        if (str != null) {
            try {
                d = Double.valueOf(str);
            } catch (NumberFormatException e) {
            }
        }
        return d;
    }

    public static Byte convertStr2Byte(String str) {
        Byte b = null;
        if (str != null) {
            try {
                b = Byte.valueOf(str);
            } catch (NumberFormatException e) {
            }
        }
        return b;
    }

    public static Short convertStr2Short(String str) {
        Short s = null;
        if (str != null) {
            try {
                s = Short.valueOf(str);
            } catch (NumberFormatException e) {
            }
        }
        return s;
    }

    public static Date convertStr2Date(String dateString) {
        if (dateString == null) {
            return null;
        }
        Date dete = null;
        try {
            SimpleDateFormat createTimeFormat = new SimpleDateFormat("yyyyMMdd");
            dete = createTimeFormat.parse(dateString);
        } catch (ParseException e) {
        }
        return dete;
    }

    public static String convertDate2Str(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat createTimeFormat = new SimpleDateFormat("yyyyMMdd");
        return createTimeFormat.format(date);
    }

    private static final String pattern = "[\\[|\\]| ]";

    // example: [1, 2, 3, 4] => 1,2,3,4
    public static String list2str(String str) {
        // String tmp = str.replaceAll(" ", "");
        // return tmp.substring(1, tmp.length() - 1);
        return str.replaceAll(pattern, "");
    }

    public static String trimAll(String str) {
        return str.replaceAll(" ", "");
    }

    // 判断是不是中文字符
    // GENERAL_PUNCTUATION 判断中文的“号
    // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
    // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean isChineseFast(char c) {
        if (Character.OTHER_LETTER == Character.getType(c)) {
            return true;
        }
        return false;
    }

    public static List<CHsub> segChinese(String str) {
        char[] ch = str.toCharArray();
        List<CHsub> chsubs = new ArrayList<CHsub>();
        CHsub sub = null;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChineseFast(c)) {
                if (sub == null) {
                    sub = new CHsub();
                    sub.setOffset(i);
                }
                sub.setOffend(i);
                sub.append(c);
            } else {
                if (sub != null) {
                    chsubs.add(sub);
                    sub = null;
                }
            }
        }
        if (sub != null) {
            chsubs.add(sub);
        }
        return chsubs;
    }

    public static class CHsub {
        private int offset;
        private int offend;
        private StringBuilder chars = new StringBuilder();

        public void append(char c) {
            chars.append(c);
        }

        public String getCH() {
            return chars.toString();
        }

        public String toString() {
            return getCH() + "[" + offset + "," + offend + "]";
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getOffend() {
            return offend;
        }

        public void setOffend(int offend) {
            this.offend = offend;
        }

    }

    public static boolean getBoolean(String str) {
        return (str == null || str.trim().length() == 0 || str.equals("null")) ? false : true;
    }

    public static boolean isLetterOrDigit(String str) {
        return str.length() == str.getBytes().length;
    }

    public static String collection2Str(Collection<String> c, String mark) {
        StringBuilder line = new StringBuilder();
        for (String str : c) {
            if (line.length() > 0) {
                line.append(mark);
            }
            line.append(str);
        }
        return line.toString();
    }

    public static String arr2Str(String[] arr, String mark) {
        StringBuilder builder = new StringBuilder();
        for (String str : arr) {
            if (builder.length() > 0) {
                builder.append(mark);
            }
            builder.append(str);
        }
        return builder.toString();
    }

    public static List<Integer> str2int(String[] strs) {
        if (strs == null || strs.length == 0) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();
        for (String str : strs) {
            Integer integer = convertStr2Int(str);
            if (integer != null) {
                list.add(integer);
            }
        }
        return list;
    }

    public static List<Long> str2long(String[] strs) {
        if (strs == null || strs.length == 0) {
            return null;
        }
        List<Long> list = new ArrayList<Long>();
        for (String str : strs) {
            Long integer = (long) convertStr2Int(str);
            if (integer != null) {
                list.add(integer);
            }
        }
        return list;
    }

    public static int[] string2int(String[] strs) {
        int[] ints = new int[strs.length];
        for (int i = 0; i < strs.length; i++) {
            ints[i] = string2int(strs[i], 0);
        }
        return ints;
    }

    public static List<Integer> str2list(String... strs) {
        List<Integer> list = new ArrayList<Integer>();
        for (String str : strs) {
            Integer integer = convertStr2Int(str);
            if (integer != null) {
                list.add(integer);
            }
        }
        return list;
    }

    public static boolean isNumericFast(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static Pattern numericPattern = Pattern.compile("[0-9]*");

    public static boolean isNumericPattern(String str) {
        return numericPattern.matcher(str).matches();
    }

    public static String[] filterString(String[] originals, String[] removes) {
        if (originals == null || originals.length == 0 || removes == null || removes.length == 0) {
            return null;
        }

        List<String> list = new ArrayList<String>();
        lable: for (int i = 0; i < originals.length; i++) {
            for (int j = 0; j < removes.length; j++) {
                if (originals[i].equals(removes[j])) {
                    continue lable;
                }
            }
            list.add(originals[i]);
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * @desc 检查标点符号,但排除了"<"60 和 ">"62
     */
    public static boolean isSymbol(int codePoint) {
        if (codePoint == 60 || codePoint == 62) {
            return false;
        }
        return (codePoint >= 33 && codePoint <= 47) || (codePoint >= 58 && codePoint <= 64)
                || (codePoint >= 91 && codePoint <= 96) || (codePoint >= 123 && codePoint <= 126);
    }

    public static String mergeList(List<Integer> list, String mark) {
        if (list == null || list.size() <= 0) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null || "".equals(list.get(i))) {
                continue;
            }
            if (sb.length() == 0) {
                sb.append(list.get(i));
            } else {
                sb.append(mark + list.get(i));
            }
        }
        return sb.toString();
    }

    public static String int2str(Integer[] ids) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ids.length; i++) {
            if (builder.length() > 0) {
                builder.append(",");
            }
            builder.append(ids[i]);
        }
        return builder.toString();
    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "000".equals(str) || "000000".equals(str)) {
            return true;
        }
        return false;
    }

    public static String getLen2HourDivisionNum(String hour, int num) {
        String rtn = "0";
        if (hour == null || "".equals(hour) || "00".equals(hour)) {
            rtn = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) / num + "";
        } else {
            rtn = Integer.parseInt(hour) / num + "";
        }
        // if(rtn.length() == 1)
        // {
        // rtn = "0" + rtn;
        // }
        return rtn;
    }

    public static String supplyChar(String str, int len, char c) {
        String ret = str;
        if (ret != null && !"".equals(ret)) {
            for (int i = 0; i < len - str.length(); i++) {
                ret = ret + c;
            }
        }
        return ret;
    }

    public static String getRangeDay(String modifytime) {
        String rangDay = "6月以上";
        if (modifytime != null && modifytime.length() > 0) {
            modifytime = modifytime.substring(0, 8);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
            Date beginDate;
            Long day = 0L;
            try {
                beginDate = df.parse(modifytime);
                Date endDate = new Date();
                day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (day == 0) {
                rangDay = "1天内";
            } else if (day < 3) {
                rangDay = "3天内";
            } else if (day < 8) {
                rangDay = "1周内";
            } else if (day < 15) {
                rangDay = "2周内";
            } else if (day < 32) {
                rangDay = "1月内";
            } else if (day < 93) {
                rangDay = "3月内";
            } else if (day < 186) {
                rangDay = "6月内";
            } else {
                rangDay = "6月以上";
            }
        }
        return rangDay;
    }

    static Map<Integer, Integer> idMap = new HashMap<Integer, Integer>() {
        {
            put(3790616, 1);
            put(3655685, 1);
            put(3970210, 1);
            put(3732568, 1);
            put(3548533, 1);
            put(3591148, 1);
            put(3882795, 1);
            put(2945320, 1);
            put(3970440, 1);
            put(3766011, 1);
            put(3581646, 1);
            put(3608256, 1);
            put(2947562, 1);
            put(3430184, 1);
            put(3068898, 1);
            put(3236058, 1);
            put(3970653, 1);
            put(3695906, 1);
            put(3815645, 1);
            put(3530441, 1);
            put(3730622, 1);
            put(3923611, 1);
            put(3876191, 1);
            put(3939911, 1);
            put(3196840, 1);
            put(3239853, 1);
            put(3865103, 1);
            put(3850246, 1);
            put(3886532, 1);
            put(3532803, 1);
            put(3970824, 1);
            put(2398165, 1);
            put(3269820, 1);
            put(3055489, 1);
            put(3816832, 1);
            put(3342762, 1);
            put(3938346, 1);
            put(3906537, 1);
        }
    };

    public static boolean isFeedbackJobId(Integer jobId) {
        // 急聘中得反馈率
        if (idMap.get(jobId) != null) {
            return true;
        }
        return false;
    }

    /**
     * 获取建索引的group 职位
     * 
     * @描述：XXXXXXX
     * @param group
     * @return
     * @return String
     * @exception
     * @createTime：2016年1月29日
     * @author: zhangwei
     */
    public static String getGroup(String group) {
        // 急聘中得反馈率
        if ("group6".equals(group)) {
            return group;
        } else if ("group15".equals(group)) {
            return group;
        } else {
            // 研发和QA环境
            return "group10";
        }
    }

    /**
     * 获取建索引的group ecomp和userh
     * 
     * @描述：XXXXXXX
     * @param group
     * @return
     * @return String
     * @exception
     * @createTime：2016年1月29日
     * @author: zhangwei
     */
    public static String getCompAndUserHGroup(String group) {
        if ("group10".equals(group)) {
            return "group10";
        } else {
            return "group15";
        }
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.getRangeDay("20150928"));
        Date endDate = new Date();
        System.out.println(endDate.toString());
    }
}
