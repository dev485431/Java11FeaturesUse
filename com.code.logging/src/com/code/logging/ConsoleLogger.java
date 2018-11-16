package com.code.logging;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Just a simple logger implementation
 */
public class ConsoleLogger implements System.Logger {

    public static void main(String[] args) {
        ConsoleLogger log = new ConsoleLogger();
        System.out.println(log.getName());
        System.out.println(log.isLoggable(Level.ALL));
    }

    @Override
    public String getName() {
        return "ConsoleLogger";
    }

    @Override
    public boolean isLoggable(Level level) {
        return true;
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        System.out.printf("ConsoleLogger [%s]: %s - %s%n", level, msg, thrown);
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        System.out.printf("ConsoleLogger [%s]: %s%n", level, MessageFormat.format(format, params));
    }
}
