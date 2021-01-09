package ucab.dsw.dtos;

public class CiudadDto extends BaseDto
{
    private String nombre;

    public String getBombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    private EstadoDto estadoDto;

    public EstadoDto getEstadoDto()
    {
        return estadoDto;
    }

    public void setEstadoDto( EstadoDto estadoDto )
    {
        this.estadoDto = estadoDto;
    }
}
