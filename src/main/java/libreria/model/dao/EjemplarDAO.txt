package libreria.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import libreria.model.entity.Ejemplar;

public interface EjemplarDAO extends JpaRepository<Ejemplar, Long> {
    
}
