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
@Table( name = "Pregunta" )
public class Pregunta extends EntidadBase{

    @Column( name = "descripcion" )
    private String _descripcion;

    @Column( name = "tipo_pregunta" )
    private String _tipopregunta;

    @Column( name = "valor_min" )
    private int _valormin;

    @Column( name = "valor_max" )
    private int _valormax;

    @OneToMany( mappedBy = "_pregunta", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Opcion_Simple_Multiple_Pregunta> _opcionsimplemultiple;

    @OneToMany( mappedBy = "_pregunta", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<PreguntaEncuesta> _preguntaencuesta;

    public List<PreguntaEncuesta> get_preguntaencuesta()
    {
        return _preguntaencuesta;
    }

    public void set_preguntaencuesta( List<PreguntaEncuesta> _preguntaencuesta )
    {
        this._preguntaencuesta = _preguntaencuesta;
    }

    public int get_valormax() {
        return _valormax;
    }

    public void set_valormax(int _valormax) {
        this._valormax = _valormax;
    }

    public int get_valormin() {
        return _valormin;
    }

    public void set_valormin(int _valormin) {
        this._valormin = _valormin;
    }

    public String get_tipopregunta() {
        return _tipopregunta;
    }

    public void set_tipopregunta(String _tipopregunta) {
        this._tipopregunta = _tipopregunta;
    }

    public String get_descripcion() {
        return _descripcion;
    }

    public void set_descripcion(String _descripcion) {
        this._descripcion = _descripcion;
    }

    public Pregunta(long id )
    {
        super( id );
    }

    public Pregunta()
    {

    }

}