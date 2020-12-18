package ucab.dsw.dtos;



public class ClienteDto extends DtoBase{

    public ClienteDto()
    {
    }

    public ClienteDto ( long id ) throws Exception
    {
        super( id );
    }

    private String rif;

    public String getRif()
    {
        return rif;
    }

    public void setRif( String rif )
    {
        this.rif = rif;
    }

    private String razon_social;

    public String getRazon_social()
    {
        return razon_social;
    }

    public void setRazon_social( String razon_social ) { this.razon_social = razon_social; }

    private String estado;

    public String getEstado()
    {
        return estado;
    }

    public void setEstado( String estado )
    {
        this.estado = estado;
    }

    private String nombre_empresa;

    public String getNombre_empresa()
    {
        return nombre_empresa;
    }

    public void setNombre_empresa( String nombre_empresa )
    {
        this.nombre_empresa = nombre_empresa;
    }

    private UsuarioDto usuarioDto;

    public UsuarioDto getUsuarioDto()
    {
        return usuarioDto;
    }

    public void setUsuarioDto( UsuarioDto usuarioDto )
    {
        this.usuarioDto = usuarioDto;
    }

    private UsuarioLdapDto usuarioLdapDto;

    public UsuarioLdapDto getUsuarioLdapDto() { return usuarioLdapDto; }

    public void setUsuarioLdapDto(UsuarioLdapDto usuarioLdapDto) { this.usuarioLdapDto = usuarioLdapDto; }
}
