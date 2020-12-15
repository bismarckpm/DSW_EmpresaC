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
@Table( name = "ocupacion_encuestado" )
public class Ocupacion_Encuestado extends EntidadBase{

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Ocupacion_id" )
    private Ocupacion _ocupacion;

    public Ocupacion get_ocupacion() { return _ocupacion; }

    public void set_ocupacion( Ocupacion _ocupacion ) { this._ocupacion = _ocupacion; }

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Encuestado_id" )
    private Encuestado _encuestado_ocupacion;

    public Encuestado get_encuestado_ocupacion()
    {
        return _encuestado_ocupacion;
    }

    public void set_encuestado_ocupacion( Encuestado _encuestado_ocupacion )
    {
        this._encuestado_ocupacion = _encuestado_ocupacion;
    }

    public Ocupacion_Encuestado( long id ) { super( id ); }

    public Ocupacion_Encuestado()
    {

    }
}
