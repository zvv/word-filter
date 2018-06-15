package com.dmwsy.platform.common;

import java.io.File;

public class WordFilterUtil {
    private static XEyes ge = new XEyes();

    public static void initGoldenEyes() {
        ge = new XEyes();
        String path = WordFilterUtil.class.getResource("/").getPath() + "/conf/sensitive.txt";
        File file = new File(path);
        ge.indexSensitiveFromFile(file);
        System.out.println("reload configure " + file.getAbsolutePath());
    }

    public static XEyes getGoldenEyes() {
        initGoldenEyes();
        return ge;
    }
}
