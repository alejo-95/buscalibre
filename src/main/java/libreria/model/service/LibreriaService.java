package libreria.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import libreria.model.dao.ClienteDAO;
import libreria.model.dao.EditorialesDAO;

import libreria.model.dao.LibroDAO;
import libreria.model.dao.PrestamoDAO;
import libreria.model.entity.Cliente;
import libreria.model.entity.Editorial;

import libreria.model.entity.Libro;
import libreria.model.entity.Prestamo;

@Service
public class LibreriaService implements LibreriaServiceIface{

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private LibroDAO libroDAO;

    @Autowired
    private EditorialesDAO editorialDAO;

    // @Autowired
    // private EjemplarDAO ejemplarDAO;

    @Autowired
    private PrestamoDAO prestamoDAO;


    //servicios para cliente
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> buscarClientesTodos() {
        return clienteDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> buscarClientesTodos(Pageable pageable) {
        return clienteDAO.findAll(pageable);
    }

    @Override
    @Transactional
    public void guardarCliente(Cliente cliente) {
        clienteDAO.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarClientePorId(Long id) {
        return clienteDAO.findById(id).orElse(null);
    }

    @Override
    public void eliminarClientePorId(Long id) {
        clienteDAO.deleteById(id);
    }

    //servicios para libros
    @Override
    @Transactional(readOnly = true)
    public List<Libro> buscarLibrosTodos() {
        return libroDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Libro> buscarLibrosTodos(Pageable pageable) {
        return libroDAO.findAll(pageable);
    }

    @Override
    @Transactional
    public void guardarLibro(Libro libro) {
        libroDAO.save(libro);
    }

    @Override
    @Transactional(readOnly = true)
    public Libro buscarLibroPorId(Long id) {
        return libroDAO.findById(id).orElse(null);
    }

    @Override
    public void eliminarLibroPorId(Long id) {
        libroDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Libro> buscarLibrosPorNombre(String term) {
        
        //return libroDAO.findByTitulosLikeIgnoreCase("%"+term+"%");

        //return libroDAO.findByTituloContainingIgnoreCase(term);

        return libroDAO.findByTituloLikeIgnoreCase("%"+term+"%");

    }

    //servicios para editoriales
    @Override
    @Transactional(readOnly = true)
    public List<Editorial> buscarEditorialesTodos() {
        return editorialDAO.findAll();
    }

    //Servicios para ejemplares
    // @Override
    // @Transactional
    // public void guardarEjemplar(Ejemplar ejemplar) {
    //     ejemplarDAO.save(ejemplar);
    // }

    // @Override
    // @Transactional(readOnly = true)
    // public Ejemplar buscarEjemplarPorId(Long id) {
    //     return ejemplarDAO.findById(id).orElse(null);
    // }

    // @Override
    // @Transactional(readOnly = true)
    // public List<Ejemplar> buscarEjemplaresTodos() {
    //     return ejemplarDAO.findAll();
    // }

    // @Override
    // @Transactional(readOnly = true)
    // public Page<Ejemplar> buscarEjemplaresTodos(Pageable pageable) {
    //     return ejemplarDAO.findAll(pageable);
    // }


    // @Override
    // public void eliminarEjemplarPorId(Long id) {
    //     ejemplarDAO.deleteById(id);
    // }

    //Servicios de prestamo

    @Override
    @Transactional
    public void guardarPrestamo(Prestamo prestamo) {
        prestamoDAO.save(prestamo);
    }

    @Override
    @Transactional(readOnly = true)
    public Prestamo buscarPrestamoPorId(Long id) {
        return prestamoDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prestamo> buscarPrestamosTodos() {
        return prestamoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Prestamo> buscarPrestamosTodos(Pageable pageable) {
        return prestamoDAO.findAll(pageable);
    }


    @Override
    public void eliminarPrestamoPorId(Long id) {
        prestamoDAO.deleteById(id);
    }

    
    
}
