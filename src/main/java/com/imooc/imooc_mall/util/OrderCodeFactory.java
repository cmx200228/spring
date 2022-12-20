package com.imooc.imooc_mall.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成订单号工具类
 * @author 陈蒙欣
 * @date 2022/12/20 21:54
 */
public class OrderCodeFactory {

    private OrderCodeFactory() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * 订单类别头
     */
    private static final String ORDER_HEAD = "1";

    /**
     * 随机编码
     */
    private static final int[] R = new int[]{7, 9, 6, 2, 8, 1, 3, 0, 5, 4};

    private static final int CODE_LEN = 5;


    /**
     * 根据id进行加密+加随机数组成固定长度编码
     */
    private static String toCode(Long id) {
        String idStr = id.toString();
        StringBuilder idSb = new StringBuilder();
        for (int i = idStr.length() - 1; i >= 0; i--) {
            idSb.append(R[idStr.charAt(i) - '0']);
        }
        return idSb.append(getRandom(CODE_LEN - idStr.length())).toString();
    }

    /**
     * 生成时间戳
     */
    private static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("HHmmss");
        return sdf.format(new Date());
    }

    /**
     * 生成固定长度随机码
     *
     * @param n 长度
     */
    private static long getRandom(long n) {
        long min = 1;
        long max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        return ((long) (new Random().nextDouble() * (max - min))) + min;
    }

    /**
     * 生成不带类别标头的编码
     */
    private static synchronized String getCode(Long userId) {
        userId = userId == null ? 10000 : userId;
        return getDateTime() + toCode(userId);
    }

    /**
     * 生成订单单号编码
     */
    public static String getOrderCode(Long userId) {
        return ORDER_HEAD + getCode(userId);
    }

}
