package com.laonstory.bbom.global.config;


import com.google.common.net.HttpHeaders;
import com.laonstory.bbom.global.application.AccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccessInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private AccessService accessService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("[REQ] ---> [METHOD] {} | [URL] {} | [qs] {} | [TOKEN] {} | reto {}",request.getMethod(), request.getRequestURI(), request.getQueryString(), request.getHeader(HttpHeaders.AUTHORIZATION),request.getHeader("Authorization: Bearer"));
        log.info("ip --->"+getLocalServerIp(request));
        accessService.accessHistory(getLocalServerIp(request));
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("[RES] ---> [STATUS] {}", response.getStatus());
        //log.info("request" + request.getParts());
        // log.info("requets" + request.get());
        super.postHandle(request, response, handler, modelAndView);
    }

    private String getLocalServerIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        log.info("X-FORWARDED-FOR : " + ip);
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info("Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.info("WL-Proxy-Client-IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            log.info("HTTP_CLIENT_IP : " + ip);
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("HTTP_X_FORWARDED_FOR : " + ip);
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
            log.info("getRemoteAddr : "+ip);
        }
        log.info("Result : IP Address : "+ip);
        return ip;
    }

}
