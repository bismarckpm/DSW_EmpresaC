package ucab.dsw.dtos;

public class Respuesta_Opcion extends BaseDto {

    private RespuestaDto respuestaDto;

    public RespuestaDto getRespuestaDto()
    {
        return respuestaDto;
    }

    public void setRespuestaDto( RespuestaDto respuestaDto )
    {
        this.respuestaDto = respuestaDto;
    }

    private OpcionSimpleMultiplePreguntaDto opcion_Simple_Multiple_PreguntaDto;

    public OpcionSimpleMultiplePreguntaDto getOpcion_Simple_Multiple_PreguntaDto()
    {
        return opcion_Simple_Multiple_PreguntaDto;
    }

    public void setOpcion_Simple_Multiple_PreguntaDto( OpcionSimpleMultiplePreguntaDto opcion_Simple_Multiple_PreguntaDto )
    {
        this.opcion_Simple_Multiple_PreguntaDto = opcion_Simple_Multiple_PreguntaDto;
    }
}
