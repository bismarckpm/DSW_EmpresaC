package ucab.dsw.dtos;

public class ParroquiaDto extends DtoBase
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

    private CiudadDto ciudadDto;

    public CiudadDto getCiudadDto()
    {
        return ciudadDto;
    }

    public void setCiudadDto( CiudadDto ciudadDto )
    {
        this.ciudadDto = ciudadDto;
    }

    public ParroquiaDto(long id) throws Exception {
        super(id);
    }

    public ParroquiaDto() {
    }
}
