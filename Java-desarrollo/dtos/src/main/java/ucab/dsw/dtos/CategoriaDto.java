package ucab.dsw.dtos;

public class CategoriaDto extends BaseDto {

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

    public String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
