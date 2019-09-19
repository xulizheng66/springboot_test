package com.turbur.commons.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class HelperUtils {

    /**
     * 返回32位的UUID字符串
     *
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }


    /**
     * 根据当前时间生成格式字符串
     * yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String getDateToStr1() {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf1.format(new Date());
    }

    /**
     * 根据当前时间生成格式字符串
     * yyyy-MM-dd
     *
     * @return
     */
    public static String getDateToStr2() {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        return sdf2.format(new Date());
    }

    /**
     * 判断对象属性是否为空
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static boolean checkObjFieldIsNull(Object obj) throws IllegalAccessException {

        boolean flag = false;
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(obj) == null) {
                flag = true;
                return flag;
            }
        }
        return flag;
    }

    public static void main(String[] args) {
//        System.out.println(getUUID());
    }
}
