package libreria.model.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="editoriales")
public class Editorial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String direccion;

    @Column( name = "nombre_completo", nullable = false, columnDefinition = "varchar(255) default ''")
    private String nombreCompleto;

    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String pais;

    @Column(nullable = false, columnDefinition = "varchar(255) default ''")
    private String url;

    @OneToMany(mappedBy = "editorial", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Libro> libros;

    public Editorial() {
    }

    public Editorial(Long id, String direccion, String nombreCompleto, String pais, String url, List<Libro> libros) {
        this.id = id;
        this.direccion = direccion;
        this.nombreCompleto = nombreCompleto;
        this.pais = pais;
        this.url = url;
        this.libros = libros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    
    

}
