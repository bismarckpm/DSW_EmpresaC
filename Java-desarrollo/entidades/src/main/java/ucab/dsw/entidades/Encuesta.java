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

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Marca_id" )
    private Marca _marca_encuesta;

<<<<<<< HEAD
=======

>>>>>>> 269b5caca644055e5622875a19c1a5450d20a301
    @OneToMany( mappedBy = "_encuesta_Pregunta", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<PreguntaEncuesta> _preguntaencuesta;

    @OneToMany( mappedBy = "_encuesta_solicitud", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<SolicitudEstudio> _solicitudestudio;

    public Marca get_marca() {
        return _marca_encuesta;
    }

    public void set_marca(Marca _marca_encuesta) {
        this._marca_encuesta = _marca_encuesta;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public Encuesta(long id )
    {
        super( id );
    }

    public Encuesta( )
    {

    }
}
