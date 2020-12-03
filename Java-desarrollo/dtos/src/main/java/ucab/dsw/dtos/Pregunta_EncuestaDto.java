package ucab.dsw.dtos;

public class Pregunta_EncuestaDto extends DtoBase{

    private String nombre;

    public String getBombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    private EncuestaDto encuestaDto;

    public EncuestaDto getEncuestaDto()
    {
        return encuestaDto;
    }

    public void setEncuestaDto( EncuestaDto encuestaDto )
    {
        this.encuestaDto = encuestaDto;
    }

    private PreguntaDto preguntaDto;

    public PreguntaDto getPreguntaDto()
    {
        return preguntaDto;
    }

    public void setPreguntaDto( PreguntaDto preguntaDto )
    {
        this.preguntaDto = preguntaDto;
    }

}
