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
@Table( name = "caracteristica_demografica" )
public class CaracteristicaDemografica extends EntidadBase{

    @Column( name = "edad_min" )
    private int _edad_min;

    public int get_edad_min()
    {
        return _edad_min;
    }

    public void set_edad_min( int _edad_min ) { this._edad_min = _edad_min; }

    @Column( name = "edad_max" )
    private int _edad_max;

    public int get_edad_max()
    {
        return _edad_max;
    }

    public void set_edad_max( int _edad_max ) { this._edad_max = _edad_max; }

    @Column( name = "nivel_socioeconomico" )
    private String _nivel_socioeconomico;

    public String get_nivel_socioeconomico()
    {
        return _nivel_socioeconomico;
    }

    public void set_nivel_socioeconomico( String _nivel_socioeconomico )
    {
        this._nivel_socioeconomico = _nivel_socioeconomico;
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

    @Column( name = "cantidad_hijos" )
    private int _cantidad_hijos;

    public int get_cantidad_hijos()
    {
        return _cantidad_hijos;
    }

    public void set_cantidad_hijos( int _cantidad_hijos ) { this._cantidad_hijos = _cantidad_hijos; }

    @Column( name = "genero" )
    private String _genero;

    public String get_genero() { return _genero; }

    public void set_genero( String _genero ) { this._genero = _genero; }

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Parroquia_id" )
    private Parroquia _Parroquia_demografia;

    public Parroquia get_Parroquia_demografia()
    {
        return _Parroquia_demografia;
    }

    public void set_Parroquia_demografia( Parroquia _Parroquia_demografia )
    {
        this._Parroquia_demografia = _Parroquia_demografia;
    }

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Nivel_academico_id" )
    private NivelAcademico _nivel_academico_demografia;

    public NivelAcademico get_nivel_academico_demografia()
    {
        return _nivel_academico_demografia;
    }

    public void set_nivel_academico_demografia( NivelAcademico _nivel_academico_demografia )
    {
        this._nivel_academico_demografia = _nivel_academico_demografia;
    }

    @OneToMany( mappedBy = "_caracteristicademografica", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<SolicitudEstudio> _solicitudestudio;

    public CaracteristicaDemografica(long id )
    {
        super( id );
    }

    public CaracteristicaDemografica()
    {

    }
}
