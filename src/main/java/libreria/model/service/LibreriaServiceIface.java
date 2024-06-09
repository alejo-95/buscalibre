package libreria.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import libreria.model.entity.Cliente;
import libreria.model.entity.Editorial;
import libreria.model.entity.Libro;
import libreria.model.entity.Prestamo;

public interface LibreriaServiceIface {
    
    //Servicios para cliente
    public List<Cliente> buscarClientesTodos();
	public Page<Cliente> buscarClientesTodos(Pageable pageable);
	public void guardarCliente(Cliente cliente);
	public Cliente buscarClientePorId(Long id);
	public void eliminarClientePorId(Long id);

	//servicios para libro
	public List<Libro> buscarLibrosTodos();
	public Page<Libro> buscarLibrosTodos(Pageable pageable);
	public void guardarLibro(Libro libro);
	public Libro buscarLibroPorId(Long id);
	public void eliminarLibroPorId(Long id);
	public List<Libro> buscarLibrosPorNombre(String term);

	//servicios para editorial
	public List<Editorial> buscarEditorialesTodos();

	//servicios para ejemplar
	// public  void guardarEjemplar(Ejemplar ejemplar);
	// public Ejemplar buscarEjemplarPorId(Long id);
	// public List<Ejemplar> buscarEjemplaresTodos();
	// public Page<Ejemplar> buscarEjemplaresTodos(Pageable pageable);
	// public void eliminarEjemplarPorId(Long id);

	//servicios para prestamo
	public  void guardarPrestamo(Prestamo prestamo);
	public Prestamo buscarPrestamoPorId(Long id);
	public List<Prestamo> buscarPrestamosTodos();
	public Page<Prestamo> buscarPrestamosTodos(Pageable pageable);
	public void eliminarPrestamoPorId(Long id);
	
}
