package com.dmwsy.platform.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

public class StringMachine {

    public StringMachine() {
    }

    // 分词
    public static List splitWords(String word) {
        List list = NLPTokenizer.segment(word);
        List ret = new ArrayList<String>();
        for (Iterator<Term> iter = list.iterator(); iter.hasNext();) {
            String item = iter.next().word;
            if (!StringMachine.matchPunctuation(item) && !StringMachine.matchSeparator(item)) {
                ret.add(item);
            }
        }

        return ret;
    }

    // 标点符号
    public static String trimPunctuation(String text, String str) {
        text = text.replaceAll("\\pP", str);
        return text;
    }

    public static boolean matchPunctuation(String text) {
        return text.matches("[\\pP]+");
    }

    // 分隔符
    public static String trimSeparator(String text, String str) {
        text = text.replaceAll("\\pZ", str);
        return text;
    }

    public static boolean matchSeparator(String text) {
        return text.matches("[\\pZ]+");
    }

    // 控制字符
    public static String trimControl(String text, String str) {
        text = text.replaceAll("\\pC", str);
        return text;
    }

    public static boolean matchControl(String text) {
        return text.matches("[\\pC]+");
    }

    // 修饰、数学、货币符号
    public static String trimMarks(String text, String str) {
        text = text.replaceAll("\\pS", str);
        return text;
    }

    public static boolean matchMarks(String text) {
        return text.matches("[\\pS]+");
    }

    // 字母
    public static String trimLatins(String text, String str) {
        text = text.replaceAll("\\pL", str);
        return text;
    }

    public static boolean matchLatins(String text) {
        return text.matches("[\\pL]+");
    }

    // 数字
    public static String trimFigures(String text, String str) {
        text = text.replaceAll("\\pN", str);
        return text;
    }

    public static boolean matchFigures(String text) {
        return text.matches("[\\pN]+");
    }

    // 去除头尾的空格，并合并中间多余的空格
    public static String mergeBlank(String text) {
        char[] array = text.toCharArray();
        boolean flag = false;
        int pos = 0;
        int i = 0;
        while (i < array.length && array[i] == ' ') {
            i++;
        }
        int end = array.length - 1;
        while (end >= 0 && array[end] == ' ') {
            end--;
        }
        for (; i <= end; ++i) {
            if (flag) {
                if (array[i] != ' ') {
                    array[pos] = array[i];
                    pos++;
                    flag = false;
                }
            } else {
                array[pos] = array[i];
                pos++;
                if (array[i] == ' ') {
                    flag = true;
                }
            }
        }
        if (pos == 0) {
            return null;
        }
        return (new String()).copyValueOf(array, 0, pos);
    }

    // 插入空格
    public static String insertBlank(String text) {
        int length = text.length();
        if (length <= 1) {
            return text;
        }
        char[] array = new char[length * 2 - 1];
        for (int i = 0; i < length - 1; ++i) {
            array[2 * i] = text.charAt(i);
            array[2 * i + 1] = ' ';
        }
        array[length * 2 - 2] = text.charAt(length - 1);
        return (new String()).copyValueOf(array, 0, length * 2 - 1);
    }
}
