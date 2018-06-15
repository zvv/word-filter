package wordfilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.dmwsy.platform.common.ACAutomaton;
import com.dmwsy.platform.common.ACAutomaton.Match;
import com.dmwsy.platform.common.SensitiveInfo;
import com.google.common.base.Joiner;

public class Test {

    public static Logger LOG = Logger.getLogger(Test.class);

    public static void main(String[] args) {
        String word = "是阿斯蒂芬苍老师毛泽东";
        // word = ChineseToEnglish.getPingYin(word);
        List<ACAutomaton.Match> list = StaticValue.getGoldenEyes().findSensitive(word);
        // List<ACAutomaton.Match> list = WordFilterUtil.ge.findSensitive(word);
        List<String> forbidWordList = new ArrayList<String>();
        JSONObject json = new JSONObject();
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
                LOG.info(Joiner.on("|").join(word, w));
                System.out.println(Joiner.on("|").join(word, w));
            }
        }
        System.out.println(json);

    }
}
