package ucab.dsw.dtos;

public class MetodoConexionEncuestadoDto extends BaseDto {

    private EncuestadoDto encuestadoDto;

    public EncuestadoDto getEncuestadoDto()
    {
        return encuestadoDto;
    }

    public void setEncuestadoDto( EncuestadoDto encuestadoDto )
    {
        this.encuestadoDto = encuestadoDto;
    }

    private MetodoConexionDto metodo_ConexionDto;

    public MetodoConexionDto getMetodo_ConexionDto()
    {
        return metodo_ConexionDto;
    }

    public void setMetodo_ConexionDto( MetodoConexionDto metodo_ConexionDto ) { this.metodo_ConexionDto = metodo_ConexionDto; }
}
