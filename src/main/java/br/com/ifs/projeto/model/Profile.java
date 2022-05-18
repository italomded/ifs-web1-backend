package br.com.ifs.projeto.model;

import java.util.ArrayList;
import java.util.List;

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
public class Profile implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(length=100, nullable=false)
	private String name;

	@Column(nullable = false)
	private Boolean status;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user_id")
	private List<UserAndProfile> users;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			  name = "profile_transaction", 
			  joinColumns = @JoinColumn(name = "profile_id"), 
			  inverseJoinColumns = @JoinColumn(name = "transaction_id"))
	private List<Transaction> transactions = new ArrayList<>();

	@Override
	public String getAuthority() {
		return this.name;
	}
	
}
