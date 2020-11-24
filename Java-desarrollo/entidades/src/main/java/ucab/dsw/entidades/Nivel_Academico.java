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
@Table( name = "nivel_academico" )
public class Nivel_Academico extends EntidadBase
{

    @Column( name = "nombre" )
    private String _nombre;

    public String get_nombre()
    {
        return _nombre;
    }

    @OneToMany( mappedBy = "_nivel_academico_encuestado", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Encuestado> _encuestado;

    public List<Encuestado> get_encuestado()
    {
        return _encuestado;
    }

    public void set_encuestado( List<Encuestado> _encuestado )
    {
        this._encuestado = _encuestado;
    }

    @OneToMany( mappedBy = "_nivel_academico_demografia", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Caracteristica_Demografica> _caracteristica_Demografica;

    public List<Caracteristica_Demografica> get_caracteristica_Demografica()
    {
        return _caracteristica_Demografica;
    }

    public void set_caracteristica_Demografica( List<Caracteristica_Demografica> _caracteristica_Demografica )
    {
        this._caracteristica_Demografica = _caracteristica_Demografica;
    }

    public Nivel_Academico( long id )
    {
        super( id );
    }

    public Nivel_Academico()
    {

    }
}
