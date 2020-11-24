package ucab.dsw.dtos;



public class RespuestaDto {


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

    private Pregunta_EncuestaDto pregunta_EncuestaDto;

    public Pregunta_EncuestaDto getPregunta_EncuestaDto()
    {
        return pregunta_EncuestaDto;
    }

    public void setPregunta_EncuestaDto( Pregunta_EncuestaDto pregunta_EncuestaDto )
    {
        this.pregunta_EncuestaDto = pregunta_EncuestaDto;
    }
}
