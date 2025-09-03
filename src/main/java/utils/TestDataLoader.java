package utils;

import java.io.InputStream;
import java.util.Properties;

public class TestDataLoader {
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            // Load from classpath instead of file path
            InputStream input = TestDataLoader.class.getClassLoader()
                    .getResourceAsStream("testdata.properties");
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("testdata.properties file not found in classpath");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data properties file.", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}