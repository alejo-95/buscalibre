package libreria.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import libreria.model.entity.Libro;

public interface LibroDAO extends JpaRepository<Libro, Long> {

    public List<Libro> findByTituloLikeIgnoreCase(String term);
    
    
}
