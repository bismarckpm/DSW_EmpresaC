package ucab.dsw.dtos;

public class UsuarioLdapDto extends DtoBase
{
    private String usuario;
    private String contrasena;
    private String tipo_usuario;
    private String sn;

    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }

    public void setUsuario( String usuario )
    {
        this.usuario = usuario;
    }
    public void setContrasena( String contrasena )
    {
        this.contrasena= contrasena;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
