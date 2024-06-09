package libreria.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import libreria.model.entity.Prestamo;

public interface PrestamoDAO extends JpaRepository<Prestamo, Long> {
    
}
