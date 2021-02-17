package ucab.dsw.dtos;

import java.util.List;


public class PreguntaDto extends BaseDto {

    public PreguntaDto()
    {
    }

    public PreguntaDto ( long id ) throws Exception
    {
        super( id );
    }

    private String descripcion;

    private String estado;

    private String tipopregunta;


    private int valormin;


    private int valormax;

    private List<OpcionSimpleMultipleDto> opciones;

    public List<OpcionSimpleMultipleDto> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<OpcionSimpleMultipleDto> opciones) {
        this.opciones = opciones;
    }

    public int getValormax() {
        return valormax;
    }

    public void setValormax(int valormax) {
        this.valormax = valormax;
    }

    public int getValormin() {
        return valormin;
    }

    public void setValormin(int valormin) {
        this.valormin = valormin;
    }

    public String getTipopregunta() {
        return tipopregunta;
    }

    public void setTipopregunta(String tipopregunta) {
        this.tipopregunta = tipopregunta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
