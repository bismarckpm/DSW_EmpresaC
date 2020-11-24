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
@Table( name = "Pregunta_Encuesta" )
public class PreguntaEncuesta extends EntidadBase{
    @Column( name = "nombre" )
    private String _nombre;

    @ManyToOne
    @JoinColumn( name = "Pregunta_id" )
    private Pregunta _pregunta;

    @ManyToOne
    @JoinColumn( name = "Encuesta_id" )
    private Encuesta _encuesta;

    @OneToMany( mappedBy = "_preguntaencuesta", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Respuesta> _respuesta;

    public Encuesta get_encuesta() {
        return _encuesta;
    }

    public void set_encuesta(Encuesta _encuesta) {
        this._encuesta = _encuesta;
    }

    public Pregunta get_pregunta() {
        return _pregunta;
    }

    public void set_pregunta(Pregunta _pregunta) {
        this._pregunta = _pregunta;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public PreguntaEncuesta(long id )
    {
        super( id );
    }

    public PreguntaEncuesta( )
    {

    }

}
