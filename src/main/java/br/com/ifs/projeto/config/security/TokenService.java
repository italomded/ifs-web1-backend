package br.com.ifs.projeto.config.security;


import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

import br.com.ifs.projeto.model.User;

@Service
public class TokenService {
	
	private final Long expiration = 3600000L;
	private final String secret = "afojsapgjsaopgsapgsoapjgposjaopgas";
	private final String issuer = "Projeto API";
	private final Algorithm algorithm = Algorithm.HMAC256(this.secret);
	private final JWTVerifier verifier = JWT.require(this.algorithm).withIssuer(this.issuer).build();
	
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
