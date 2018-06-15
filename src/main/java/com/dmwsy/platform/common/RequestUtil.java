package com.dmwsy.platform.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016年12月19日 下午4:14:51 类说明
 */

public class RequestUtil {

    public static void responseJson(HttpServletResponse response, String json) throws IOException {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 可以方便地修改日期格式
        String time = dateFormat.format(now);
        System.out.println(time + "  " + json);
        System.out.println();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }
}
