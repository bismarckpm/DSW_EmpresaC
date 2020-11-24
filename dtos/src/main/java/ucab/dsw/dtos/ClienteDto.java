package ucab.dsw.dtos;



public class ClienteDto extends DtoBase{


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
}
