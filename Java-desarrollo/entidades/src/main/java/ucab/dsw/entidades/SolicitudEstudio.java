package ucab.dsw.entidades;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(name="EstudiosByCliente", query="select e FROM SolicitudEstudio e where e._cliente._id=:cliente_id and e._marca_solicitud._id=:marca_id")
})
@Table( name = "Solicitud_Estudio" )
public class SolicitudEstudio extends EntidadBase{

    @Column( name = "fecha_inicio" )
    private Date _fecha_inicio;

    @Column( name = "fecha_final" )
    private Date _fecha_fin;

    @Column( name = "estado" )
    private String _estado;

    @Column( name = "resultado_analista" )
    private String _resultadoanalista;

    @Column( name = "modo_encuesta" )
    private String _modoencuesta;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Encuesta_id" )
    private Encuesta _encuesta_solicitud;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Cliente_id" )
    private Cliente _cliente;

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Usuario_analista_id" )
    private Usuario _usuario;

    @ManyToOne(optional = false , fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn( name = "Caracteristica_demografica_id" )
    private Caracteristica_Demografica _caracteristicademografica;

    @OneToMany( mappedBy = "_solicitudestudio", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Participacion> _participacion;

    public Caracteristica_Demografica get_caracteristicademografica() {
        return _caracteristicademografica;
    }

    public void set_caracteristicademografica(Caracteristica_Demografica _caracteristicademografica) {
        this._caracteristicademografica = _caracteristicademografica;
    }

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Usuario_admin_id" )
    private Usuario _usuario2;

    public void set_usuario2(Usuario _usuario2) {
        this._usuario2 = _usuario2;
    }

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "marca_id" )

    private Marca _marca_solicitud;

    public void set_marca(Marca _marca_solicitud) {
        this._marca_solicitud = _marca_solicitud;
    }

    public Marca get_marca() {
        return _marca_solicitud;
    }

    public Usuario get_usuario() {
        return _usuario;
    }

    public void set_usuario(Usuario _usuario) {
        this._usuario = _usuario;
    }

    public Cliente get_cliente() {
        return _cliente;
    }

    public void set_cliente(Cliente _cliente) {
        this._cliente = _cliente;
    }

    public Encuesta get_encuesta() {
        return _encuesta_solicitud;
    }

    public void set_encuesta(Encuesta _encuesta_solicitud) {
        this._encuesta_solicitud = _encuesta_solicitud;
    }

    public String get_modoencuesta() {
        return _modoencuesta;
    }

    public void set_modoencuesta(String _modoencuesta) {
        this._modoencuesta = _modoencuesta;
    }

    public String get_resultadoanalista() {
        return _resultadoanalista;
    }

    public void set_resultadoanalista(String _resultadoanalista) {
        this._resultadoanalista = _resultadoanalista;
    }

    public String get_estado() {
        return _estado;
    }

    public void set_estado(String _estado) {
        this._estado = _estado;
    }

    public Date get_fecha_fin() {
        return _fecha_fin;
    }

    public void set_fecha_fin(Date _fecha_fin) {
        this._fecha_fin = _fecha_fin;
    }

    public Date get_fecha_inicio() {
        return _fecha_inicio;
    }

    public void set_fecha_inicio(Date _fecha_inicio) {
        this._fecha_inicio = _fecha_inicio;
    }

    public SolicitudEstudio(long id )
    {
        super( id );
    }

    public SolicitudEstudio()
    {

    }

    public List<Participacion> get_participacion() {
        return _participacion;
    }

    public void set_participacion(List<Participacion> _participacion) {
        this._participacion = _participacion;
    }
}
