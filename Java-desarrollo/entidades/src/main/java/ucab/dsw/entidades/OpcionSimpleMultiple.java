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

    @OneToMany( mappedBy = "_opcionsimplemultiple", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Opcion_Simple_Multiple_Pregunta> _opcion_Simple_Multiple_Pregunta;



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
