package br.com.ifs.projeto.config.security;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.ifs.projeto.config.LogFilter;
import br.com.ifs.projeto.repository.LogRepository;
import br.com.ifs.projeto.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserAuthenticationService userAuthService;
	
	@Autowired
	private LogRepository logRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userAuthService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.headers().frameOptions().disable() // h2
			.and()
				.authorizeRequests()
					// public
					.antMatchers(HttpMethod.POST, "/user").permitAll()
					.antMatchers("/authentication").permitAll()
					.antMatchers("/h2-console/**").permitAll()
					// adm role
					.antMatchers(HttpMethod.GET, "/user").hasRole("ADM")
					.antMatchers("/log").hasRole("ADM")
					.antMatchers(HttpMethod.POST).hasRole("ADM")
					.antMatchers(HttpMethod.DELETE).hasRole("ADM")
					.antMatchers(HttpMethod.PUT).hasRole("ADM")
					// springdoc
					.antMatchers("/api-docs/**").permitAll()
					// logged
					.anyRequest().authenticated()
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.addFilterBefore(
						new AuthenticationTokenFilter(tokenService, userRepository), 
						UsernamePasswordAuthenticationFilter.class
						)
				.addFilterAfter(new LogFilter(logRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	
//	public static void main(String[] args) {
//		BCryptPasswordEncoder bp = new BCryptPasswordEncoder();
//		System.out.println(bp.encode("12345"));
//	}

}
