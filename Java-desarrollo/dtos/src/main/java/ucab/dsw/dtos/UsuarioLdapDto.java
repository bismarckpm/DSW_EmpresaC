package ucab.dsw.dtos;

public class UsuarioLdapDto extends DtoBase
{
    private String cn;
    private String sn;
    private String tipo_usuario;
    private String nombre;
    private String correoelectronico;
    private String uid;
    private String contrasena;

    public String getCn() { return cn; }
    public void setCn(String cn) { this.cn = cn; }

    public String getSn() { return sn; }
    public void setSn(String sn) { this.sn = sn; }

    public String getTipo_usuario() { return tipo_usuario; }
    public void setTipo_usuario(String tipo_usuario) { this.tipo_usuario = tipo_usuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreoelectronico() { return correoelectronico; }
    public void setCorreoelectronico(String correoelectronico) { this.correoelectronico = correoelectronico; }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
