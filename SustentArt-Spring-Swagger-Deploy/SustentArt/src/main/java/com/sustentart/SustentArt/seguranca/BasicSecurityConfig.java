package com.sustentart.SustentArt.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity 
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter 
{
	
	@Autowired
	private UserDetailsService userDetailsService; //É um objeto da classe WebSecurityConfigurerAdapter.
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService);	
	}
	
	@Bean
	public PasswordEncoder  passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Override //libera alguns caminhos do controller para que o cliente tenha acesso sem usar um token.
	protected void  configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
		.antMatchers("/usuarios/login").permitAll()
		.antMatchers("/usuarios/cadastrar").permitAll()  
		.anyRequest().authenticated()
		.and().httpBasic()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().cors()
		.and().csrf().disable();
	}

}
