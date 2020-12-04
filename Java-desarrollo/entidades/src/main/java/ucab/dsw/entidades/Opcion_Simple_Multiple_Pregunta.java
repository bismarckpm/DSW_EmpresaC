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
@Table( name = "Opcion_Simple_Multiple_Pregunta" )
public class Opcion_Simple_Multiple_Pregunta extends EntidadBase{

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
    @JoinColumn( name = "Pregunta_id" )
    private Pregunta _pregunta;

    public Pregunta get_pregunta() {
        return _pregunta;
    }

    public void set_pregunta(Pregunta _pregunta) {
        this._pregunta = _pregunta;
    }

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
    @JoinColumn( name = "Opcion_Silple_Multiple_id" )
    private OpcionSimpleMultiple _opcionsimplemultiple;

    public OpcionSimpleMultiple get_opcionsimplemultiple() {
        return _opcionsimplemultiple;
    }

    public void set_opcion_Simple_Multiple_Pregunta(OpcionSimpleMultiple _opcionsimplemultiple) {
        this._opcionsimplemultiple = _opcionsimplemultiple;
    }



    @OneToMany( mappedBy = "_opcionsimplemultiple_pregunta", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<RespuestaOpcion> _respuestaopcion;


}
