package libreria.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import libreria.model.entity.Autor;

public interface AutoresDAO extends JpaRepository<Autor, Long>{
    
}
