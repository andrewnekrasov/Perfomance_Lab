package ru.nekrasov.spring_task.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import ru.nekrasov.spring_task.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserService userService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable().authorizeRequests().anyRequest().authenticated()
				.and().formLogin().loginPage("/login").permitAll()
				.and().rememberMe()
				.and().logout().permitAll();
	}


	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

	// @Bean
	// @Override
	// public UserDetailsService userDetailsService() {
	// UserDetails user =
	// User.withDefaultPasswordEncoder().username("u1").password("p1").roles("USER").build();
	//
	// return new InMemoryUserDetailsManager(user);
	// }
}
