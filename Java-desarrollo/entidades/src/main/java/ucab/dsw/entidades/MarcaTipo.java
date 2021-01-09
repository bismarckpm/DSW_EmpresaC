package ucab.dsw.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "Marca_Tipo" )
@NamedQueries({
        @NamedQuery(name="MarcaTiposByMarca", query="select mt FROM MarcaTipo mt WHERE mt._marca._id=:marca_id"),

})
public class MarcaTipo extends EntidadBase{

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Tipo_id" )
    private Tipo _tipo;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Marca_id" )
    private Marca _marca;

    public Marca get_marca() {
        return _marca;
    }

    public void set_marca(Marca _marca) {
        this._marca = _marca;
    }

    public Tipo get_tipo() {
        return _tipo;
    }

    public void set_tipo(Tipo _tipo) {
        this._tipo = _tipo;
    }

    public MarcaTipo(long id )
    {
        super( id );
    }

    public MarcaTipo()
    {

    }

}