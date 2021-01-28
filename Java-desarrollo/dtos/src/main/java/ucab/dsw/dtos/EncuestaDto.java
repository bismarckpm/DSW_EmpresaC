package ucab.dsw.dtos;

import java.util.List;

public class EncuestaDto extends BaseDto {


    public EncuestaDto()
    {
    }

    public EncuestaDto ( long id ) throws Exception
    {
        super( id );
    }

    private String nombre;

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    private String estado;

    public String getEstado()
    {
        return estado;
    }

    public void setEstado( String estado )
    {
        this.estado = estado;
    }

    private MarcaDto marcaDto;

    public MarcaDto getMarcaDto()
    {
        return marcaDto;
    }

    public void setMarcaDto( MarcaDto marcaDto )
    {
        this.marcaDto = marcaDto;
    }

    private List<PreguntaDto> preguntas;

    public List<PreguntaDto> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaDto> preguntas) {
        this.preguntas = preguntas;
    }

    private List<EncuestadoDto> encuestado;

    public List<EncuestadoDto> getEncuestado() {
        return encuestado;
    }

    public void setEncuestado(List<EncuestadoDto> encuestado) {
        this.encuestado = encuestado;
    }
}
