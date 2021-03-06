package ucab.dsw.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table( name = "Pregunta_Encuesta" )
public class PreguntaEncuesta extends EntidadBase{


    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Pregunta_id" )
    private Pregunta _pregunta_Encuesta;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Encuesta_id" )
    private Encuesta _encuesta_Pregunta;

    @OneToMany( mappedBy = "_preguntaencuesta", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Respuesta> _respuesta;

    public List<Respuesta> get_respuesta()
    {
        return _respuesta;
    }

    public void set_respuesta( List<Respuesta>  _respuesta )
    {
        this._respuesta = _respuesta;
    }

    public Encuesta get_encuesta() {
        return _encuesta_Pregunta;
    }

    public void set_encuesta(Encuesta _encuesta_Pregunta) {
        this._encuesta_Pregunta = _encuesta_Pregunta;
    }

    public Pregunta get_pregunta() {
        return _pregunta_Encuesta;
    }

    public void set_pregunta(Pregunta _pregunta_Encuesta) {
        this._pregunta_Encuesta = _pregunta_Encuesta;
    }


    public PreguntaEncuesta(long id )
    {
        super( id );
    }

    public PreguntaEncuesta( )
    {

    }

}