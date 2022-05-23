package br.com.ifs.projeto.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ifs.projeto.model.UserAndProfile;
import br.com.ifs.projeto.model.UserAndProfileKey;

@Repository
public interface UserAndProfileRepository extends JpaRepository<UserAndProfile, UserAndProfileKey> {

	@Query("SELECT up FROM user_profile AS up WHERE up.user.id = ?1 AND up.profile.id = ?2")
	public Optional<UserAndProfile> exist(Long userId, Long profileId);

	public Set<UserAndProfile> findByUser_Id(Long id);
	
	
	//public Boolean deleteByIds(Long userId, Long profileId);
	
}
