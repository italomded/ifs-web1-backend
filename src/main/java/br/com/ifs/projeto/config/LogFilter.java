package br.com.ifs.projeto.config;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.ifs.projeto.model.Log;
import br.com.ifs.projeto.model.User;
import br.com.ifs.projeto.repository.LogRepository;

public class LogFilter extends OncePerRequestFilter {

	private LogRepository logRepository;
	
	public LogFilter(LogRepository logRepository) {
		this.logRepository = logRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String URI = request.getRequestURI();
		if (URI.contains("/h2-console") || URI.contains("/favicon.ico")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = null;
		if (auth != null) {
			user = (User) auth.getPrincipal();
		}
		
		Log log = new Log();;
		log.setDate(LocalDate.now());
		if (user != null) {
			log.setUser_id(user.getId());
		} else {
			log.setUser_id(null);
		}
		
		log.setText(request.getRequestURI() + " METHOD: " + request.getMethod());
		logRepository.save(log);
		filterChain.doFilter(request, response);
		
	}

}
