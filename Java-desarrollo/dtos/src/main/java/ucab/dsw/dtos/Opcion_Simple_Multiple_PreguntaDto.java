package ucab.dsw.dtos;

public class Opcion_Simple_Multiple_PreguntaDto extends DtoBase{

    private PreguntaDto preguntaDto;

    public PreguntaDto getPreguntaDto()
    {
        return preguntaDto;
    }

    public void setPreguntaDto( PreguntaDto preguntaDto )
    {
        this.preguntaDto = preguntaDto;
    }

    private Opcion_Simple_MultipleDto opcion_Simple_MultipleDto;

    public Opcion_Simple_MultipleDto getOpcion_Simple_MultipleDto()
    {
        return opcion_Simple_MultipleDto;
    }

    public void setOpcion_Simple_MultipleDto( Opcion_Simple_MultipleDto opcion_Simple_MultipleDto )
    {
        this.opcion_Simple_MultipleDto = opcion_Simple_MultipleDto;
    }
}
