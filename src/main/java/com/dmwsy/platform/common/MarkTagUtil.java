package com.dmwsy.platform.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkTagUtil {

    /**
     * @desc 对词进行加标签提取，标签相邻近的优先提取
     * @param String
     *            source 原文
     * @param String
     *            word 关键词
     * @param String
     *            flag0 前标签
     * @param String
     *            flag1 后标签
     * @param int limit 限定返回字符串长度
     */
    public static String still(String source, String word, String flag0, String flag1, int limit, boolean cutHead) {
        word = word.trim().toLowerCase();
        word = word.replace(")", "）");
        word = word.replace("(", "（");
        word = word.replace("*", "");
        source = source.toLowerCase();
        StringBuilder replace = new StringBuilder();
        replace.append(flag0).append(word).append(flag1);

        int wordLength = word.length();
        int sourceLength = source.length();
        String pattern = word;
        if (StringUtils.isLetterOrDigit(word)) {
            pattern = word.replaceAll("\\+", "\\\\+");
        }
        Pattern p = Pattern.compile(pattern);

        if (sourceLength <= limit) {
            Matcher m = p.matcher(source);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                m.appendReplacement(sb, replace.toString());
            }
            m.appendTail(sb);
            return sb.toString();
        } else if (!cutHead) {
            source = source.substring(0, limit) + "...";
            Matcher m = p.matcher(source);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                m.appendReplacement(sb, replace.toString());
            }
            m.appendTail(sb);
            return sb.toString();
        } else {
            Matcher m = p.matcher(source);
            ArrayList<Segment> founds = new ArrayList<Segment>();
            int i = 0;
            while (m.find()) {
                Segment segment = new Segment();
                segment.start = m.start();
                segment.end = m.end();
                segment.length = wordLength;
                segment.distance = (i != 0) ? segment.start - founds.get(i - 1).end : -1;
                founds.add(segment);
                i++;
            }
            // 没找到
            if (i == 0) {
                if (sourceLength > limit) {
                    return source.substring(0, limit) + "...";
                } else {
                    return source + "...";
                }
            }

            List<List<Segment>> result = new ArrayList<List<Segment>>();
            for (int k = 0; k < i; k++) {
                List<Segment> list = new ArrayList<Segment>();
                Segment begin = founds.get(k);
                list.add(begin);
                int length = wordLength;
                for (int j = k + 1; j < i; j++) {
                    Segment next = founds.get(j);
                    if (next.distance + length + wordLength >= limit) {
                        break;
                    } else {
                        length += wordLength + next.distance;
                        list.add(next);
                    }
                }
                result.add(list);
            }
            // 对组合最多的情况倒序排列
            Collections.sort(result, new SizeComparator());

            int startPos, endPos;
            List<Segment> pit = result.get(0);
            startPos = pit.get(0).start;
            endPos = pit.get(0).end;
            if (pit.size() > 1) {
                endPos = pit.get(pit.size() - 1).end;
            }
            int remain = limit - (endPos - startPos);
            int use = 0;
            if (remain > 4) {
                // startPos = (startPos > remain/2) ? startPos - remain / 2: 0;
                if (startPos > remain / 2) {
                    startPos = startPos - remain / 2;
                    use += remain / 2;
                } else {
                    use += startPos;
                    startPos = 0;
                }
                // endPos = ((sourceLength - endPos) < 2) ? sourceLength :
                // endPos + remain / 2;
                if ((sourceLength - endPos) < remain / 2) {
                    use += sourceLength - endPos;
                    endPos = sourceLength;
                } else {
                    endPos = endPos + remain / 2;
                    use += remain / 2;
                }
                if (remain != use) {
                    if (startPos == 0 && endPos != sourceLength) {
                        endPos += remain - use;
                    } else if (endPos == sourceLength && startPos != 0) {
                        startPos -= remain - use;
                    }
                }
            }
            if (startPos < 0) {
                startPos = 0;
            }
            if (endPos > sourceLength) {
                endPos = sourceLength;
            }
            StringBuilder sb = new StringBuilder(source.substring(startPos, endPos).replaceAll(pattern,
                    replace.toString()));
            while (StringUtils.isSymbol(sb.codePointAt(0))) {
                sb.deleteCharAt(0);
            }
            while (StringUtils.isSymbol(sb.codePointAt(sb.length() - 1))) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("...");
            return sb.toString();
        }
    }

    private static class Segment {
        public String word;
        public int start;
        public int end;
        public int length;
        public int distance;

        @Override
        public String toString() {
            return "Segment [word=" + word + ", start=" + start + ", end=" + end + ", length=" + length + ", distance="
                    + distance + "]";
        }
    }

    private static class SizeComparator implements Comparator<List<?>> {

        @Override
        public int compare(List<?> arg0, List<?> arg1) {
            if (arg0.size() == arg1.size()) {
                return 0;
            }
            return arg0.size() < arg1.size() ? 1 : -1;
        }

    }

    public static String still(String source, String[] words, String flag0, String flag1, int limit, boolean cutHead) {
        Map<String, String> replaceMap = new HashMap<String, String>();
        StringBuilder pattern = new StringBuilder();
        for (String word : words) {
            word = word.trim().toLowerCase();
            word = word.replace(")", "）");
            word = word.replace("(", "（");
            word = word.replace("*", "");
            StringBuilder replace = new StringBuilder();
            replace.append(flag0).append(word).append(flag1);
            replaceMap.put(word, replace.toString());
            String p = (StringUtils.isLetterOrDigit(word)) ? word.replaceAll("\\+", "\\\\+") : word;
            p = p.replaceAll("\\.", "\\\\.");
            if (pattern.length() != 0) {
                pattern.append("|");
            }
            pattern.append(p);
        }
        return still(source, pattern.toString(), replaceMap, flag0, flag1, limit, cutHead);
    }

    public static String still(String source, Set<String> words, String flag0, String flag1, int limit, boolean cutHead) {
        Map<String, String> replaceMap = new HashMap<String, String>();
        StringBuilder pattern = new StringBuilder();
        for (String word : words) {
            word = word.trim().toLowerCase();
            word = word.replace(")", "）");
            word = word.replace("(", "（");
            word = word.replace("*", "");
            StringBuilder replace = new StringBuilder();
            replace.append(flag0).append(word).append(flag1);
            replaceMap.put(word, replace.toString());
            String p = (StringUtils.isLetterOrDigit(word)) ? word.replaceAll("\\+", "\\\\+") : word;
            p = p.replaceAll("\\.", "\\\\.");
            // String p = Pattern.quote(word);
            if (pattern.length() != 0) {
                pattern.append("|");
            }
            pattern.append(p);
        }
        return still(source, pattern.toString(), replaceMap, flag0, flag1, limit, cutHead);
    }

    public static String still(String source, String pattern, Map<String, String> replaceMap, String flag0,
            String flag1, int limit, boolean cutHead) {
        source = source.toLowerCase();
        int sourceLength = source.length();
        Pattern p = Pattern.compile(pattern.toString());// 转移字符

        if (sourceLength <= limit) {
            Matcher m = p.matcher(source);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                String son = source.substring(m.start(), m.end());
                if (replaceMap.get(son) != null) {
                    m.appendReplacement(sb, replaceMap.get(son));
                }
            }
            m.appendTail(sb);
            return sb.toString();
        } else if (!cutHead) {
            source = source.substring(0, limit) + "...";
            Matcher m = p.matcher(source);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                String son = source.substring(m.start(), m.end());
                if (replaceMap.get(son) != null) {
                    m.appendReplacement(sb, replaceMap.get(son));
                }
            }
            m.appendTail(sb);
            return sb.toString();
        } else {
            Matcher m = p.matcher(source);
            ArrayList<Segment> founds = new ArrayList<Segment>();
            int i = 0;
            while (m.find()) {
                String son = source.substring(m.start(), m.end());
                Segment segment = new Segment();
                segment.word = son;
                segment.start = m.start();
                segment.end = m.end();
                segment.length = son.length();
                segment.distance = (i != 0) ? segment.start - founds.get(i - 1).end : -1;
                founds.add(segment);
                i++;
            }
            // 没找到
            if (i == 0) {
                if (sourceLength > limit) {
                    return source.substring(0, limit) + "...";
                } else {
                    return source + "...";
                }
            }

            List<List<Segment>> result = new ArrayList<List<Segment>>();
            for (int k = 0; k < i; k++) {
                List<Segment> list = new ArrayList<Segment>();
                Segment begin = founds.get(k);
                list.add(begin);
                int length = begin.length;
                for (int j = k + 1; j < i; j++) {
                    Segment next = founds.get(j);
                    if (next.distance + length + next.length >= limit) {
                        break;
                    } else {
                        length += next.length + next.distance;
                        list.add(next);
                    }
                }
                result.add(list);
            }
            // 对组合最多的情况倒序排列
            Collections.sort(result, new SizeComparator());

            int startPos, endPos;
            List<Segment> pit = result.get(0);
            startPos = pit.get(0).start;
            endPos = pit.get(0).end;
            if (pit.size() > 1) {
                endPos = pit.get(pit.size() - 1).end;
            }
            int remain = limit - (endPos - startPos);
            int use = 0;
            if (remain > 4) {
                if (startPos > remain / 2) {
                    startPos = startPos - remain / 2;
                    use += remain / 2;
                } else {
                    use += startPos;
                    startPos = 0;
                }
                if ((sourceLength - endPos) < remain / 2) {
                    use += sourceLength - endPos;
                    endPos = sourceLength;
                } else {
                    endPos = endPos + remain / 2;
                    use += remain / 2;
                }
                if (remain != use) {
                    if (startPos == 0 && endPos != sourceLength) {
                        endPos += remain - use;
                    } else if (endPos == sourceLength && startPos != 0) {
                        startPos -= remain - use;
                    }
                }
            }
            if (startPos < 0) {
                startPos = 0;
            }
            if (endPos > sourceLength) {
                endPos = sourceLength;
            }
            // 替换
            String select = source.substring(startPos, endPos);
            m = m.reset(select);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                String son = select.substring(m.start(), m.end());
                if (replaceMap.get(son) != null) {
                    m.appendReplacement(sb, replaceMap.get(son));
                }
            }
            m.appendTail(sb);

            while (StringUtils.isSymbol(sb.codePointAt(0))) {
                sb.deleteCharAt(0);
            }
            while (StringUtils.isSymbol(sb.codePointAt(sb.length() - 1))) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("...");
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        String ttt = "【环球网军事12月22日报道】据路透社21日报道，泰国军方发言人孔切普当天表示，正在与中国进行国有关两国合作制造军工设施的对话。孔切普称，泰国国防部长巴维上周前中国往中国北京，与中国国防部长常万全会面时提到，“泰方有意修理并维护目前泰国军械库中的中国产设备”，希望“中国能提供制造小型武器和其他安保相关器具的技术，如无人机”。据悉，泰国也与俄罗斯进行过类似对话。环球网军事12月22日报道】据路透社21日报道，泰国军方发言人孔切普当天表示，正在与中国进行国有关两国合作制造军工设施的对话。孔切普称，泰国国防部长巴维上周前中国往中国北京，与中国国防部长常万全会面时提到，“泰方有意修理并维护目前泰国军械库中的中国产设备”，希望“中国能提供制造小型武器和其他安保相关器具的技术，如无人机”。据悉，泰国也与俄罗斯进行过类似对话。环球网军事12月22日报道】据路透社21日报道，泰国军方发言人孔切普当天表示，正在与中国进行国有关两国合作制造军工设施的对话。孔切普称，泰国国防部长巴维上周前中国往中国北京，与中国国防部长常万全会面时提到，“泰方有意修理并维护目前泰国军械库中的中国产设备”，希望“中国能提供制造小型武器和其他安保相关器具的技术，如无人机”。据悉，泰国也与俄罗斯进行过类似对话。";
        System.out.println(ttt.length());
        String words = "中国";
        System.out.println(MarkTagUtil.still(ttt, words, "<<<", ">>>", 30, true));
        Long aa = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            MarkTagUtil.still(ttt, words, "<<<", ">>>", 30, true);
        }

        System.out.println(System.currentTimeMillis() - aa);
    }
}
