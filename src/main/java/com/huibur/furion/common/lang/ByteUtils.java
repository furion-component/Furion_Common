package com.huibur.furion.common.lang;

/*******************************************************
 * Copyright(c)2019-2019 HuiBur. All rights reserved.
 * Header: ByteUtils.java
 * Discussion: 字节转换工具
 *      1. 将字节自动换算为最大单位
 *      2. 指定单位进行换算
 * Create Date：2019/6/6
 * Author: Jerry Wen
 * Version: 1.0
 *******************************************************/
public class ByteUtils {
    private static final int RADIX = 1024;

    public static enum UnitType {
        B("B"), KB("KB"), MB("MB"), GB("GB"), TB("TB"), PB("PB"),
        EB("EB"), ZB("ZB"), YB("YB"), NB("NB"), DB("DB");
        private final String des;

        private UnitType(String des) {
            this.des = des;
        }

        public String getDes() {
            return des;
        }
    }

    /***
     * Name: formatByteSize
     * Discussion: 字节单位数据换算为最大单位
     * Params:
     *      byteSize 字节单位值
     * Return:
     *      带最大单位字符串
     ***/
    public static String formatByteSize(long byteSize) {
        if (byteSize <= -1) {
            return String.valueOf(byteSize);
        }

        double size = 1.0 * byteSize;

        if ((int) Math.floor(size / RADIX) <= 0) { // 不足1KB
            return format(size, UnitType.B);
        }

        size = size / RADIX;
        if ((int) Math.floor(size / RADIX) <= 0) { // 不足1MB
            return format(size, UnitType.KB);
        }

        size = size / RADIX;
        if ((int) Math.floor(size / RADIX) <= 0) { // 不足1GB
            return format(size, UnitType.MB);
        }

        size = size / RADIX;
        if ((int) Math.floor(size / RADIX) <= 0) { // 不足1TB
            return format(size, UnitType.GB);
        }

        size = size / RADIX;
        if ((int) Math.floor(size / RADIX) <= 0) { // 不足1PB
            return format(size, UnitType.TB);
        }

        size = size / RADIX;
        if ((int) Math.floor(size / RADIX) <= 0) {
            return format(size, UnitType.PB);
        }

        size = size / RADIX;
        if ((int) Math.floor(size / RADIX) <= 0) {
            return format(size, UnitType.EB);
        }

        size = size / RADIX;
        if ((int) Math.floor(size / RADIX) <= 0) {
            return format(size, UnitType.ZB);
        }

        size = size / RADIX;
        if ((int) Math.floor(size / RADIX) <= 0) {
            return format(size, UnitType.YB);
        }

        size = size / RADIX;
        if ((int) Math.floor(size / RADIX) <= 0) {
            return format(size, UnitType.NB);
        }

        size = size / RADIX;
        if ((int) Math.floor(size / RADIX) <= 0) {
            return format(size, UnitType.DB);
        }
        return ">DB";
    }

    /***
     * Name: formatByteSize
     * Discussion: 字节单位数据换算为指定单位
     * Params:
     *      byteSize 字节单位值
     * Return:
     *      指定单位字符串
     ***/
//    public static String formatByteSize(long byteSize, UnitType unitType){
//           for(){
//
//           }
//    }



    /***
     * Name: format
     * Discussion: 根据单位类型获取展示字符串
     * Params:
     *     size 大小
     *     type 转换类型
     * Return:
     ***/
    private static String format(double size, UnitType unitType) {
        int precision = 0;
        if (size * 1000 % 10 > 0) {
            precision = 3;
        } else if (size * 100 % 10 > 0) {
            precision = 2;
        } else if (size * 10 % 10 > 0) {
            precision = 1;
        } else {
            precision = 0;
        }

        String formatStr = "%." + precision + "f";
        switch (unitType) {
            case KB:
                return String.format(formatStr, size) + UnitType.KB.getDes();
            case MB:
                return String.format(formatStr, size) + UnitType.MB.getDes();
            case GB:
                return String.format(formatStr, size) + UnitType.GB.getDes();
            case TB:
                return String.format(formatStr, size) + UnitType.TB.getDes();
            case PB:
                return String.format(formatStr, size) + UnitType.PB.getDes();
            case EB:
                return String.format(formatStr, size) + UnitType.EB.getDes();
            case ZB:
                return String.format(formatStr, size) + UnitType.ZB.getDes();
            case YB:
                return String.format(formatStr, size) + UnitType.YB.getDes();
            case NB:
                return String.format(formatStr, size) + UnitType.NB.getDes();
            case DB:
                return String.format(formatStr, size) + UnitType.DB.getDes();
        }

        return String.format(formatStr, (size)) + UnitType.B.getDes();
    }

}
