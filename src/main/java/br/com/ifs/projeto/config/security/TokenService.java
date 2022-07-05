package br.com.ifs.projeto.config.security;


import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

import br.com.ifs.projeto.model.User;

@Service
@PropertySource(value = {"classpath:application.properties"})
public class TokenService {
	
	@Value( "${projeto.jwt.secret}" )
	private String secret;
	
	@Value( "${projeto.jwt.expiration}" )
	private Long expiration;
	
	private final String issuer = "Projeto API";
	private Algorithm algorithm;
	private JWTVerifier verifier;
	
	@PostConstruct
	public void addValue() {
		System.out.println(secret);
		this.algorithm = Algorithm.HMAC256(this.secret);
		this.verifier = JWT.require(this.algorithm).withIssuer(this.issuer).build();
	}
	
	public String generateToken(Authentication authentication) {
		User logged = (User) authentication.getPrincipal();
		Date now = new Date();
		Date expirationDate = new Date(now.getTime() + this.expiration);
		return JWT.create()
				.withIssuer(this.issuer)
				.withSubject(logged.getLogin())
				.withIssuedAt(now)
				.withExpiresAt(expirationDate)
				.sign(this.algorithm);
	}

	public Boolean isTokenValid(String token) {
		try {
			this.verifier.verify(token);
			return true;
		} catch (JWTVerificationException exception) {
			return false;
		}
	}
	
	public String getUserLogin(String token) {
		String userLogin = this.verifier.verify(token).getSubject();
		return userLogin;
	}
	
}
