package ucab.dsw.entidades;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "Respuesta_Opcion" )
public class RespuestaOpcion extends EntidadBase{

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Opcion_Simple_Multiple_Pregunta_id" )
    private OpcionSimpleMultiplePregunta _opcionsimplemultiple_pregunta;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Respuesta_id" )
    private Respuesta _respuesta;

    public Respuesta get_respuesta() {
        return _respuesta;
    }

    public void set_respuesta(Respuesta _respuesta) {
        this._respuesta = _respuesta;
    }

    public OpcionSimpleMultiplePregunta get_opcionsimplemultiple() {
        return _opcionsimplemultiple_pregunta;
    }

    public void set_opcionsimplemultiple(OpcionSimpleMultiplePregunta _opcionsimplemultiple_pregunta) {
        this._opcionsimplemultiple_pregunta = _opcionsimplemultiple_pregunta;
    }

    public RespuestaOpcion(long id )
    {
        super( id );
    }

    public RespuestaOpcion( )
    {

    }

}
