package ucab.dsw.dtos;

public class Ocupacion_EncuestadoDto extends DtoBase{

    private EncuestadoDto encuestadoDto;

    public EncuestadoDto getEncuestadoDto()
    {
        return encuestadoDto;
    }

    public void setEncuestadoDto( EncuestadoDto encuestadoDto )
    {
        this.encuestadoDto = encuestadoDto;
    }

    private OcupacionDto ocupacionDto;

    public OcupacionDto getOcupacionDto()
    {
        return ocupacionDto;
    }

    public void setOcupacionDto( OcupacionDto ocupacionDto )
    {
        this.ocupacionDto = ocupacionDto;
    }
}
