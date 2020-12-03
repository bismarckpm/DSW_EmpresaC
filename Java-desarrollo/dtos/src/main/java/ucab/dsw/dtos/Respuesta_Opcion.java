package ucab.dsw.dtos;

public class Respuesta_Opcion extends DtoBase{

    private RespuestaDto respuestaDto;

    public RespuestaDto getRespuestaDto()
    {
        return respuestaDto;
    }

    public void setRespuestaDto( RespuestaDto respuestaDto )
    {
        this.respuestaDto = respuestaDto;
    }

    private Opcion_Simple_Multiple_PreguntaDto opcion_Simple_Multiple_PreguntaDto;

    public Opcion_Simple_Multiple_PreguntaDto getOpcion_Simple_Multiple_PreguntaDto()
    {
        return opcion_Simple_Multiple_PreguntaDto;
    }

    public void setOpcion_Simple_Multiple_PreguntaDto( Opcion_Simple_Multiple_PreguntaDto opcion_Simple_Multiple_PreguntaDto )
    {
        this.opcion_Simple_Multiple_PreguntaDto = opcion_Simple_Multiple_PreguntaDto;
    }
}
