package com.laonstory.bbom.global.config;


import com.google.gson.Gson;
import com.laonstory.bbom.global.dto.ErrorResponse;
import com.laonstory.bbom.global.error.model.ErrorCode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class ExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (EntityNotFoundException ex){
            log.error("exception exception handler filter");
            setErrorResponse(HttpStatus.FORBIDDEN,response,ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response,Throwable ex){
        response.setStatus(status.value());
        response.setContentType("application/json");
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.HANDLE_ACCESS_DENIED);
        try{
            Gson gson = new Gson();
            String json = gson.toJson(errorResponse);
            //String json = errorResponse.convertToJson();
            System.out.println(json);
            response.getWriter().write(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}