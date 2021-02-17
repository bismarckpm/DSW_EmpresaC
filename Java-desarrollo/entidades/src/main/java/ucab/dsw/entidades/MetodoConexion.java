package ucab.dsw.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table( name = "metodo_conexion" )
public class MetodoConexion extends EntidadBase{

    @Column( name = "nombre" )
    private String _nombre;

    public String get_nombre() { return _nombre; }

    public void set_nombre( String _nombre ) { this._nombre = _nombre; }

    @OneToMany( mappedBy = "_metodo_conexion", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<MetodoConexionEncuestado> _metodo_Conexion_Encuestado;

    public List<MetodoConexionEncuestado> get_metodo_Conexion_Encuestado() { return _metodo_Conexion_Encuestado; }

    public void set_metodo_Conexion_Encuestado( List<MetodoConexionEncuestado> _metodo_Conexion_Encuestado )
    {
        this._metodo_Conexion_Encuestado = _metodo_Conexion_Encuestado;
    }

    public MetodoConexion(long id ) { super( id ); }

    public MetodoConexion()
    {

    }

}
