package br.com.ifs.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ifs.projeto.model.UserAndProfile;

@Repository
public interface UserAndProfileRepository extends JpaRepository<UserAndProfile, Long> {

	@Query("SELECT up FROM user_profile AS up WHERE up.user_id.id != ?1 AND up.profile_id.id != ?2")
	public Optional<UserAndProfile> exist(Long userId, Long profileId);
	
}
