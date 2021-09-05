package com.boz.entity;

import java.util.List;

public class Constant {

    public static final String HTTP_HEADER_USER = "X-Authenticated-User-Roles";
    public static final String HTTP_HEADER_TOKEN = "Authorization";

    protected static final String AUTHENTICATION_SCHEME = "X-HTTP-HEADER";
    private static final String REALM = "TAI-REALM";

    public static final String HTTP_HEADER_CLIENT_CORR_ID = "clientCorrelationId";
    public static final String HTTP_HEADER_APIGW_CORR_ID = "APINIZER-CORRELATION-ID";
    public static final String HTTP_HEADER_APP = "app";
    public static final String HTTP_HEADER_CLIENT_IP = "clientIp";
    public static final String HTTP_HEADER_CLIENT_MACHINE_NAME = "clientMachineName";
    public static final String HTTP_HEADER_CLIENT_MACHINE_USER_ID = "clientUserId";
    public static final String HTTP_HEADER_CLIENT_PAGE_ID = "clientPageId";
    public static final Boolean INCLUDE_ONLY_ID_FIELD = Boolean.TRUE;

    public static final List<String> HEADER_FIELDS = List.of(
            HTTP_HEADER_USER,
            HTTP_HEADER_TOKEN,
            HTTP_HEADER_CLIENT_CORR_ID,
            HTTP_HEADER_APIGW_CORR_ID,
            HTTP_HEADER_APP,
            HTTP_HEADER_CLIENT_IP,
            HTTP_HEADER_CLIENT_MACHINE_NAME,
            HTTP_HEADER_CLIENT_MACHINE_USER_ID,
            HTTP_HEADER_CLIENT_PAGE_ID,
            //Standart fields
            "Content-Type",
            "Accept-Language",
            "Content-Language");
}
