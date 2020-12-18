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
@Table( name = "pais" )
public class Pais extends EntidadBase
{


    @Column( name = "nombre" )
    private String _nombre;

    public String get_nombre()
    {
        return _nombre;
    }

    public void set_nombre( String _nombre )
    {
        this._nombre = _nombre;
    }

    @Column( name = "nacionalidad" )
    private String _nacionalidad;

    public String get_nacionalidad()
    {
        return _nacionalidad;
    }

    public void set_nacionalidad( String _nacionalidad )
    {
        this._nacionalidad = _nacionalidad;
    }

    @OneToMany( mappedBy = "_pais", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Estado> _estado;

    public List<Estado> get_estado()
    {
        return _estado;
    }

    public void set_estado( List<Estado> _estado )
    {
        this._estado = _estado;
    }

    public Pais( long id )
    {
        super( id );
    }

    public Pais()
    {

    }
}
