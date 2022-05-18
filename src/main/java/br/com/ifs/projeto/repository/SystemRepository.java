package br.com.ifs.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ifs.projeto.model.ModelSystem;

@Repository
public interface SystemRepository extends JpaRepository<ModelSystem, Long>  {

}
