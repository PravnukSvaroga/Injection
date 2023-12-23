package SomePackage;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {
    /**
     * Injects dependencies into fields annotated with AutoInjectable.
     *
     * @param object The object to inject dependencies into.
     * @param flag   A flag indicating the type of properties to retrieve.
     * @param <T>    The type of the object to inject dependencies into.
     * @return The object with injected dependencies, or null if an exception occurs.
     */
    public <T> T inject(T object, boolean flag) {
        try {
            Properties properties = getProperties(flag);
            Class<?> clazz = object.getClass();

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(AutoInjectable.class)) {
                    Class<?> fieldType = field.getType();
                    String interfaceName = fieldType.getName();
                    String implementationClassName = properties.getProperty(interfaceName);

                    if (implementationClassName != null) {
                        Object implementationInstance = Class.forName(implementationClassName).newInstance();
                        field.setAccessible(true);
                        field.set(object, implementationInstance);
                    }
                }
            }

            return object;
        } catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Retrieves properties from a configuration file.
     *
     * @param flag a boolean flag indicating which configuration file to load
     * @return the properties loaded from the specified configuration file
     * @throws IOException if an I/O error occurs while reading the configuration file
     */
    private static Properties getProperties(boolean flag) throws IOException {
        FileInputStream input;
        if (!flag) {
            input = new FileInputStream("src/main/resources/config1.properties");
        } else {
            input = new FileInputStream("src/main/resources/config2.properties");
        }
        Properties properties = new Properties();
        properties.load(input);
        return properties;
    }
}
