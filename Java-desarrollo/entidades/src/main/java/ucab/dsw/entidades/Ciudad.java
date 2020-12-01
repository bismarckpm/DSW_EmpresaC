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
@Table( name = "ciudad" )
public class Ciudad extends EntidadBase
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

    @OneToMany( mappedBy = "_ciudad", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Parroquia> _parroquia;

    public List<Parroquia> get_parroquia()
    {
        return _parroquia;
    }

    public void set_parroquia( List<Parroquia> _parroquia )
    {
        this._parroquia = _parroquia;
    }

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
    @JoinColumn( name = "Estado_id" )
    private Estado _estado;

    public Estado get_estado()
    {
        return _estado;
    }

    public void set_pais( Estado _estado )
    {
        this._estado = _estado;
    }

    public Ciudad( long id )
    {
        super( id );
    }

    public Ciudad()
    {

    }
}
