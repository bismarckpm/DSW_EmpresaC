package ucab.dsw.dtos;

public class SubcategoriaDto extends BaseDto {

    private String nombre;

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    private CategoriaDto categoriaDto;

    public CategoriaDto getCategoriaDto()
    {
        return categoriaDto;
    }

    public void setCategoriaDto( CategoriaDto categoriaDto )
    {
        this.categoriaDto = categoriaDto;
    }

    public SubcategoriaDto(long id) throws Exception {
        super(id);
    }

    public SubcategoriaDto() {
    }

    public String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
