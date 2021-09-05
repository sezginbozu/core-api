package com.boz.control;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.boz.entity.Constant;
import com.boz.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;


//@Component
public class AuthenticationFilter extends GenericFilterBean {

    private String appName;

    private User user;
    private ObjectMapper mapper;

    public AuthenticationFilter(ApplicationContext applicationContext) {
        this.user = applicationContext.getBean(User.class);
        this.mapper = applicationContext.getBean(ObjectMapper.class);
        this.appName = applicationContext.getId();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var httpResponse = (HttpServletResponse) response;

        try {
            final String userStr = URLDecoder.decode(httpRequest.getHeader(Constant.HTTP_HEADER_USER), "UTF-8");
            //{"BOLUM":"0G0110","KULLANICIID":"T10000","AD":"BYS","YETKIGRUP":"ADMIN,GROUP1,GROUP2","SOYAD":"BYS","ID":1000}
            Map<String, String> apiRequestHeaders = new HashMap<>();
            apiRequestHeaders.put(Constant.HTTP_HEADER_TOKEN, httpRequest.getHeader(Constant.HTTP_HEADER_TOKEN));
            apiRequestHeaders.put(Constant.HTTP_HEADER_CLIENT_CORR_ID,
                    httpRequest.getHeader(Constant.HTTP_HEADER_CLIENT_CORR_ID));
            apiRequestHeaders.put(Constant.HTTP_HEADER_APIGW_CORR_ID,
                    httpRequest.getHeader(Constant.HTTP_HEADER_APIGW_CORR_ID));
            apiRequestHeaders.put(Constant.HTTP_HEADER_APP,
                    httpRequest.getContextPath().length() > 1 ? httpRequest.getContextPath().substring(1) : appName);
            apiRequestHeaders.put(Constant.HTTP_HEADER_CLIENT_IP, httpRequest.getHeader(Constant.HTTP_HEADER_CLIENT_IP));
            apiRequestHeaders.put(Constant.HTTP_HEADER_CLIENT_MACHINE_NAME,
                    httpRequest.getHeader(Constant.HTTP_HEADER_CLIENT_MACHINE_NAME));
            apiRequestHeaders.put(Constant.HTTP_HEADER_CLIENT_MACHINE_USER_ID,
                    httpRequest.getHeader(Constant.HTTP_HEADER_CLIENT_MACHINE_USER_ID));
            apiRequestHeaders.put(Constant.HTTP_HEADER_CLIENT_PAGE_ID,
                    httpRequest.getHeader(Constant.HTTP_HEADER_CLIENT_PAGE_ID));

            if (userStr != null && !userStr.isEmpty()) {
                this.setUser(userStr);
                Locale locale = httpRequest.getLocale();
                if (locale == null || locale.getLanguage().equals("") || locale.getLanguage().equals("*")
                        || locale.getLanguage().startsWith("tr")) {
                    locale = null;
                }

                user.setLocale(locale);
                user.getApiRequestHeaders().putAll(apiRequestHeaders);
                this.login(httpRequest, user);
                if (httpRequest.getHeader(Constant.HTTP_HEADER_CLIENT_CORR_ID) != null)
                    httpResponse.addHeader(Constant.HTTP_HEADER_CLIENT_CORR_ID,
                            httpRequest.getHeader(Constant.HTTP_HEADER_CLIENT_CORR_ID));
            }
            else {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized!");
                return;
            }
        } catch (Exception e) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized!");
            return;
        }

        chain.doFilter(request, response);
    }

    public void login(HttpServletRequest req, User user) {
        List<GrantedAuthority> grantedAuths = user.getUserRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user, "",
                grantedAuths);
        SecurityContextHolder.getContext().setAuthentication(authReq);
        req.setAttribute("user", user);
    }

    public void setUser(String userJson) {
        try {
            Map object = mapper.readValue(userJson, HashMap.class);
            this.user.setId(Integer.parseInt(object.get("ID").toString()));
            this.user.setUserId(object.get("KULLANICIID").toString());
            this.user.setName(object.get("AD").toString());
            this.user.setSurname(object.get("SOYAD").toString());
            this.user.setDepartment(object.get("BOLUM").toString());
            List<String> userRoles = Arrays.asList(object.get("YETKIGRUP").toString().split(","));
            this.user.setUserRoles(userRoles);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
