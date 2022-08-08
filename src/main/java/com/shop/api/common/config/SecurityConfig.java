package com.shop.api.common.config;

import com.shop.api.common.advice.CustomAccessDeniedHandler;
import com.shop.api.common.advice.CustomAuthenticationEntryPoint;
import com.shop.api.security.JwtAuthenticationFilter;
import com.shop.api.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers("/api/v1/login").permitAll()
                .antMatchers(HttpMethod.POST, "/category").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/user").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/api/v1/user").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/user").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/v1/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/refresh").hasRole("ANONYMOUS")
                .antMatchers(HttpMethod.POST, "/api/v2/seller").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/v3/product").hasRole("SELLER")
                .antMatchers(HttpMethod.POST, "/api/v4/search").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v5/cart").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/v5/cart").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/api/v5/cart").hasRole("USER")
                .antMatchers(HttpMethod.DELETE, "/api/v5/cart/del").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/v6/order").hasRole("USER")

                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


}
