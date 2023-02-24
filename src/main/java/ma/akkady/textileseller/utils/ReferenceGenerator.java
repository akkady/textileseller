package ma.akkady.textileseller.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class ReferenceGenerator {
    public static Long gen() {
        return Long.parseLong(RandomStringUtils.randomNumeric(14));
    }

    public static String genNumeric() {
        return RandomStringUtils.randomNumeric(14);
    }
    public static String genStringRef() {
        return RandomStringUtils.randomAlphanumeric(14);
    }
    public static String genComplexRef() {
        return UUID.randomUUID().toString();
    }
}
