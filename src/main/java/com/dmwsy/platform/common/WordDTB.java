package com.dmwsy.platform.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordDTB implements Serializable {

    protected Map words = new HashMap<String, Integer>();
    protected double sum = 0;

    public WordDTB() {
    }

    public double getSum() {
        return this.sum;
    }

    public void addItem(String word) {
        if (this.words.containsKey(word)) {
            this.words.put(word, (Integer) this.words.get(word) + 1);
            this.sum += 1;
        } else {
            this.words.put(word, 1);
            this.sum += 1;
        }
    }

    public void addItem(List word) {
        for (int i = 0; i < word.size(); ++i) {
            addItem((String) word.get(i));
        }
    }

    public void addItem(String word, int num) {
        if (this.words.containsKey(word)) {
            this.words.put(word, (Integer) this.words.get(word) + num);
            this.sum += num;
        } else {
            this.words.put(word, num);
            this.sum += num;
        }
    }

    public Set getWords() {
        return this.words.keySet();
    }

    public double proportion(String word) {
        double proportion = 0.0;
        if (words.containsKey(word)) {
            proportion = (Integer) words.get(word);
        }

        return proportion / this.sum;
    }

    public String toString() {
        String ret = "";
        for (Iterator<String> iter = this.words.keySet().iterator(); iter.hasNext();) {
            String key = iter.next();
            double p = (Integer) this.words.get(key);
            ret += String.format("|%s: %f|", key, p / sum);
        }

        return ret;
    }
}
