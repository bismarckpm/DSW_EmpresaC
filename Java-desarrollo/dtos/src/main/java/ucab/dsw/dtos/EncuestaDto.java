package ucab.dsw.dtos;

public class EncuestaDto extends DtoBase{

    private String nombre;

    public String getBombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    private MarcaDto marcaDto;

    public MarcaDto getMarcaDto()
    {
        return marcaDto;
    }

    public void setMarcaDto( MarcaDto marcaDto )
    {
        this.marcaDto = marcaDto;
    }
}
