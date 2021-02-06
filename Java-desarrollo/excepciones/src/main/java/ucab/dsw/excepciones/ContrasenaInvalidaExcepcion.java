package ucab.dsw.excepciones;

public class ContrasenaInvalidaExcepcion extends RuntimeException{

    public String mensaje;

    public ContrasenaInvalidaExcepcion(String mensaje) {
        this.mensaje = mensaje;
    }

    public ContrasenaInvalidaExcepcion() {
        this.mensaje = "Contraseña invalida";
    }

    public String getMensaje() { return mensaje; }

    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}

