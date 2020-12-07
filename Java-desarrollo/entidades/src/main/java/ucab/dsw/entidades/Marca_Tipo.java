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
    private Tipo _tipo_marca;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Marca_id" )
    private Marca _Marca_Tipo;

    public Marca get_Marca_Tipo() {
        return _Marca_Tipo;
    }

    public void set_Marca_Tipo(Marca _Marca_Tipo) {
        this._Marca_Tipo = _Marca_Tipo;
    }

    public Tipo get_tipo() {
        return _tipo_marca;
    }

    public void set_tipo(Tipo _tipo_marca) {
        this._tipo_marca = _tipo_marca;
    }

    public Marca_Tipo(long id )
    {
        super( id );
    }

    public Marca_Tipo()
    {

    }

}