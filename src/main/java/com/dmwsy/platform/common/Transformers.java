package com.dmwsy.platform.common;

import java.util.ArrayList;
import java.util.List;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.py.Pinyin;

public class Transformers {

    public Transformers() {
    }

    /**
     * 返回字符串的全拼和头
     * 
     * @param text
     *            输入的字符串
     * @return 包含全拼和头字符串的ArrayList
     */
    static public List getPinYin(String text) {
        if (text.length() == 0) {
            return new ArrayList<String>();
        }

        List list = new ArrayList<List>();
        StringBuffer quanpin = new StringBuffer("");
        StringBuffer head = new StringBuffer("");

        List<Pinyin> pinyinList = HanLP.convertToPinyinList(text);
        int index = 0;
        for (Pinyin pinyin : pinyinList) {
            String quanpin_ = pinyin.getPinyinWithoutTone();
            String head_ = pinyin.getHead().name();
            if (quanpin_.equals("none")) {
                quanpin.append(text.substring(index, index + 1));
            } else {
                quanpin.append(quanpin_);
            }
            quanpin.append(" ");
            if (head_.equals("none")) {
                head.append(text.substring(index, index + 1));
            } else {
                head.append(head_.substring(0, 1));
            }
            head.append(" ");
            index += 1;
        }
        quanpin.deleteCharAt(quanpin.length() - 1);
        head.deleteCharAt(head.length() - 1);

        list.add(quanpin.toString());
        list.add(head.toString());

        return list;
    }

    static public List getPinYinM(String text) {
        if (text.length() == 0) {
            return new ArrayList<String>();
        }

        List list = new ArrayList<List>();
        StringBuffer quanpin = new StringBuffer("");
        StringBuffer head = new StringBuffer("");

        List<Pinyin> pinyinList = HanLP.convertToPinyinList(text);
        int index = 0;
        for (Pinyin pinyin : pinyinList) {
            String quanpin_ = pinyin.getPinyinWithoutTone();
            String head_ = pinyin.getHead().name();
            if (quanpin_.equals("none")) {
                quanpin.append(text.substring(index, index + 1));
            } else {
                quanpin.append(quanpin_);
            }
            quanpin.append(" ");
            if (head_.equals("none")) {
                head.append(text.substring(index, index + 1));
            } else {
                head.append(head_.substring(0, 1));
            }
            head.append(" ");
            index += 1;
        }
        quanpin.deleteCharAt(quanpin.length() - 1);
        head.deleteCharAt(head.length() - 1);

        list.add(quanpin.toString());
        list.add(head.toString());

        return list;
    }
}
