package com.code.logging;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Just a simple logger implementation for this module
 */
public class ConsoleLogger implements System.Logger {

    private String name;

    ConsoleLogger(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isLoggable(Level level) {
        return true;
    }

    @Override
    public void log(Level level, String msg) {
        System.out.printf("[%s] [%s]: %s%n", name, level, msg);
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        System.out.printf("[%s] [%s]: %s - %s%n", name, level, msg, thrown);
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        System.out.printf("[%s] [%s]: %s%n", name, level, MessageFormat.format(format, params));
    }
}
