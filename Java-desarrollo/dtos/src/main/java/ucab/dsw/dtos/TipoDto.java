package ucab.dsw.dtos;

public class TipoDto extends DtoBase{

    private String nombre;

    public String getNombre()
    {
        return nombre;
    }

    private String estado;

    public String getEstado()
    {
        return estado;
    }

    public void setEstado( String estado )
    {
        this.estado = estado;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    public TipoDto()
    {
    }

    public TipoDto ( long id ) throws Exception
    {
        super( id );
    }

}
