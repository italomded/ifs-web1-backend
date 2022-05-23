package br.com.ifs.projeto.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="user_profile")
@EqualsAndHashCode
public class UserAndProfile {

	@EmbeddedId
	private UserAndProfileKey id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("profileId")
	@JoinColumn(name = "profile_id")
	private Profile profile;

	@Column(nullable = false)
	private LocalDate start;
	
	@Column
	private LocalDate end;
	
	public Profile getProfile() {
		if (end == null) {
			return this.profile;
		} else {
			Profile profile = new Profile();
			profile.setName("EMPTY");
			return profile;
		}
	}
	
}
