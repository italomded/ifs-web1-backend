package br.com.ifs.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ifs.projeto.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByLogin(String login);
	
	@Query("SELECT u FROM users AS u JOIN FETCH u.profiles WHERE u.id = :id")
	public Optional<User> findByIdd(Long id);
	
}
