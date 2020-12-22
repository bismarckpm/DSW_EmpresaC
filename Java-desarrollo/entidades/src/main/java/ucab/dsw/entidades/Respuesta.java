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
@Table( name = "Respuesta" )
public class Respuesta extends EntidadBase{
    @Column( name = "Respuesta_Booleana" )
    private int _respuestaboolean;

    @Column( name = "Respuesta_Desarrollo" )
    private String _respuestadesarrollo;

    @Column( name = "Respuesta_rango" )
    private int _respuestarango;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Pregunta_encuesta_id" )
    private PreguntaEncuesta _preguntaencuesta;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Participacion_id" )
    private Participacion _participacion;

    @OneToMany( mappedBy = "_respuesta", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<RespuestaOpcion> _respuestaopcion;

    public Participacion get_participacion() {
        return _participacion;
    }

    public void set_participacion(Participacion _participacion) {
        this._participacion = _participacion;
    }

    public PreguntaEncuesta get_preguntaencuesta() {
        return _preguntaencuesta;
    }

    public void set_preguntaencuesta(PreguntaEncuesta _preguntaencuesta) {
        this._preguntaencuesta = _preguntaencuesta;
    }

    public int get_respuestarango() {
        return _respuestarango;
    }

    public void set_respuestarango(int _respuestarango) {
        this._respuestarango = _respuestarango;
    }

    public String get_respuestadesarrollo() {
        return _respuestadesarrollo;
    }

    public void set_respuestadesarrollo(String _respuestadesarrollo) {
        this._respuestadesarrollo = _respuestadesarrollo;
    }

    public int get_respuestaboolean() {
        return _respuestaboolean;
    }

    public void set_respuestaboolean(int _respuestaboolean) {
        this._respuestaboolean = _respuestaboolean;
    }

    public Respuesta(long id )
    {
        super( id );
    }

    public Respuesta( )
    {

    }

}
