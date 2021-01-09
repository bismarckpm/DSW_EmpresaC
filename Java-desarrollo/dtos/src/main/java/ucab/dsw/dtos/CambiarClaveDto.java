package ucab.dsw.dtos;

public class CambiarClaveDto {
    private String user_id;
    private String contrasena_actual;
    private String contrasena_nueva;

    public String getUser_id() { return user_id; }

    public void setUser_id(String user_id) { this.user_id = user_id; }

    public String getContrasena_actual() { return contrasena_actual; }

    public void setContrasena_actual(String contrasena_actual) { this.contrasena_actual = contrasena_actual; }

    public String getContrasena_nueva() { return contrasena_nueva; }

    public void setContrasena_nueva(String contrasena_nueva) { this.contrasena_nueva = contrasena_nueva; }
}
