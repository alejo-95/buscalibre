package libreria.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import libreria.model.entity.Cliente;

public interface LibreriaServiceIface {
    
    //Servicios para cliente
    public List<Cliente> buscarClientesTodos();
	public Page<Cliente> buscarClientesTodos(Pageable pageable);
	public void guardarCliente(Cliente cliente);
	public Cliente buscarClientePorId(Long id);
	public void eliminarClientePorId(Long id);
}
