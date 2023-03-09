package ma.akkady.textileseller.constants;

public class MappingUrls {
    public static final String API_URL = "/api/v1";

    public static class CLIENTS {
        public static final String BASE_URL = "/clients";
        public static final String GET_API_DESCRIPTION = "Get all clients";
    }

    public static class VENDORS {
        public static final String TAG = "Vendors";
        public static final String BASE_URL = "/vendors";
        public static final String REGISTRATION = "/subscribe";
        public static final String PASSWORD = "/password";
        public static final String SEARCH = "/search";
        public static final String GET_BY_ID = SEARCH + "/{id}";


    }
}
