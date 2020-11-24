package ucab.dsw.dtos;

public class Opcion_Simple_MultipleDto {

    private String opcion;

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
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
