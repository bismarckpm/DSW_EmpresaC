package ucab.dsw.dtos;


import java.sql.Date;

public class HijoDto extends DtoBase{
    private String nombre;

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre( String nombre )
    {
        this.nombre = nombre;
    }

    private String apellido;

    public String getApellido() { return apellido; }

    public void setApellido( String apellido ) { this.apellido = apellido; }


    private String genero;

    public String getGenero()
    {
        return genero;
    }

    public void setGenero( String genero )
    {
        this.genero = genero;
    }


    private Date fecha_nacimiento;

    public Date getFecha_nacimiento() { return fecha_nacimiento; }

    public void setFecha_nacimiento( Date fecha_nacimiento ) { this.fecha_nacimiento = fecha_nacimiento; }

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
