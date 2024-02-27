package com.chinthaka.backendroyallmssystem.statics;


import io.micrometer.core.instrument.Counter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class ApiRequestInterceptor implements HandlerInterceptor {

//    private final Counter apiRequestCounter;

//    public ApiRequestInterceptor(Counter apiRequestCounter) {
//        this.apiRequestCounter = apiRequestCounter;
//    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        apiRequestCounter.increment();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
