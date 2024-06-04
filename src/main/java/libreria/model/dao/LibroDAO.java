package libreria.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import libreria.model.entity.Libro;

public interface LibroDAO extends JpaRepository<Libro, Long> {
    
}
