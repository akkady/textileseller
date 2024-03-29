package ma.akkady.textileseller.constants;

public class MappingUrls {
    private MappingUrls(){}

    public static final String API_URL = "/api/v1";

    public static class CLIENTS {
        private CLIENTS(){}
        public static final String TAG = "Clients";
        public static final String BASE_URL = API_URL+"/clients";
        public static final String BY_CODE = "/{code}";
        public static final String SEARCH = "/search";
        public static final String SEARCH_BY_CODE = SEARCH + BY_CODE;
        public static final String GET_API_DESCRIPTION = "Get all clients";

    }

    public static class VENDORS {
        private VENDORS(){}
        public static final String TAG = "Vendors";
        public static final String BASE_URL = API_URL+"/vendors";
        public static final String REGISTRATION = "/subscribe";
        public static final String PASSWORD = "/password";
        public static final String SEARCH = "/search";
        public static final String GET_BY_ID = SEARCH + "/{id}";

    }

    public static class PRODUCTS {
        private PRODUCTS(){}

        public static final String TAG = "Products";
        public static final String BASE_URL =API_URL+ "/products";
        public static final String SEARCH = "/search";
        public static final String SEARCH_BY_ID = SEARCH + "/{id}";

    }
    public static class Auth {
        private Auth(){}
        public static final String BASE = API_URL + "/auth";
        public static final String REFRESH = BASE + "/refresh";
        public static final String LOGIN = BASE + "/login";
    }

}
