package ucab.dsw.dtos;

public class MarcaTipoDto extends BaseDto {

    private MarcaDto marcaDro;

    public MarcaTipoDto(long id) throws Exception {
        super(id);
    }

    public MarcaTipoDto() {

    }

    public MarcaDto getMarcaDto()
    {
        return marcaDro;
    }

    public void setMarcaDto( MarcaDto marcaDro )
    {
        this.marcaDro = marcaDro;
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
