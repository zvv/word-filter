package com.dmwsy.platform.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DocumentReader {
    public DocumentReader() {
        ;
    }

    public ArrayList<String> extract(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        ArrayList<String> ret = new ArrayList<String>();
        while ((line = br.readLine()) != null) {
            ret.add(line);
        }

        return ret;
    }
}
