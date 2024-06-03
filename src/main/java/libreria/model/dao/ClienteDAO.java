package libreria.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import libreria.model.entity.Cliente;

public interface ClienteDAO extends JpaRepository<Cliente, Long> {
    
}
