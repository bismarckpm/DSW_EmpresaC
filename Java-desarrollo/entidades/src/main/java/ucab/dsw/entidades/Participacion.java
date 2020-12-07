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

    @ManyToOne(optional = false , fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn( name = "Estudio_id" )
    private SolicitudEstudio _solicitudestudio;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Encuestado_id" )
    private Encuestado _encuestado;

    @OneToMany( mappedBy = "_participacion", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Respuesta> _respuesta;

    public Encuestado get_encuestado() {
        return _encuestado;
    }

    public void set_encuestado(Encuestado _encuestado) {
        this._encuestado = _encuestado;
    }

    public SolicitudEstudio get_solicitudestudio() {
        return _solicitudestudio;
    }

    public void set_solicitudestudio(SolicitudEstudio _solicitudestudio) {
        this._solicitudestudio = _solicitudestudio;
    }

    public String get_estado() {
        return _estado;
    }

    public void set_estado(String _estado) {
        this._estado = _estado;
    }

    public Participacion(long id )
    {
        super( id );
    }

    public Participacion( )
    {

    }

}
