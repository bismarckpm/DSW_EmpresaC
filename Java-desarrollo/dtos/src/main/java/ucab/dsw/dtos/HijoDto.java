package ucab.dsw.dtos;


public class HijoDto extends BaseDto {
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


    private String fecha_nacimiento;

    public String getFecha_nacimiento() { return fecha_nacimiento; }

    public void setFecha_nacimiento(String fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento; }

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
