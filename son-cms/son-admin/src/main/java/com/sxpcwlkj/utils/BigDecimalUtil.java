package com.sxpcwlkj.utils;

import java.math.BigDecimal;

/**
 * @author http://www.sxpcwlkj.com
 * @version 1.0
 * @description com.sxpcwlkj.utils
 * @date 2019/6/29
 * BigDecimal的加法运算封装
 */
public class BigDecimalUtil {

    // 除法运算默认精度
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 精确加法  +
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 精确加法 +
     */
    public static double add(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.add(b2).doubleValue();
    }

    /**
     * 精确减法 -
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 精确减法 -
     */
    public static double sub(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 精确乘法  *
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 精确乘法  *
     */
    public static double mul(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 精确除法 使用默认精度  /
     */
    public static double div(double value1, double value2) throws IllegalAccessException {
        return div(value1, value2, DEF_DIV_SCALE);
    }

    /**
     * 精确除法 使用默认精度  /
     */
    public static double div(String value1, String value2) throws IllegalAccessException {
        return div(value1, value2, DEF_DIV_SCALE);
    }

    /**
     * 精确除法
     *
     * @param scale   /
     *            精度
     */
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        // return b1.divide(b2, scale).doubleValue();
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 精确除法   /
     *
     * @param scale
     *            精度
     */
    public static double div(String value1, String value2, int scale) throws IllegalAccessException {
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        // return b1.divide(b2, scale).doubleValue();
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入
     *
     * @param scale
     *            小数点后保留几位
     */
    public static double round(double v, int scale) throws IllegalAccessException {
        return div(v, 1, scale);
    }

    /**
     * 四舍五入
     *
     * @param scale
     *            小数点后保留几位
     */
    public static double round(String v, int scale) throws IllegalAccessException {
        return div(v, "1", scale);
    }

    /**
     * 比较大小 小于0：v1 < v2 大于0：v1 > v2 等于0：v1 = v2
     *
     * @param v1
     * @param v2
     * @return
     */
    public static int getCompare(BigDecimal n1,BigDecimal n2) {
        return n1.compareTo(n2);
    }
    /**
     * 比较大小 小于0：v1 < v2 大于0：v1 > v2 等于0：v1 = v2
     *
     * @param v1
     * @param v2
     * @return
     */
    public static int getCompare(double v1, double v2) {
        BigDecimal n1 = new BigDecimal(Double.toString(v1));
        BigDecimal n2 = new BigDecimal(Double.toString(v2));
        return n1.compareTo(n2);
    }


    public static double getDoubleDian(double d,int n) {
        BigDecimal bg = new BigDecimal(d);
        /**
         * 参数：
         newScale - 要返回的 BigDecimal 值的标度。
         roundingMode - 要应用的舍入模式。
         返回：
         一个 BigDecimal，其标度为指定值，其非标度值可以通过此 BigDecimal 的非标度值乘以或除以十的适当次幂来确定。
         */
        double f1 = bg.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    /**
     * 转为负数
     * @param bigDecimal
     * @return
     */
    public  static BigDecimal getNegate(BigDecimal bigDecimal){
        return bigDecimal.negate();
    }
    /**
     * 转为正数
     * @param bigDecimal
     * @return
     */
    public  static BigDecimal getAbs(BigDecimal bigDecimal){
        return bigDecimal.abs();
    }

    /**
     * 小数点向右移动n位
     * @param yuan
     * @return
     */
    public static final Integer ONE=1;
    public static final Integer TWO=2;
    public static final Integer THREE=3;
    public static final Integer FOUR=4;
    public static final Integer FIVE=5;
    public static Integer getBigDecimalDian(BigDecimal yuan,int num) {
        //（重点）Double直接转BigDecimal丢失精度，此处需要将Double转换为String
        return yuan.movePointRight(num).intValue();
    }
}