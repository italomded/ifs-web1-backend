package br.com.ifs.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ifs.projeto.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
