package ucab.dsw.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name="SubcategoriaByCategoriaId", query="select s FROM Subcategoria s where s._categoria._id=:categoria_id")
})
@Table( name = "Subcategoria" )
public class Subcategoria extends EntidadBase{
    @Column( name = "nombre" )
    private String _nombre;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
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

    @Column( name = "estado" )
    private String _estado;

    public String get_estado() {
        return _estado;
    }

    public void set_estado(String _estado) {
        this._estado = _estado;
    }

    public List<Marca> get_marca() {
        return _marca;
    }

    public void set_marca(List<Marca> _marca) {
        this._marca = _marca;
    }

    public Subcategoria( long id )
    {
        super( id );
    }

    public Subcategoria()
    {

    }


}
