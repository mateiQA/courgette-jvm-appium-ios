import drivermanager.DriverManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {
    public static String getProperty(String propertyName) {
        Properties properties = new Properties();

        try (InputStream in = DriverManager.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            properties.load(in);

            return properties.getProperty(propertyName);

        } catch (IOException e) {
            throw new RuntimeException("Fail to load or read properties file", e);
        }


    }
}
