package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = ConfigManager.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
                // Resolve system properties in the values
                resolveSystemProperties();
            } else {
                throw new RuntimeException("Unable to find config.properties");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private static void resolveSystemProperties() {
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            if (value != null && value.contains("${user.dir}")) {
                String resolvedValue = value.replace("${user.dir}", System.getProperty("user.dir"));
                properties.setProperty(key, resolvedValue);
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String getPlatform() {
        return getProperty("platform", "android");
    }

    public static String getAppPath() {
        String platform = getPlatform();
        return getProperty("app.path." + platform);
    }

    public static boolean isAndroid() {
        return getPlatform().equalsIgnoreCase("android");
    }

    public static boolean isIOS() {
        return getPlatform().equalsIgnoreCase("ios");
    }
}