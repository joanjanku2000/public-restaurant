package com.project.restaurant.security;

import com.project.restaurant.base.dtos.Roles;
import com.project.restaurant.security.filter.CustomAuthenticationFilter;
import com.project.restaurant.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor // automatically do DI
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(URL.LOGIN, URL.SAVE_USER, URL.SWAGGER,
                        URL.CONFIRM_ACOUNT, URL.SELLERS, URL.ITEMS_SELLER
                        , URL.ITEM_FILTER)
                .permitAll();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, URL.ITEM)
                .hasAnyAuthority(Roles.SELLER.toString(), Roles.USER.toString(), Roles.ADMIN.toString());

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, URL.ROLE)
                .hasAuthority(Roles.ADMIN.toString());

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, URL.ITEM, URL.INGREDIENT, URL.CITY, URL.ROLE)
                .hasAnyAuthority(Roles.SELLER.toString(), Roles.ADMIN.toString());

        http.authorizeRequests()
                .antMatchers(URL.ITEMS)
                .hasAnyAuthority(Roles.USER.toString(), Roles.ADMIN.toString(), Roles.SELLER.toString());

        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT,
                        URL.ITEM, URL.INGREDIENT, URL.CITY, URL.ROLE)
                .hasAnyAuthority(Roles.SELLER.toString(), Roles.ADMIN.toString());

        http.authorizeRequests()
                .antMatchers(HttpMethod.PATCH, URL.ITEM)
                .hasAnyAuthority(Roles.SELLER.toString(), Roles.ADMIN.toString());

        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, URL.ANY)
                .hasAnyAuthority(Roles.ADMIN.toString(), Roles.SELLER.toString());

        http.authorizeRequests()
                .antMatchers(URL.ORDER)
                .hasAnyAuthority(Roles.ADMIN.toString(), Roles.SELLER.toString(), Roles.USER.toString());

        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(URL.SWAGGER, URL.REFRESH_TOKEN);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
