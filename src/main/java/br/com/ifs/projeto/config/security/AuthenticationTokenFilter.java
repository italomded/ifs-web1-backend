package br.com.ifs.projeto.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.ifs.projeto.model.User;
import br.com.ifs.projeto.repository.UserRepository;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UserRepository userRepository;
	
	public AuthenticationTokenFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
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
		}
		
		if (valid) {
			this.authUser(token);
		}
		
		this.generateLog(request, token);
		
		filterChain.doFilter(request, response);;
		
	}
	
	private void generateLog(HttpServletRequest request, String token) {
		// IMPLEMENTAR
		return;
	}
	
	private void authUser(String token) {
		String userLogin = tokenService.getUserLogin(token);
		Optional<User> optUser = userRepository.findByLogin(userLogin);
		if (optUser.get() == null || (!optUser.get().getStatus()) ) {
			return;
		}
		User user = optUser.get();
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
