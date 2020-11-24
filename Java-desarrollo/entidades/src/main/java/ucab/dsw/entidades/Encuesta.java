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
@Table( name = "Encuesta" )
public class Encuesta extends EntidadBase{
    @Column( name = "nombre" )
    private String _nombre;

    @ManyToOne
    @JoinColumn( name = "Marca_id" )
    private Marca _marca;

    @OneToMany( mappedBy = "_encuesta", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<EncuestaTipo> _encuestatipo;

    @OneToMany( mappedBy = "_encuesta", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<PreguntaEncuesta> _preguntaencuesta;



}
