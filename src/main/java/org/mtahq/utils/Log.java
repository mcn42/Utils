/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mtahq.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author MNILSEN
 */
public class Log {

    public static Logger log = null;
    public static Logger activityLog = null;

    public static void configure(String logFilePrefix, String logPackage, String path) {
        log = Logger.getLogger(logPackage);
        activityLog = Logger.getLogger(logPackage + ".activity");
        log.setUseParentHandlers(false);
        activityLog.setUseParentHandlers(false);
        log.setLevel(Level.ALL);

        try {
            File dir = new File(path);
            if (!dir.exists()) {
                log.info(String.format("Creating Log directory at %s", dir.getCanonicalPath()));
                dir.mkdirs();
            }
            FileHandler fh = new FileHandler(path + "/" + logFilePrefix + ".log", 1000000, 6, false);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.ALL);
            log.addHandler(fh);

            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            log.addHandler(ch);

            fh = new FileHandler(path + "/" + logFilePrefix + "_error.log", 1000000, 6, false);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.WARNING);
            log.addHandler(fh);

            fh = new FileHandler(path + "/" + logFilePrefix + "_activity.log", 1000000, 6, false);
            fh.setFormatter(new PlainFormatter());
            fh.setLevel(Level.ALL);
            activityLog.addHandler(fh);

        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to add logging FileHandler", e);
        }

    }

    public static Logger getLog() {
        return log;
    }

    public static Logger getActivityLog() {
        return activityLog;
    }

}
