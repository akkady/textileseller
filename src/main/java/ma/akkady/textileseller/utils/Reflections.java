package ma.akkady.textileseller.utils;

import java.lang.reflect.Field;

public class Reflections {
    public static void setAttributeValue(Object object,Field field, String attributeName, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(object, value);
        field.setAccessible(false);
    }

}
