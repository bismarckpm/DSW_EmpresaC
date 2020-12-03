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

    private Opcion_Simple_MultipleDto opcion_Simple_MultipleDto;

    public Opcion_Simple_MultipleDto getOpcion_Simple_MultipleDto()
    {
        return opcion_Simple_MultipleDto;
    }

    public void setOpcion_Simple_MultipleDto( Opcion_Simple_MultipleDto Oopcion_Simple_MultipleDto )
    {
        this.opcion_Simple_MultipleDto = opcion_Simple_MultipleDto;
    }
}
