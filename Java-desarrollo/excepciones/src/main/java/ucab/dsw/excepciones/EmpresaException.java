package ucab.dsw.excepciones;

public class EmpresaException extends Exception {

    public String codigo;
    public String mensaje_soporte;
    public String mensaje;

    public EmpresaException(String codigo, String mensaje_soporte, String mensaje) {
        super();
        this.codigo = codigo;
        this.mensaje_soporte = codigo + " - " +mensaje_soporte;
        this.mensaje=mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

}
