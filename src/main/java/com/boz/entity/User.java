package com.boz.entity;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class User {

    private Integer id; // genel sicil
    private String userId; // kullanıcı kimliği
    private String name;
    private String surname;
    private String department;
    private Map<String, String> apiRequestHeaders = new HashMap<>();
    private Locale locale;

    private List<String> userRoles = new ArrayList<>();

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }

    public Map<String, String> getApiRequestHeaders() {
        return apiRequestHeaders;
    }

    public void setApiRequestHeaders(Map<String, String> apiRequestHeaders) {
        this.apiRequestHeaders = apiRequestHeaders;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getClientMachineName() {
        String result = this.apiRequestHeaders.getOrDefault(Constant.HTTP_HEADER_CLIENT_MACHINE_NAME, resolveIpToMachineName());
        return result;
    }

    private String resolveIpToMachineName() {
        String result = "unknown";
        String machineIp = this.apiRequestHeaders.get(Constant.HTTP_HEADER_CLIENT_IP);
        if (machineIp != null) {
            InetAddress[] ips;
            try {
                ips = InetAddress.getAllByName(machineIp);
                if (ips != null && ips.length > 0) {
                    result = ips[0].getHostName() + ".";
                    result = result.toUpperCase().substring(0, result.indexOf('.'));
                    this.apiRequestHeaders.put(Constant.HTTP_HEADER_CLIENT_MACHINE_NAME, result);
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
