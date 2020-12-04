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
@Table( name = "Presentacion" )
public class Presentacion extends EntidadBase{

    @Column( name = "nombre" )
    private String _nombre;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Tipo_id" )
    private Tipo _tipo_Presentacion;

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public Tipo get_tipo() {
        return _tipo_Presentacion;
    }

    public void set_tipo(Tipo _tipo) {
        this._tipo_Presentacion = _tipo_Presentacion;
    }

    public Presentacion(long id )
    {
        super( id );
    }

    public Presentacion()
    {

    }


}