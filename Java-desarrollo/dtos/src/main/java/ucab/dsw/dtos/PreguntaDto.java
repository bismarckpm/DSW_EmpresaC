package ucab.dsw.dtos;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

public class PreguntaDto {


    private String descripcion;

    private String tipopregunta;


    private int valormin;


    private int valormax;


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
