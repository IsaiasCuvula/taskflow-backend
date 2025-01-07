package com.bersyte.utils;

import java.util.UUID;

/**
 * Utility class for application-wide helper methods.
 */
public class AppHelper {

    /**
     * Generates a random UUID.
     *
     * @return a randomly generated unique identifier as a string
     */
    public static String getRandomId() {
        return UUID.randomUUID().toString();
    }
}
