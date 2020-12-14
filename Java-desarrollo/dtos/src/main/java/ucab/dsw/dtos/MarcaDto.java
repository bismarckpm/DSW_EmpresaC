package ucab.dsw.dtos;


import java.util.List;

public class MarcaDto extends DtoBase{

    public MarcaDto()
    {
    }

    public MarcaDto ( long id ) throws Exception
    {
        super( id );
    }


    private String nombre;

    public String getNombre()
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

    public List<TipoDto> tipoDto;

    public List<TipoDto> getTipo_Dto() {
        return tipoDto;
    }

    public void setTipo_Dto(List<TipoDto> tipoDto) {
        this.tipoDto = tipoDto;
    }
}
