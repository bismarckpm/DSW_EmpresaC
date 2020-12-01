package ucab.dsw.dtos;

public class MarcaDto {

    private String nombre;

    public String getBombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    private SubcategoriaDto subcategoriaDto;

    public SubcategoriaDto getSubcategoriaDto()
    {
        return subcategoriaDto;
    }

    public void setSubcategoriaDto( SubcategoriaDto subcategoriaDto )
    {
        this.subcategoriaDto = subcategoriaDto;
    }
}
