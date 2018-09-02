package com.dmwsy.platform.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {
	/**
	 *
	 * @Description: TODO(判断字符串中是否包含英文字母)
	 */
	public static boolean isHaveChar(String str){
		String regex=".*[a-zA-Z]+.*";
		Matcher m=Pattern.compile(regex).matcher(str);
		return m.matches();
	}
}
