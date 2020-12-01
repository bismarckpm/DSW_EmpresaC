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
@Table( name = "Opcion_Simple_Multiple" )
public class OpcionSimpleMultiple extends EntidadBase{
    @Column( name = "opcion" )
    private String _opcion;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
    @JoinColumn( name = "Pregunta_id" )
    private Pregunta _pregunta;

    @OneToMany( mappedBy = "_opcionsimplemultiple", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<RespuestaOpcion> _respuestaopcion;

    public Pregunta get_pregunta() {
        return _pregunta;
    }

    public void set_pregunta(Pregunta _pregunta) {
        this._pregunta = _pregunta;
    }

    public String get_opcion() {
        return _opcion;
    }

    public void set_opcion(String _opcion) {
        this._opcion = _opcion;
    }

    public OpcionSimpleMultiple(long id )
    {
        super( id );
    }

    public OpcionSimpleMultiple( )
    {

    }

}
