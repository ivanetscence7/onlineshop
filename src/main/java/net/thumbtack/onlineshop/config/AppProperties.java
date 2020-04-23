package net.thumbtack.onlineshop.config;

import net.thumbtack.onlineshop.exception.AppErrorCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private static Map<String, AppErrorCode> errorCodeMap;
    public static final String COOKIE_NAME = "JAVASESSIONID";
    private static int restHttpPort;
    private static int maxNameLength;
    private static int minPasswordLength;
    private static int minNameLength;

    public static void initSettings() {
        errorCodeMap = new HashMap<>();
        for (AppErrorCode errorCode : AppErrorCode.values()) {
            errorCodeMap.putIfAbsent(errorCode.getField(), errorCode);
        }
    }

    public static Map<String, AppErrorCode> getErrorCodeMap() {
        return errorCodeMap;
    }

    public static String getCookieName() {
        return COOKIE_NAME;
    }

    public int getRestHttpPort() {
        return restHttpPort;
    }

    public void setRestHttpPort(int restHttpPort) {
        AppProperties.restHttpPort = restHttpPort;
    }

    public static int getMaxNameLength() {
        return maxNameLength;
    }

    public void setMaxNameLength(int maxNameLength) {
        AppProperties.maxNameLength = maxNameLength;
    }

    public static int getMinPasswordLength() {
        return minPasswordLength;
    }

    public void setMinPasswordLength(int minPasswordLength) {
        AppProperties.minPasswordLength = minPasswordLength;
    }

    public int getMinNameLength() {
        return minNameLength;
    }

    public void setMinNameLength(int minNameLength) {
        AppProperties.minNameLength = minNameLength;
    }

    public static AppErrorCode getAppErrorCode(String fieldName) {
        return errorCodeMap.get(fieldName);
    }

    public static void setErrorCodeMap(Map<String, AppErrorCode> errorCodeMap) {
        AppProperties.errorCodeMap = errorCodeMap;
    }
}
