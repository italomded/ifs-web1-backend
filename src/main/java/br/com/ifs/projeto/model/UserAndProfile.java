package br.com.ifs.projeto.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name="user_profile")
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
	
	public void setUserAndProfileId(Long profileId, Long userId) {
		id = new UserAndProfileKey();
		id.setProfileId(profileId);
		id.setUserId(userId);
	}
	
	public Profile getProfile() {
		if (end == null) {
			return this.profile;
		} else {
			Profile profile = new Profile();
			profile.setName("EMPTY");
			profile.setStatus(false);
			profile.setId(this.profile.getId());
			return profile;
		}
	}
	
}
