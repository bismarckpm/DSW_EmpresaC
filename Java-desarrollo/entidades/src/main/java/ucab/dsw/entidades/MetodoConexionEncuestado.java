package ucab.dsw.entidades;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table( name = "metodo_conexion_encuestado" )
public class MetodoConexionEncuestado extends EntidadBase{


    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Metodo_conexion_id" )
    private MetodoConexion _metodo_conexion;

    public MetodoConexion get_metodo_conexion() { return _metodo_conexion; }

    public void set_metodo_conexion( MetodoConexion _metodo_conexion ) { this._metodo_conexion = _metodo_conexion; }

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Encuestado_id" )
    private Encuestado _encuestado_metodo_conexion;

    public Encuestado get_encuestado_metodo_conexion()
    {
        return _encuestado_metodo_conexion;
    }

    public void set_encuestado_metodo_conexion( Encuestado _encuestado_metodo_conexion )
    {
        this._encuestado_metodo_conexion = _encuestado_metodo_conexion;
    }

    public MetodoConexionEncuestado(long id ) { super( id ); }

    public MetodoConexionEncuestado()
    {

    }
}
