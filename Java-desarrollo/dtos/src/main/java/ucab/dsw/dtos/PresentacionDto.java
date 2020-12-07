package ucab.dsw.dtos;

<<<<<<< HEAD
public class PresentacionDto extends DtoBase{
=======
public class PresentacionDto extends DtoBase {
>>>>>>> 269b5caca644055e5622875a19c1a5450d20a301

    private String nombre;

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
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

    private TipoDto tipoDto;

    public TipoDto getTipoDto()
    {
        return tipoDto;
    }

    public void setTipoDto( TipoDto tipoDto )
    {
        this.tipoDto = tipoDto;
    }

    public PresentacionDto()
    {
    }

    public PresentacionDto ( long id ) throws Exception
    {
        super( id );
    }
}
