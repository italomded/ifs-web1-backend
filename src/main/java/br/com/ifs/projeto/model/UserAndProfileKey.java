package br.com.ifs.projeto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class UserAndProfileKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="user_id")
	private Long userId;
	
	@Column(name="profile_id")
	private Long profileId;
	
}
