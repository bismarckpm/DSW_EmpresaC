package ucab.dsw.dtos;


import java.util.List;

public class MarcaDto extends BaseDto {

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

    public MarcaTipoDto marcaTipoDto;

    public MarcaTipoDto getMarcaTipo_Dto() {
        return marcaTipoDto;
    }

    public void setMarcaTipo_Dto(MarcaTipoDto marcaTipoDto) {
        this.marcaTipoDto = marcaTipoDto;
    }

    public String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
