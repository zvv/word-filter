package com.dmwsy.platform.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LDAInference {

    public int nTopic = 0;
    public int nWord = 0;
    public double alpha = 0.01;
    public double beta = 0.01;
    public Map dtbWordTopic = new HashMap<String, List>();
    public List dtbTopic = new ArrayList<Double>();
    public Set vocab = new HashSet();

    private LDAInference() {
    }

    public LDAInference(File file, double alpha_, double beta_) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] infos = line.split(" ");
                List list = new ArrayList<Double>();
                for (int i = 1; i < infos.length; ++i) {
                    list.add(Double.parseDouble(infos[i]));
                }
                this.dtbWordTopic.put(infos[0], list);
            }
            this.nWord = this.dtbWordTopic.size();
            // initialize nTopic and vocab and dtbTopic
            Iterator<String> iter = this.dtbWordTopic.keySet().iterator();
            String word = iter.next();
            List list = (List) dtbWordTopic.get(word);
            this.nTopic = list.size();
            this.vocab.add(word);
            for (int i = 0; i < this.nTopic; ++i) {
                this.dtbTopic.add(list.get(i));
            }
            // complete vocab and dtbTopic
            while (iter.hasNext()) {
                word = iter.next();
                list = (List) dtbWordTopic.get(word);
                vocab.add(word);
                for (int i = 0; i < this.nTopic; ++i) {
                    this.dtbTopic.set(i, (Double) this.dtbTopic.get(i) + (Double) list.get(i));
                    // this.dtbTopic.add(i+(double)(list.get(i)));
                }
            }
            this.alpha = alpha_;
            this.beta = beta_;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void infer(LDADocument doc, int iterations, int burn_iterations, boolean flag) {

        List dtb = new ArrayList<Double>();
        for (int i = 0; i < this.nTopic; ++i) {
            dtb.add(0.0);
        }
        Map content = doc.getContent();
        List dtbTopicForDoc = doc.getDtbTopic();
        for (int index = 0; index < iterations; ++index) {
            for (Iterator<String> iter = content.keySet().iterator(); iter.hasNext();) {
                String word = iter.next();
                List temp = (List) content.get(word);
                for (int i = 0; i < temp.size(); ++i) {
                    int wordtopic = (Integer) temp.get(i);
                    List dtbTopicForWord = generateDtbTopicForWord(doc, word, wordtopic, flag);
                    int newtopic = getAccumulativeSample(dtbTopicForWord);
                    if (flag) {
                        adjustModel(word, wordtopic, newtopic, 1);
                    }
                    dtbTopicForDoc.set(wordtopic, (Double) dtbTopicForDoc.get(wordtopic) - 1);
                    dtbTopicForDoc.set(newtopic, (Double) dtbTopicForDoc.get(newtopic) + 1);
                    // dtbTopicForDoc.add(wordtopic, -1);
                    // dtbTopicForDoc.add(newtopic, 1);
                    temp.set(i, newtopic);
                }
            }
            if (index >= burn_iterations) {
                for (int i = 0; i < this.nTopic; ++i) {
                    // dtb.add(i, (double)dtbTopicForDoc.get(i));
                    dtb.set(i, (Double) dtb.get(i) + (Double) dtbTopicForDoc.get(i));
                }
            }
        }

        for (int i = 0; i < this.nTopic; ++i) {
            dtbTopicForDoc.set(i, (Double) (dtb.get(i)) / (iterations - burn_iterations + 1));
        }
    }

    public void adjustModel(String word, int oldtopic, int newtopic, int step) {
        List dtb = (List) this.dtbWordTopic.get(word);
        dtb.set(oldtopic, (Double) dtb.get(oldtopic) - step);
        dtb.set(newtopic, (Double) dtb.get(newtopic) + step);
        // dtb.add(oldtopic, -step);
        // dtb.add(newtopic, step);
        this.dtbTopic.set(oldtopic, (Double) this.dtbTopic.get(oldtopic) - step);
        this.dtbTopic.set(newtopic, (Double) this.dtbTopic.get(newtopic) + step);
        // this.dtbTopic.add(oldtopic, -step);
        // this.dtbTopic.add(newtopic, step);
    }

    public List generateDtbTopicForWord(LDADocument doc, String word, int wordtopic, boolean flag) {
        List dtbTopicForWord = new ArrayList<Double>();

        for (int i = 0; i < this.nTopic; ++i) {
            int adjust = (flag && i == wordtopic) ? -1 : 0;
            double fWordTopic = (Double) ((List) this.dtbWordTopic.get(word)).get(i) + adjust;
            double fTopicForModel = (Double) this.dtbTopic.get(i) + adjust;
            double fTopicForDoc = (Double) doc.getDtbTopic().get(i) + adjust;

            double temp = (fWordTopic + this.beta) * (fTopicForDoc + this.alpha)
                    / (fTopicForModel + this.nWord * this.beta);
            dtbTopicForWord.add(temp);
        }

        return dtbTopicForWord;
    }

    public int getAccumulativeSample(List dtb) {
        double sum = 0.0;
        Random random = new Random(); // seed ?
        for (int i = 0; i < dtb.size(); ++i) {
            sum += (Double) dtb.get(i);
        }
        double threshold = random.nextDouble() * sum;
        sum = 0.0;
        int index = -1;
        while (sum < threshold) {
            sum += (Double) dtb.get(++index);
        }

        return index;
    }

    public void infer(LDADocument doc, int iterations, boolean flag) {
        infer(doc, iterations, iterations, flag);
    }

    public int getNTopic() {
        return this.nTopic;
    }

    public double getAlpha() {
        return this.alpha;
    }

    public double getBeta() {
        return this.beta;
    }

    public Set getVocab() {
        return this.vocab;
    }

    public Map getDtbWordTopic() {
        return this.dtbWordTopic;
    }

    public List getDtbTopic() {
        return this.dtbTopic;
    }
}
