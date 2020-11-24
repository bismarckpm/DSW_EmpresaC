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
@Table( name = "Marca" )
public class Marca extends EntidadBase{
    @Column( name = "nombre" )
    private String _nombre;

    @ManyToOne
    @JoinColumn( name = "Subcategoria_id" )
    private Subcategoria _subcategoria;

    @OneToMany( mappedBy = "_marca", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Encuesta> _encuesta;

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }



    public Marca( long id )
    {
        super( id );
    }

    public Marca()
    {

    }
}
