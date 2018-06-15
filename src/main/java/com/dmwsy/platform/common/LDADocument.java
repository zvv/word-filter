package com.dmwsy.platform.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LDADocument {

    public Map content = new HashMap<String, List>();
    public int nTopic;
    public List dtbTopic = new ArrayList<Double>();

    private LDADocument() {
    }

    public LDADocument(String line, int nTopic_) {
        this.nTopic = nTopic_;
        line = line.trim();
        String[] infos = line.split(" ");
        int length = infos.length;
        // timemillis as seed
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < length / 2; ++i) {
            int size = Integer.parseInt(infos[2 * i + 1]);
            List list = new ArrayList<Integer>();
            for (int k = 0; k < size; ++k) {
                list.add(random.nextInt(this.nTopic));
            }
            this.content.put(infos[2 * i], list);
        }
        countTopic();
    }

    public LDADocument(String line, int nTopic_, Set vocab) {
        this.nTopic = nTopic_;
        line = line.trim();
        String[] infos = line.split(" ");
        int length = infos.length;
        // timemillis as seed
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < length / 2; ++i) {
            if (vocab.contains(infos[2 * i])) {
                int size = Integer.parseInt(infos[2 * i + 1]);
                List list = new ArrayList<Integer>();
                for (int k = 0; k < size; ++k) {
                    list.add(random.nextInt(this.nTopic));
                }
                this.content.put(infos[2 * i], list);
            }
        }
        countTopic();
    }

    public void countTopic() {
        for (int i = 0; i < this.nTopic; ++i) {
            this.dtbTopic.add(0.0);
        }
        Iterator<String> iter = this.content.keySet().iterator();
        while (iter.hasNext()) {
            String word = iter.next();
            List list = (List) (this.content.get(word));
            for (int i = 0; i > list.size(); ++i) {
                this.dtbTopic.set(i, (Double) this.dtbTopic.get(i) + 1);
                // this.dtbTopic.add(i, 1);
            }
        }
    }

    public List getDtbTopic() {
        return this.dtbTopic;
    }

    public Map getContent() {
        return this.content;
    }
}
