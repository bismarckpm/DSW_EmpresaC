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
@Table( name = "parroquia" )
public class Parroquia extends EntidadBase
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

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
    @JoinColumn( name = "Ciudad_id" )
    private Ciudad _ciudad;

    public Ciudad get_ciudad()
    {
        return _ciudad;
    }

    public void set_ciudad( Ciudad _ciudad )
    {
        this._ciudad = _ciudad;
    }

    @OneToMany( mappedBy = "_Parroquia_encuestado", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Encuestado> _encuestado;

    public List<Encuestado> get_encuestado()
    {
        return _encuestado;
    }

    @OneToMany( mappedBy = "_Parroquia_demografia", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Caracteristica_Demografica> _caracteristica_Demografica;

    public List<Caracteristica_Demografica> get_caracteristica_Demografica()
    {
        return _caracteristica_Demografica;
    }

    public void set_caracteristica_Demografica( List<Caracteristica_Demografica> _caracteristica_Demografica )
    {
        this._caracteristica_Demografica = _caracteristica_Demografica;
    }

    public Parroquia( long id )
    {
        super( id );
    }

    public Parroquia()
    {

    }
}
