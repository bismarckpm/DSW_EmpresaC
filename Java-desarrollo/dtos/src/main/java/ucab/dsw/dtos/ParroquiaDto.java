package ucab.dsw.dtos;

public class ParroquiaDto extends BaseDto
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

    private String categoria_social;

    public String getCategoria_social()
    {
        return categoria_social;
    }

    public void setCategoria_social( String categoria_social )
    {
        this.categoria_social = categoria_social;
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
