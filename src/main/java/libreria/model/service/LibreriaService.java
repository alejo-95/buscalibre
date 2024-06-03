package libreria.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import libreria.model.dao.ClienteDAO;
import libreria.model.entity.Cliente;

@Service
public class LibreriaService implements LibreriaServiceIface{

    @Autowired
    private ClienteDAO clienteDAO;


    //servicios para cliente
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> buscarClientesTodos() {
        return clienteDAO.findAll();
    }

    @Override
    public Page<Cliente> buscarClientesTodos(Pageable pageable) {
        return clienteDAO.findAll(pageable);
    }

    @Override
    @Transactional
    public void guardarCliente(Cliente cliente) {
        clienteDAO.save(cliente);
    }

    @Override
    public Cliente buscarClientePorId(Long id) {
        return clienteDAO.findById(id).orElse(null);
    }

    @Override
    public void eliminarClientePorId(Long id) {
        clienteDAO.deleteById(id);
    }

    
    
}
