package ucab.dsw.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table( name = "metodo_conexion_encuestado" )
public class Metodo_Conexion_Encuestado extends EntidadBase{


    @ManyToOne
    @JoinColumn( name = "Metodo_conexion_id" )
    private Metodo_conexion _metodo_conexion;

    public Metodo_conexion get_metodo_conexion() { return _metodo_conexion; }

    public void set_metodo_conexion( Metodo_conexion _metodo_conexion ) { this._metodo_conexion = _metodo_conexion; }

    @ManyToOne
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

    public Metodo_Conexion_Encuestado( long id ) { super( id ); }

    public Metodo_Conexion_Encuestado()
    {

    }
}
