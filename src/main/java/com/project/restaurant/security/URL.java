package com.project.restaurant.security;

public class URL {
    public static final String ITEM = "/item/**";
    public static final String ORDER = "/orders/**";
    public static final String ROLE = "/roles/**";
    public static final String INGREDIENT = "/ingredient/**";
    public static final String USER = "/user/**";
    public static final String CITY = "/city/**";
    public static final String SAVE_USER = "/user/save";
    public static final String SELLERS = "/user/sellers/**";
    public static final String REFRESH_TOKEN = "/user/refresh-token";
    public static final String SWAGGER = "/swagger-ui/**";
    public static final String ITEMS_SELLER = "/item/seller/**";
    public static final String ITEM_FILTER = "/item/filter/**";
    public static final String ITEMS = "/item/items";
    public static final String LOGIN = "/login";
    public static final String ANY = "/**";
    public static final String CONFIRM_ACOUNT = "/user/confirm-account/**";

    private URL() {
    }
}
