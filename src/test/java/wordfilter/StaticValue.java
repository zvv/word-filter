package wordfilter;

import java.io.File;

import com.dmwsy.platform.common.XEyes;

public class StaticValue {
    private static XEyes ge = new XEyes();

    public static void initGoldenEyes() {
        ge = new XEyes();
        File file = new File("/Users/zhangwei/work/workspace/codeGome/wukong/src/main/resources/conf/sensitive.txt");
        ge.indexSensitiveFromFile(file);
        System.out.println("reload configure " + file.getAbsolutePath());
    }

    public static XEyes getGoldenEyes() {
        initGoldenEyes();
        return ge;
    }
}
