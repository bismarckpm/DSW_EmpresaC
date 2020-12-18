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
@Table( name = "cliente" )
public class Cliente extends EntidadBase
{
    @Column( name = "rif" )
    private String _rif;

    public String get_rif()
    {
        return _rif;
    }

    public void set_rif( String _rif )
    {
        this._rif = _rif;
    }

    @Column( name = "razon_social" )
    private String _razon_social;

    public String get_razon_social()
    {
        return _razon_social;
    }

    public void set_razon_social( String _razon_social ) { this._razon_social = _razon_social; }

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

    @Column( name = "nombre_empresa" )
    private String _nombre_empresa;

    public String get_nombre_empresa()
    {
        return _nombre_empresa;
    }

    public void set_nombre_empresa( String _nombre_empresa )
    {
        this._nombre_empresa = _nombre_empresa;
    }

    @ManyToOne(optional = false , fetch = FetchType.EAGER)
    @JoinColumn( name = "Usuario_id" )
    private Usuario _usuario_cliente;

    public Usuario get_usuario_cliente()
    {
        return _usuario_cliente;
    }

    public void set_usuario_cliente( Usuario _usuario_cliente )
    {
        this._usuario_cliente = _usuario_cliente;
    }

    @OneToMany( mappedBy = "_cliente", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private List<SolicitudEstudio> _solicitudestudio;

    public Cliente( long id )
    {
        super( id );
    }

    public Cliente()
    {

    }
}
