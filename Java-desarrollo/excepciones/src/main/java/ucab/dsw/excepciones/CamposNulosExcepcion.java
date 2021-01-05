package ucab.dsw.excepciones;

public class CamposNulosExcepcion extends Exception {
    public String mensaje;

    public CamposNulosExcepcion(String mensaje) {
        this.mensaje = mensaje;
    }

    public CamposNulosExcepcion() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
