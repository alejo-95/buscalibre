package libreria.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import libreria.model.entity.Editorial;

public interface EditorialesDAO extends JpaRepository<Editorial, Long>{
    
}
