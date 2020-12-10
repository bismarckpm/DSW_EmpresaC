package ucab.dsw.dtos;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

public class PreguntaDto extends DtoBase {

    public PreguntaDto()
    {
    }

    public PreguntaDto ( long id ) throws Exception
    {
        super( id );
    }

    private String descripcion;

    private String tipopregunta;


    private int valormin;


    private int valormax;

    private List<Opcion_Simple_MultipleDto> opciones;

    public List<Opcion_Simple_MultipleDto> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<Opcion_Simple_MultipleDto> opciones) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
