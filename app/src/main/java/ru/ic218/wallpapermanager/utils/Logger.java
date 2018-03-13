package ru.ic218.wallpapermanager.utils;

import ru.ic218.wallpapermanager.BuildConfig;

/**
 * @author Nikolay Vlaskin on 13.03.2018.
 */

public class Logger {

    public static void log(String message){
        if (BuildConfig.DEBUG) {
            System.out.println(message);
        }
    }
}
