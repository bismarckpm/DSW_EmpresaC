package ucab.dsw.dtos;

public class Metodo_Conexion_EncuestadoDto extends DtoBase{

    private EncuestadoDto encuestadoDto;

    public EncuestadoDto getEncuestadoDto()
    {
        return encuestadoDto;
    }

    public void setEncuestadoDto( EncuestadoDto encuestadoDto )
    {
        this.encuestadoDto = encuestadoDto;
    }

    private Metodo_ConexionDto metodo_ConexionDto;

    public Metodo_ConexionDto getMetodo_ConexionDto()
    {
        return metodo_ConexionDto;
    }

    public void setMetodo_ConexionDto( Metodo_ConexionDto metodo_ConexionDto ) { this.metodo_ConexionDto = metodo_ConexionDto; }
}
