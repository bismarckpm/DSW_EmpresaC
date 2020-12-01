package ucab.dsw.dtos;

public class PresentacionDto {

    private String nombre;

    public String getBombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
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
