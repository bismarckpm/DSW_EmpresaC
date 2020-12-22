package ucab.dsw.dtos;

import java.util.List;

public class EncuestaDto extends DtoBase{


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
}
