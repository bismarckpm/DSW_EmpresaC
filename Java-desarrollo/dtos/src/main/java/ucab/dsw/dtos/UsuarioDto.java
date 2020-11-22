package ucab.dsw.dtos;

public class UsuarioDto extends DtoBase
{
    private String nombre;

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido( String apellido )
    {
        this.apellido = apellido;
    }

    public String getCorreoelectronico()
    {
        return correoelectronico;
    }

    public void setCorreoelectronico( String correoelectronico )
    {
        this.correoelectronico = correoelectronico;
    }

    public TipoUsuarioDto getTipoUsuarioDto()
    {
        return tipoUsuarioDto;
    }

    public void setTipoUsuarioDto( TipoUsuarioDto tipoUsuarioDto )
    {
        this.tipoUsuarioDto = tipoUsuarioDto;
    }

    private String apellido;
    private String correoelectronico;
    private TipoUsuarioDto tipoUsuarioDto;

    public String getContrasena()
    {
        return contrasena;
    }

    public void setContrasena( String contrasena )
    {
        this.contrasena = contrasena;
    }

    private String contrasena;

    public String getEstatus()
    {
        return estatus;
    }

    public void setEstatus( String estatus )
    {
        this.estatus = estatus;
    }

    private String estatus;
}
