package libreria.model.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import libreria.model.dao.UsuarioDAO;
import libreria.model.entity.Rol;
import libreria.model.entity.Usuario;

@Service
public class UsuarioDetailService implements UserDetailsService{

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario usuario = usuarioDAO.findByNombre(nombre);
        
        if(usuario == null){
            log.error("*** Error de autenticación, el usuario '" + nombre+ "' no existe.");
            throw new UsernameNotFoundException("*** Error de autenticación, el usuario '"+nombre+"' no existe.");
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        for(Rol rol : usuario.getRoles()){
            log.info("Rol: "+rol.getNombre());
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }

        if(roles.isEmpty()){
            log.warn("*** El usuario '" + usuario.getNombre() + "' no tiene los reoles asignados");
            throw new UsernameNotFoundException("*** El usuario '"+ usuario.getNombre() + "' no tiene roles asigandos");
        }

        return new User(usuario.getNombre(), usuario.getClave(), usuario.isActivo(), true, true, true ,roles);
    }
    
}
