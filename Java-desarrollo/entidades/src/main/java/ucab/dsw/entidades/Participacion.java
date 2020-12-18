package ucab.dsw.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "Participacion" )
@NamedQueries({
        @NamedQuery(name="ParticipacionByEstudio", query="select p FROM Participacion p where p._solicitudestudio._id=:estudio_id")

})
public class Participacion extends EntidadBase{

    @Column( name = "estado" )
    private String _estado;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
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
