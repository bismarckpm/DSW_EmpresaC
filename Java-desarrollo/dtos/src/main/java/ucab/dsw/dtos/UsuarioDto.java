package ucab.dsw.dtos;

public class UsuarioDto extends DtoBase
{
    private String usuario;

    public String getUsuario() { return usuario; }

    public void setUsuario( String usuario )
    {
        this.usuario = usuario;
    }

    private String estado;

    public String getEstados()
    {
        return estado;
    }

    public void setEstados( String estado )
    {
        this.estado = estado;
    }

    private String rol;

    public String getRol()
    {
        return rol;
    }

    public void setRol( String rol )
    {
        this.rol = rol;
    }

}
