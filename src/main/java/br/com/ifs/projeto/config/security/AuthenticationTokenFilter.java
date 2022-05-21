package br.com.ifs.projeto.config.security;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.ifs.projeto.model.Log;
import br.com.ifs.projeto.model.User;
import br.com.ifs.projeto.repository.LogRepository;
import br.com.ifs.projeto.repository.UserRepository;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UserRepository userRepository;
	private LogRepository logRepository;
	
	public AuthenticationTokenFilter(TokenService tokenService, UserRepository userRepository, LogRepository logRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
		this.logRepository = logRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = this.retrieveToken(request);
		Boolean valid;
		
		if (token != null) {
			valid = tokenService.isTokenValid(token);
		} else {
			valid = false;
			this.generateLog(request, null);
		}
		
		if (valid) {
			String userLogin = tokenService.getUserLogin(token);
			Optional<User> optUser = userRepository.findByLogin(userLogin);
			if (optUser.get() == null || (!optUser.get().getStatus()) ) {
				return;
			}
			User user = optUser.get();
			this.authUser(user);
			this.generateLog(request, user);
		}
		
		filterChain.doFilter(request, response);;
		
	}
	
	private void generateLog(HttpServletRequest request, User user) {
		Log log = new Log();;
		log.setDate(LocalDate.now());
		if (user != null) {
			log.setUser_id(user.getId());
		} else {
			log.setUser_id(null);
		}
		log.setText(request.getRequestURI() + " METHOD: " + request.getMethod());
		logRepository.save(log);
	}
	
	private void authUser(User user) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	private String retrieveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		} else {
			return token.substring(7, token.length());
		}
	}
	
}
