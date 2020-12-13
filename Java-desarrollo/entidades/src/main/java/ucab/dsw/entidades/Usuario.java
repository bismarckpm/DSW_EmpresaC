package ucab.dsw.entidades;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name="Admins", query="select a FROM Usuario a where a._rol='admin'"),
        @NamedQuery(name="Encuestados", query="select a FROM Usuario a where a._rol='encuestado' and a._id=:encuestado_id")
        @NamedQuery(name="analistas", query="select a FROM Usuario a where a._rol='analista'")
})
@Table( name = "usuario")
public class Usuario extends EntidadBase
{
    @Column( name = "usuario" )
    private String _usuario;

    public String get_usuario()
    {
        return _usuario;
    }

    public void set_usuario( String _usuario )
    {
        this._usuario = _usuario;
    }

    @Column( name = "estado" )
    private String _estado;

    public String get_estado()
    {
        return _estado;
    }

    public void set_estado( String _estado )
    {
        this._estado = _estado;
    }


    @Column( name = "rol" )
    private String _rol;

    public String get_rol()
    {
        return _rol;
    }

    public void set_rol( String _rol )
    {
        this._rol = _rol;
    }


    @OneToMany( mappedBy = "_usuario_cliente", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Cliente> _cliente;

    public List<Cliente> get_cliente()
    {
        return _cliente;
    }

    public void set_cliente( List<Cliente> _cliente )
    {
        this._cliente = _cliente;
    }

    @OneToMany( mappedBy = "_usuario_encuestado", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<Encuestado> _encuestado;

    public List<Encuestado> get_encuestado()
    {
        return _encuestado;
    }

    public void set_encuestado( List<Encuestado> _encuestado )
    {
        this._encuestado = _encuestado;
    }

    @OneToMany( mappedBy = "_usuario", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<SolicitudEstudio> _solicitudestudio;

    public List<SolicitudEstudio> get_solicitudestudio()
    {
        return _solicitudestudio;
    }

    public void set_solicitudestudio( List<SolicitudEstudio>  _solicitudestudio )
    {
        this._solicitudestudio = _solicitudestudio;
    }


    @OneToMany( mappedBy = "_usuario2", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<SolicitudEstudio> _solicitudestudio2;

    public List<SolicitudEstudio> get_solicitudestudio2()
    {
        return _solicitudestudio2;
    }

    public void set_solicitudestudio2( List<SolicitudEstudio>  _solicitudestudio2 )
    {
        this._solicitudestudio2 = _solicitudestudio2;
    }

    public Usuario( long id )
    {
        super( id );
    }

    public Usuario()
    {

    }
}
