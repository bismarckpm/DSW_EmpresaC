package ucab.dsw.dtos;

public class EstadoDto extends BaseDto
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

    private PaisDto paisDto;

    public PaisDto getPaisDto()
    {
        return paisDto;
    }

    public void setPaisDto( PaisDto paisDto )
    {
        this.paisDto = paisDto;
    }
}
