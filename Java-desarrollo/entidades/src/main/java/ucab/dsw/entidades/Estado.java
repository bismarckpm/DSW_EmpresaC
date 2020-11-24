package ucab.dsw.entidades;

import ucab.dsw.dtos.DtoBase;

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
@Table( name = "estado" )
public class Estado extends EntidadBase
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

    @OneToMany( mappedBy = "_estado", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Ciudad> _ciudad;

    public List<Ciudad> get_ciudad()
    {
        return _ciudad;
    }

    public void set_ciudad( List<Ciudad> _cliente )
    {
        this._ciudad = _ciudad;
    }

    @ManyToOne
    @JoinColumn( name = "idPais" )
    private Pais _pais;

    public Pais get_pais()
    {
        return _pais;
    }

    public void set_pais( Pais _pais )
    {
        this._pais = _pais;
    }

    public Estado( long id )
    {
        super( id );
    }

    public Estado()
    {

    }
}
