package ucab.dsw.dtos;

public class OpcionSimpleMultiplePreguntaDto extends BaseDto {

    private PreguntaDto preguntaDto;

    public PreguntaDto getPreguntaDto()
    {
        return preguntaDto;
    }

    public void setPreguntaDto( PreguntaDto preguntaDto )
    {
        this.preguntaDto = preguntaDto;
    }

    private OpcionSimpleMultipleDto opcion_Simple_MultipleDto;

    public OpcionSimpleMultipleDto getOpcion_Simple_MultipleDto()
    {
        return opcion_Simple_MultipleDto;
    }

    public void setOpcion_Simple_MultipleDto( OpcionSimpleMultipleDto opcion_Simple_MultipleDto )
    {
        this.opcion_Simple_MultipleDto = opcion_Simple_MultipleDto;
    }

    public OpcionSimpleMultiplePreguntaDto()
    {
    }

    public OpcionSimpleMultiplePreguntaDto(long id ) throws Exception
    {
        super( id );
    }
}
