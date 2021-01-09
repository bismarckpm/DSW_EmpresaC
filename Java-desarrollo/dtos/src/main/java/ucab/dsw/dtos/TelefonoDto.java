package ucab.dsw.dtos;



public class TelefonoDto extends BaseDto {


    private String numero;

    public String getNumero()
    {
        return numero;
    }

    public void setNumero( String numero )
    {
        this.numero = numero;
    }


    private String codigo_area;

    public String getCodigo_area() { return codigo_area; }

    public void setCodigo_area( String codigo_area ) { this.codigo_area = codigo_area; }

    private EncuestadoDto encuestadoDto;

    public EncuestadoDto getEncuestadoDto()
    {
        return encuestadoDto;
    }

    public void setEncuestadoDto( EncuestadoDto encuestadoDto )
    {
        this.encuestadoDto = encuestadoDto;
    }
}
