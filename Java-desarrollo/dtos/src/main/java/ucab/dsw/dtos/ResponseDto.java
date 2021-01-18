package ucab.dsw.dtos;

public class ResponseDto extends BaseDto {
    public String mensaje;
    public String mensaje_soporte;
    public String estado;
    public Object objeto;

    public ResponseDto() {
    }

    public String get_mensaje() {
        return mensaje;
    }

    public void set_mensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String get_mensaje_soporte() {
        return mensaje_soporte;
    }

    public void set_mensaje_soporte(String _mensaje_soporte) {
        this.mensaje_soporte = _mensaje_soporte;
    }

    public String get_estado() {
        return estado;
    }

    public void set_estado(String estado) {
        this.estado = estado;
    }

    public Object get_objeto() {
        return objeto;
    }

    public void set_objeto(Object _objeto) {
        this.objeto = _objeto;
    }
}
