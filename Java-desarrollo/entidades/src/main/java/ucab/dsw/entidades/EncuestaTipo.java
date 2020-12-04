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
@Table( name = "Encuesta_Tipo" )
public class EncuestaTipo extends EntidadBase{

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
    @JoinColumn( name = "Tipo_id" )
    private Tipo _tipo_encuesta;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
    @JoinColumn( name = "Encuesta_id" )
    private Encuesta _encuesta_Tipo;

    public Encuesta get_encuesta() {
        return _encuesta_Tipo;
    }

    public void set_encuesta(Encuesta _encuesta) {
        this._encuesta_Tipo = _encuesta_Tipo;
    }

    public Tipo get_tipo() {
        return _tipo_encuesta;
    }

    public void set_tipo(Tipo _tipo_encuesta) {
        this._tipo_encuesta = _tipo_encuesta;
    }

    public EncuestaTipo(long id )
    {
        super( id );
    }

    public EncuestaTipo()
    {

    }

}