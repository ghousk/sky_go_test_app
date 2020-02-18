package com.innovativequest.sky_go_test_app.util;

/**
 * Created by Ghous on 2020-02-19.
 */
public class AppConstants {

    private static final String TAG = "AppConstants";


    // ENV DEPENDENT BASE URLs
    public static final String BASE_URL_DEV = "https://api.stackexchange.com/2.2/";
    private static final String DEFAULT_LOCALE = "en-GB";

    public static String getLocale()
    {
        return DEFAULT_LOCALE;
    }

    public static final String GET_LIST_ITEMS = "users";

    public static final String GET_LIST_ITEM_BY_ID = "users/{userId}";


}

