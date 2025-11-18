package com.nocountry.crm.common;

public class ApiPaths {
    private ApiPaths() {}

    public static final String BASE = "/api/v1";
    public static final String AUTH_BASE = BASE + "/auth";
    public static final String REGISTER_PATH = AUTH_BASE + "/register";
    public static final String LOGIN_PATH = AUTH_BASE + "/login";

}
