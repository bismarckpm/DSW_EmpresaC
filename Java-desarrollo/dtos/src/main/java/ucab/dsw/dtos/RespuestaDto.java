package ucab.dsw.dtos;


import java.util.List;

public class RespuestaDto extends BaseDto {


    private int respuestaboolean;

    public int getRespuestaboolean() {
        return respuestaboolean;
    }

    public void setRespuestaboolean(int respuestaboolean) {
        this.respuestaboolean = respuestaboolean;
    }

    private String respuestadesarrollo;

    public String getRespuestadesarrollo() {
        return respuestadesarrollo;
    }

    public void setRespuestadesarrollo(String respuestadesarrollo) {
        this.respuestadesarrollo = respuestadesarrollo;
    }


    private int respuestarango;

    public int getRespuestarango() {
        return respuestarango;
    }

    public void setRespuestarango(int respuestarango) {
        this.respuestarango = respuestarango;
    }

    private ParticipacionDto participacionDto;

    public ParticipacionDto getParticipacionDto()
    {
        return participacionDto;
    }

    public void setParticipacionDto( ParticipacionDto participacionDto )
    {
        this.participacionDto = participacionDto;
    }

    private PreguntaEncuestaDto pregunta_EncuestaDto;

    public PreguntaEncuestaDto getPregunta_EncuestaDto()
    {
        return pregunta_EncuestaDto;
    }

    public void setPregunta_EncuestaDto( PreguntaEncuestaDto pregunta_EncuestaDto )
    {
        this.pregunta_EncuestaDto = pregunta_EncuestaDto;
    }

    private List<OpcionSimpleMultiplePreguntaDto> opciones;

    public List<OpcionSimpleMultiplePreguntaDto> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<OpcionSimpleMultiplePreguntaDto> opciones) {
        this.opciones = opciones;
    }
}
