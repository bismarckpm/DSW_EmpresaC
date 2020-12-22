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
@Table( name = "Categoria" )
public class Categoria extends EntidadBase{

    @Column( name = "nombre" )
    private String _nombre;

    @OneToMany( mappedBy = "_categoria", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Subcategoria> _subcategoria;

    @Column( name = "estado" )
    private String _estado;

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public List<Subcategoria> get_subcategoria() {
        return _subcategoria;
    }

    public void set_subcategoria(List<Subcategoria> _subcategoria) {
        this._subcategoria = _subcategoria;
    }

    public String get_estado() {
        return _estado;
    }

    public void set_estado(String _estado) {
        this._estado = _estado;
    }

    public Categoria(long id )
    {
        super( id );
    }

    public Categoria()
    {

    }

}