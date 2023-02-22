package ma.akkady.textileseller.utils;

import java.util.UUID;

public class ReferenceGenerator {
    public static Long gen() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replaceAll("-", "");
        return Long.parseLong(uuidStr, 16);
    }

    public static String genStringRef() {
        return String.valueOf(gen());
    }
    public static String genComplexRef() {
        return UUID.randomUUID().toString();
    }
}
