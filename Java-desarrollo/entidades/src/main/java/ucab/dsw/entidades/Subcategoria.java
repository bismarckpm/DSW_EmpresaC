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
@Table( name = "Subcategoria" )
public class Subcategoria extends EntidadBase{
    @Column( name = "nombre" )
    private String _nombre;

    @ManyToOne()
    @JoinColumn( name = "Categoria_id" )
    private Categoria _categoria;

    @OneToMany( mappedBy = "_subcategoria", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Marca> _marca;

    public Categoria get_categoria() {
        return _categoria;
    }

    public void set_categoria(Categoria _categoria) {
        this._categoria = _categoria;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }


    public Subcategoria( long id )
    {
        super( id );
    }

    public Subcategoria()
    {

    }
}
