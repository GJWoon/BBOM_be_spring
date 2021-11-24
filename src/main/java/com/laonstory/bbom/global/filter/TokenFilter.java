package com.laonstory.bbom.global.filter;

import com.laonstory.bbom.global.provider.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.persistence.EntityNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenFilter extends GenericFilterBean {




    private final TokenProvider tokenProvider;

    public TokenFilter(TokenProvider tokenProvider){
        this.tokenProvider =tokenProvider;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = tokenProvider.resolveToken((HttpServletRequest) request);
        try {

            if(token !=null && tokenProvider.validateToken(token) && !token.equals("")){
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (Exception e){
            throw new EntityNotFoundException();
        }
        chain.doFilter(request,response);
    }
}
