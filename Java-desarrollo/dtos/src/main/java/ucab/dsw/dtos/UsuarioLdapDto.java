package ucab.dsw.dtos;

public class UsuarioLdapDto extends DtoBase
{
    private String correo;
    private String contrasena;

    public String getCorreoelectronico() { return correo; }
    public String getContrasena() { return contrasena; }

    public void setCorreoelectronico( String correo )
    {
        this.correo = correo;
    }
    public void setContrasena( String contrasena )
    {
        this.contrasena= contrasena;
    }
    

}
