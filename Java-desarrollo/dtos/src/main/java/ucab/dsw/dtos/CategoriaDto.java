package ucab.dsw.dtos;

public class CategoriaDto extends DtoBase{

    private String nombre;

    public CategoriaDto(long id) throws Exception {
        super(id);
    }

    public CategoriaDto(){}

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }
}
