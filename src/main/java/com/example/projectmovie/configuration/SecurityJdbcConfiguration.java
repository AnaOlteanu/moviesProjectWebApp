package com.example.projectmovie.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityJdbcConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/movie/new", "/actor/new", "/genre/new").hasRole("ADMIN")
                .antMatchers("/movie/delete/*", "/actor/delete/*", "/movie/movieInfoEdit/*",
                        "/genre/edit/*", "/actor/editContactInfo/*", "/movie/edit/*").hasRole("ADMIN")
                .antMatchers("/movie/*", "/actor/*", "/movie/editGenres/*", "/genre/*").hasAnyRole("ADMIN", "GUEST")
                .and()
                .formLogin()
                .defaultSuccessUrl("/movie/list")
                .loginPage("/login")
                .loginProcessingUrl("/authUser")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");

        http.logout().logoutSuccessUrl("/");

    }

}
