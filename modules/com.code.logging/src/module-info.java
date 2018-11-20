module com.code.logging {
    provides java.lang.System.LoggerFinder
            with com.code.logging.CustomLoggerFinder;
    exports com.code.logging;
}