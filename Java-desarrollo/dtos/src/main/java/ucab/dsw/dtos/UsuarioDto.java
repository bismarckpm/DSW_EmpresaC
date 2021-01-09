package ucab.dsw.dtos;

public class UsuarioDto extends BaseDto
{
    public UsuarioDto()
    {
    }

    public UsuarioDto ( long id ) throws Exception
    {
        super( id );
    }
    private String usuario;

    public String getUsuario() { return usuario; }

    public void setUsuario( String usuario )
    {
        this.usuario = usuario;
    }

    private String estado;

    public String getEstado()
    {
        return estado;
    }

    public void setEstado( String estado )
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

    public String contrasena;

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
