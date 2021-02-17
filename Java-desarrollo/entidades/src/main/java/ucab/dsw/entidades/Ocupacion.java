package ucab.dsw.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table( name = "ocupacion" )
public class Ocupacion extends EntidadBase{



    @Column( name = "nombre" )
    private String _nombre;

    public String get_nombre() { return _nombre; }

    public void set_nombre( String _nombre ) { this._nombre = _nombre; }

    @OneToMany( mappedBy = "_ocupacion", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<OcupacionEncuestado> _ocupacion_Encuestado;

    public List<OcupacionEncuestado> get_ocupacion_Encuestado() { return _ocupacion_Encuestado; }

    public void set_ocupacion_Encuestado( List<OcupacionEncuestado> _ocupacion_Encuestado )
    {
        this._ocupacion_Encuestado = _ocupacion_Encuestado;
    }

    public Ocupacion( long id ) { super( id ); }

    public Ocupacion()
    {

    }
}
