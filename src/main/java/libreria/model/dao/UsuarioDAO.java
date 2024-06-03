package libreria.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import libreria.model.entity.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
    
    // @Query("select u from Usuario u where u.nombre = ?1")
    // public Usuario buscarUsuarioPorNombre(String nombre);

    public Usuario findByNombre(String nombre);
}
