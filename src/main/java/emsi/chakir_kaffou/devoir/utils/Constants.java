package emsi.chakir_kaffou.devoir.utils;

import java.util.Map;

public final class Constants {
    private static final Map<String, String> roles;
    public static final String API_PREFIX = "/api/v1";

    static {
        roles = Map.of("admin", "ADMIN", "dev", "DEV", "client", "CLIENT");
    }

    public Constants() {}

    public static String getRole(String role) {
        return roles.get(role);
    }
}
