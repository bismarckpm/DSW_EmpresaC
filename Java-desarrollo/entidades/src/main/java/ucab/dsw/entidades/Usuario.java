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
@Table( name = "usuario" )
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

    @Override
    public String get_estados()
    {
        return _estado;
    }

    @Override
    public void set_estados( String _estado )
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



    public Usuario( long id )
    {
        super( id );
    }

    public Usuario()
    {

    }
}
