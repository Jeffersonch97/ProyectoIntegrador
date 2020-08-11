package com.project.Veterinary.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
		 http.authorizeRequests()
         .antMatchers(
             "/",
         	"/registration**",
             "/js/**",
             "/css/**",
             "/img/**",
             "/h2-console/**",
             "/webjars/**").permitAll()
         .antMatchers("/productos/listA").access("hasRole('ADMIN')")
         .antMatchers("/productos/add").access("hasRole('ADMIN')")
         
         .antMatchers("/promociones/list_promotionA").access("hasRole('ADMIN')")
         .antMatchers("/promociones/add_promotion").access("hasRole('ADMIN')")
         
         .antMatchers("/users/add_user").access("hasRole('ADMIN')")
         .antMatchers("/users/list_user").access("hasRole('ADMIN')")
       
         .and().csrf().ignoringAntMatchers("/h2-console/**")
         .and().headers().frameOptions().sameOrigin()
         .and()
         .formLogin()
         .loginPage("/productos/login")
         .permitAll()
         .successForwardUrl("/productos/private")
         .and()
         .logout()
         .permitAll()
         .logoutRequestMatcher(new AntPathRequestMatcher("/productos/logout"))
         .logoutSuccessUrl("/");
	    }	
		
	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) 
	      throws Exception {
	        auth
	          .inMemoryAuthentication()
	          .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
	    }
	    
	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
}
