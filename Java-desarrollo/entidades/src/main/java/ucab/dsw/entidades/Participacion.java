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
@Table( name = "Participacion" )
public class Participacion extends EntidadBase{

    @Column( name = "estado" )
    private String _estado;



    @OneToMany( mappedBy = "_participacion", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Respuesta> _respuesta;

}
