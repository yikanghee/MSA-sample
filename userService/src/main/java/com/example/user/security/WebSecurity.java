package com.example.user.security;


import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    Environment env;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManager authenticationManager = getAuthenticationFilter(http);

        http.csrf().disable();
            http
                .authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/error/**").permitAll()
                .antMatchers("/**").permitAll()
                .and()
                .authenticationManager(authenticationManager)
                .addFilter(getAuthenticationFilter(authenticationManager));

        return http.build();
    }

    private AuthenticationManager getAuthenticationFilter(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new AuthenticationFilter(authenticationManager, userService, env);
    }


}

