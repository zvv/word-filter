package com.dmwsy.platform.modules.wordfilter.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dmwsy.platform.common.ACAutomaton;
import com.dmwsy.platform.common.ACAutomaton.Match;
import com.dmwsy.platform.common.MarkTagUtil;
import com.dmwsy.platform.common.RequestUtil;
import com.dmwsy.platform.common.SensitiveInfo;
import com.dmwsy.platform.common.WordFilterUtil;
import com.google.common.base.Joiner;

@Controller
@RequestMapping("/filter")
public class WordfilterControcoller {

    private Logger logger = Logger.getLogger(WordfilterControcoller.class);

    @RequestMapping(value = "/forbidWord")
    public void filterForbidWord(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (request.getParameter("word") == null) {
            try {
                RequestUtil.responseJson(response, "{清输入参数}");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("=====1======" + request.getParameter("word"));
        //String word = new String(request.getParameter("word").getBytes("ISO-8859-1"), "UTF-8"); // joo为字符串
        String word = request.getParameter("word");
        // word = ChineseToEnglish.getPingYin(word);
        List<ACAutomaton.Match> list = WordFilterUtil.getGoldenEyes().findSensitive(word);
        // List<ACAutomaton.Match> list = WordFilterUtil.ge.findSensitive(word);
        List<String> forbidWordList = new ArrayList<String>();
        Map json = new HashMap<String, Object>();
        for (Iterator<ACAutomaton.Match> iter = list.iterator(); iter.hasNext();) {
            Match match = iter.next();
            SensitiveInfo info = (SensitiveInfo) match.info;
            forbidWordList.add(info.getTerm());
        }

        json.put("code", 0);
        json.put("check_status", forbidWordList.size() > 0 ? false : true);
        json.put("message", forbidWordList.size() > 0 ? "注意! 验证文本中含有敏感词!" : "ok! 验证文本无敏感词!");
        json.put("forbid_word_list", forbidWordList);
        if (forbidWordList != null && forbidWordList.size() > 0) {
            for (String w : forbidWordList) {
                System.out.println(Joiner.on("|").join(word, w));
            }
        }
        System.out.println(json);
        try {
            // 执行返回方法
            RequestUtil.responseJson(response, json.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/filterWeb")
    public String filterWeb(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        if (request.getParameter("word") == null) {
            return "";
        }
        String word = request.getParameter("word"); // joo为字符串
        // word = ChineseToEnglish.getPingYin(word);
        List<ACAutomaton.Match> list = WordFilterUtil.getGoldenEyes().findSensitive(word);
        // List<ACAutomaton.Match> list = WordFilterUtil.ge.findSensitive(word);
        List<String> forbidWordList = new ArrayList<String>();
        for (Iterator<ACAutomaton.Match> iter = list.iterator(); iter.hasNext();) {
            Match match = iter.next();
            SensitiveInfo info = (SensitiveInfo) match.info;
            forbidWordList.add(info.getTerm());
        }

        if (forbidWordList != null && forbidWordList.size() > 0) {
            for (String w : forbidWordList) {
                System.out.println(Joiner.on("|").join(word, w));
            }
        }
        String souceword = word;
        String result = "";
        for (String key : forbidWordList) {
            result = MarkTagUtil.still(word, key, "<font color=\"#FF0000\">", "</font>", 1000, true);
            word = result;
        }

        model.addAttribute("word", souceword);
        model.addAttribute("result", result);
        System.out.println(souceword + " ====  " + result);
        return "/index";
    }
}