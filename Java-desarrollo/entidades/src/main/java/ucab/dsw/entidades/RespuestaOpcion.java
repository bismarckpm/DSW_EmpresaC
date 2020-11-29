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
@Table( name = "Respuesta_Opcion" )
public class RespuestaOpcion extends EntidadBase{

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
    @JoinColumn( name = "Opcion_Simple_Multiple_id" )
    private OpcionSimpleMultiple _opcionsimplemultiple;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
    @JoinColumn( name = "Respuesta_id" )
    private Respuesta _respuesta;

    public Respuesta get_respuesta() {
        return _respuesta;
    }

    public void set_respuesta(Respuesta _respuesta) {
        this._respuesta = _respuesta;
    }

    public OpcionSimpleMultiple get_opcionsimplemultiple() {
        return _opcionsimplemultiple;
    }

    public void set_opcionsimplemultiple(OpcionSimpleMultiple _opcionsimplemultiple) {
        this._opcionsimplemultiple = _opcionsimplemultiple;
    }

    public RespuestaOpcion(long id )
    {
        super( id );
    }

    public RespuestaOpcion( )
    {

    }

}
