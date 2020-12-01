package ucab.dsw.dtos;

public class Encuesta_TipoDto {

    private EncuestaDto encuestaDto;

    public EncuestaDto getEncuestaDto()
    {
        return encuestaDto;
    }

    public void setEncuestaDto( EncuestaDto encuestaDto )
    {
        this.encuestaDto = encuestaDto;
    }

    private TipoDto tipoDto;

    public TipoDto getTipoDto()
    {
        return tipoDto;
    }

    public void setTipoDto( TipoDto tipoDto )
    {
        this.tipoDto = tipoDto;
    }


}
