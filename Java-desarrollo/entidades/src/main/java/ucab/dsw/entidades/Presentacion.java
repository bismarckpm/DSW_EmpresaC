package ucab.dsw.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "Presentacion" )
@NamedQueries({
        @NamedQuery(name="PresentacionesByTipo", query="select p FROM Presentacion p WHERE p._tipo_Presentacion._id=:tipo_id"),

})
public class Presentacion extends EntidadBase{

    @Column( name = "nombre" )
    private String _nombre;

    @Column( name = "estado" )
    private String _estado;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Tipo_id" )
    private Tipo _tipo_Presentacion;

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_estado() {
        return _estado;
    }

    public void set_estado(String _estado) {
        this._estado = _estado;
    }

    public Tipo get_tipo() {
        return _tipo_Presentacion;
    }

    public void set_tipo(Tipo _tipo_Presentacion) {
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