package br.com.ifs.projeto.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="profiles")
public class Profile implements GrantedAuthority, Model {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(length=100, nullable=false)
	private String name;

	@Column(nullable = false)
	private Boolean status;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "profile", cascade = CascadeType.ALL)
	private Set<UserAndProfile> users = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			  name = "profile_transaction", 
			  joinColumns = @JoinColumn(name = "profile_id"), 
			  inverseJoinColumns = @JoinColumn(name = "transaction_id"))
	private Set<Transaction> transactions = new HashSet<>();

	@Override
	public String getAuthority() {
		if (status) {
			return this.name;
		} else {
			return null;
		}
	}
	
}
