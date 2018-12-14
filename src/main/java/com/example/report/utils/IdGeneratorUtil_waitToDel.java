package com.example.report.utils;

import java.util.Random;

public class IdGeneratorUtil_waitToDel {
    private static Random random = new Random(9);

    public static long createID() {

        return  System.currentTimeMillis() + random.nextInt();
    }
}
