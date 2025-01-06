package com.bersyte.utils;

import java.util.UUID;

public class AppHelper {

    public static String getRandomId() {
        return UUID.randomUUID().toString();
    }
}
