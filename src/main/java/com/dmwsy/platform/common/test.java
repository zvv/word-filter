package com.dmwsy.platform.common;

/**
 */
public class test {
    /**
     * 判断是否匹配
     * 
     * @param target
     *            目标文本串
     * @param mode
     *            模式串
     * @return 匹配结果
     */
    public static boolean matchString(String target, String mode) {
        // 为了和算法保持一致，使index从1开始，增加一前缀
        String newTarget = "x" + target;
        String newMode = 'x' + mode;

        int[] K = calculateK(mode);
        System.out.println(K);
        int i = 1;
        int j = 1;
        while (i <= target.length() && j <= mode.length()) {
            if (j == 0 || newTarget.charAt(i) == newMode.charAt(j)) {
                i++;
                j++;
            } else {
                j = K[j];
            }
        }

        if (j > mode.length()) {
            return true;
        }
        return false;
    }

    /*
     * 计算K值
     */
    private static int[] calculateK(String mode) {
        // 为了和算法保持一致，使index从1开始，增加一前缀
        String newMode = "x" + mode;
        int[] K = new int[newMode.length()];
        int i = 1;
        K[1] = 0;
        int j = 0;

        while (i < mode.length()) {
            if (j == 0 || newMode.charAt(i) == newMode.charAt(j)) {
                i++;
                j++;
                K[i] = j;
            } else {
                j = K[j];
            }
        }
        for (int P : K) {
            System.out.print(P + "  ");
        }
        return K;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String a = "bcabcabcabbcabcabcabcab";
        String b = "bcabcabcabc";// "ababbaaba";//
        System.out.println(test.matchString(a, b));

    }
}
