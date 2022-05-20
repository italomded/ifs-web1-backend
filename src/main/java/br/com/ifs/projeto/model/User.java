package br.com.ifs.projeto.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="users")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(length=100, nullable=false)
	private String name;

	@Column(length=50, nullable=false, unique=true)
	private String login;
	
	@Column(length=500, nullable=false)
	private String password;

	@Column(nullable = false)
	private Boolean status;
	
	@Column(nullable = false)
	private LocalDate register;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "profile_id")
	private List<UserAndProfile> profiles = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return profiles.stream().map(p -> p.getProfile_id()).collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.status;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.status;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.status;
	}

	@Override
	public boolean isEnabled() {
		return this.status;
	}

}
