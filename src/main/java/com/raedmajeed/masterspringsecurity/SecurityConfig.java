package com.raedmajeed.masterspringsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.raedmajeed.masterspringsecurity.OwnerFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OwnerFilter ownerFilter;

//    @Order(1)
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.securityMatcher("/admin")
//                .authorizeHttpRequests(auth -> {
//                    auth.anyRequest().hasRole("ADMIN");
//                })
//                .formLogin(Customizer.withDefaults());
////                .oauth2Login(Customizer.withDefaults());
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
        http
//                .securityMatcher("/user")
                .authorizeHttpRequests(auth -> {

                    auth.requestMatchers("/private").authenticated();
                    auth.anyRequest().permitAll();
                })
                .formLogin(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                .addFilterBefore(new OwnerFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    @Order(3)
//    public SecurityFilterChain formLoginFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll()
//                );
////                .formLogin(Customizer.withDefaults());
//        return http.build();
//    }

//    @Bean
//    @Order(3)
//    public SecurityFilterChain securityFilterChain3(HttpSecurity http) throws Exception {
//        return  http.authorizeHttpRequests(auth->auth.anyRequest().permitAll()).build();
//    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        daoAuthenticationProvider().setUserDetailsService();
//        daoAuthenticationProvider().setPasswordEncoder();
//    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager( User.builder().username("raed")
//                .password("{noop}1234")
//                .authorities("ROLE_USER")
//                .build()
//        );
//    }
    @Bean
    public UserDetailsService userDetailsService2() {
        return new InMemoryUserDetailsManager( User.builder().username("admin")
                .password("{noop}1234")
                .authorities("ROLE_ADMIN")
                .build()
        );
    }

}
