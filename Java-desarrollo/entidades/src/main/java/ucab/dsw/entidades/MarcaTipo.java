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
@Table( name = "Marca_Tipo" )
public class MarcaTipo extends EntidadBase{

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Tipo_id" )
    private Tipo _tipo_encuesta;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Marca_id" )
    private Marca _marca_Tipo;

    public Marca get_marca() {
        return _marca_Tipo;
    }

    public void set_marca(Marca _marca_Tipo) {
        this._marca_Tipo = _marca_Tipo;
    }

    public Tipo get_tipo() {
        return _tipo_encuesta;
    }

    public void set_tipo(Tipo _tipo_encuesta) {
        this._tipo_encuesta = _tipo_encuesta;
    }

    public MarcaTipo(long id )
    {
        super( id );
    }

    public MarcaTipo()
    {

    }

}