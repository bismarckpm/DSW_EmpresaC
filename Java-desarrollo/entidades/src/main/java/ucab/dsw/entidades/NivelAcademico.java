package ucab.dsw.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Entity
@Table( name = "nivel_academico" )
public class NivelAcademico extends EntidadBase
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
    private List<CaracteristicaDemografica> _caracteristica_Demografica;

    public List<CaracteristicaDemografica> get_caracteristica_Demografica()
    {
        return _caracteristica_Demografica;
    }

    public void set_caracteristica_Demografica( List<CaracteristicaDemografica> _caracteristica_Demografica )
    {
        this._caracteristica_Demografica = _caracteristica_Demografica;
    }

    public NivelAcademico(long id )
    {
        super( id );
    }

    public NivelAcademico()
    {

    }
}
