package com.revath.banking.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.revath.banking.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException
	{
		String authHeader=request.getHeader("Authorization");
		if(authHeader==null || !authHeader.startsWith("Bearer "))
		{
			filterChain.doFilter(request, response);
			return;
		}
		String token=authHeader.substring(7);
		try
		{
			String email=jwtService.extractEmail(token);
			if(email!=null && jwtService.isTokenValid(token, email))
			{
				UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority("USER"))
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
		}
		catch(Exception e)
		{
			System.out.println("Invalid JWT token");
		}
		filterChain.doFilter(request,response);
		
		
	}
	
	
	
	

}
