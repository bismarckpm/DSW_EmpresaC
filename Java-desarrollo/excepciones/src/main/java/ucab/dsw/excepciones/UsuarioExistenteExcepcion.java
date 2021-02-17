package ucab.dsw.excepciones;

public class UsuarioExistenteExcepcion extends RuntimeException{
    public String mensaje;

    public UsuarioExistenteExcepcion(String mensaje) {
        this.mensaje = mensaje;
    }

    public UsuarioExistenteExcepcion() { }

    public String getMensaje() { return mensaje; }

    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
