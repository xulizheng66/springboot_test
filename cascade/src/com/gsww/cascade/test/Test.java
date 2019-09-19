package com.gsww.cascade.test;

import java.sql.Time;

import com.gsww.cascade.utils.TimeHelper;

public class Test {
    public static void main(String[] args) {
        System.out.println("rtime = " + String.valueOf(System.currentTimeMillis()));
        System.out.println("date=" + TimeHelper.getCurrentTime().replaceAll("-", "").replaceAll(" ", "").replaceAll(":", ""));
    }
}
