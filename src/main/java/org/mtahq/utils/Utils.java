/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mtahq.utils;

import java.text.DateFormat;

/**
 *
 * @author MNILSEN
 */
public class Utils {

    private static DateFormat dfLong = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

    public static String formatLongDateTime(long timestamp) {
        return dfLong.format(timestamp);
    }

    public static void main(String[] args) {
        System.out.println(Utils.formatLongDateTime(1480482000000L));
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            //  no op
        }
    }
}
