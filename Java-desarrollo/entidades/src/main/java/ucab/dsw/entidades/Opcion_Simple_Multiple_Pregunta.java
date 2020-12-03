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
    private OpcionSimpleMultiple _opcion_Simple_Multiple_Pregunta;

    public OpcionSimpleMultiple get_opcion_Simple_Multiple_Pregunta() {
        return _opcion_Simple_Multiple_Pregunta;
    }

    public void set_opcion_Simple_Multiple_Pregunta(OpcionSimpleMultiple _opcion_Simple_Multiple_Pregunta) {
        this._opcion_Simple_Multiple_Pregunta = _opcion_Simple_Multiple_Pregunta;
    }



    @OneToMany( mappedBy = "_opcionsimplemultiple", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<RespuestaOpcion> _respuestaopcion;


}
