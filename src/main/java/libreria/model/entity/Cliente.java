package libreria.model.entity;

import java.util.Date;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;  

    @Column(nullable = false, length = 100)
    @NotEmpty
    private String apellidos;

    @Column(nullable = false, length = 100)
    @NotEmpty
    private String nombres;

    @Column(nullable = false, length = 100)
    @NotEmpty
    private String identificacion;

    @Column(name = "correo_electronico", length = 100)
    @NotEmpty
    @Email
    private String correo_electronico;


    @Column(name = "fecha_ingreso")
	@Temporal(TemporalType.DATE)
    private Date fechaIngreso;

    @NotEmpty
    private String telefono;
    
    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String foto;

    @Column
    private boolean activo;
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Prestamo> prestamos;

    public Cliente() {
    }

    public Cliente(Long id, @NotEmpty String apellidos, @NotEmpty String nombres, @NotEmpty String identificacion,
            @NotEmpty @Email String correo_electronico, @NotNull Date fechaIngreso, @NotEmpty String telefono,
            String foto, boolean activo, List<Prestamo> prestamos) {
        Id = id;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.identificacion = identificacion;
        this.correo_electronico = correo_electronico;
        this.fechaIngreso = fechaIngreso;
        this.telefono = telefono;
        this.foto = foto;
        this.activo = activo;
        this.prestamos = prestamos;
    }

    @PrePersist
    protected void onCreate() {
        this.fechaIngreso = new Date();
        this.activo = true; 
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo_electronico() {
        return correo_electronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    

        
    
}
