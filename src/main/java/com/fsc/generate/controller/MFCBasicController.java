package com.fsc.generate.controller;

import common.config.tools.config.ConfigTools3;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class MFCBasicController {

    public Integer getCookieAsInt(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(cookieName)) {
                    return Integer.valueOf(cookies[i].getValue());
                }
            }
        }
        return null;
    }

    public void writeCookie(HttpServletResponse response, String cookieName, Object cookieValue) {
        Cookie cookie = new Cookie(cookieName, String.valueOf(cookieValue));
        cookie.setPath("/");
        cookie.setMaxAge(31104000);
        response.addCookie(cookie);
    }

    public String getPlan(int plan){
        return ConfigTools3.getString("crm.device.plan."+plan,"Unknown");
    }

    public String getProduct(long product){
        return ConfigTools3.getString("crm.account.plan.name."+product,"Unknown");
    }
}
