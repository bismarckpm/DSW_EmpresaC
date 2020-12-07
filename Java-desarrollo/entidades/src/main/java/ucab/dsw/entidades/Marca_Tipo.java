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
public class Marca_Tipo extends EntidadBase{

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

    public Marca_Tipo(long id )
    {
        super( id );
    }

    public Marca_Tipo()
    {

    }

}