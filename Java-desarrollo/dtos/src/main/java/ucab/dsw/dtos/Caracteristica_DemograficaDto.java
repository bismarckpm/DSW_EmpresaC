package ucab.dsw.dtos;


public class Caracteristica_DemograficaDto {

    private int edad_min;

    public int getEdad_min()
    {
        return edad_min;
    }

    public void setEdad_min( int edad_min ) { this.edad_min = edad_min; }


    private int edad_max;

    public int getEdad_max()
    {
        return edad_max;
    }

    public void setEdad_max( int edad_max ) { this.edad_max = edad_max; }


    private String nivel_socioeconomico;

    public String getNivel_socioeconomico()
    {
        return nivel_socioeconomico;
    }

    public void setNivel_socioeconomico( String nivel_socioeconomico )
    {
        this.nivel_socioeconomico = nivel_socioeconomico;
    }

    private String nacionalidad;

    public String getNacionalidad()
    {
        return nacionalidad;
    }

    public void setNacionalidad( String nacionalidad )
    {
        this.nacionalidad = nacionalidad;
    }


    private int cantidad_hijos;

    public int getCantidad_hijos()
    {
        return cantidad_hijos;
    }

    public void setCantidad_hijos( int cantidad_hijos ) { this.cantidad_hijos = cantidad_hijos; }


    private String genero;

    public String getGenero() { return genero; }

    public void setGenero( String genero ) { this.genero = genero; }

    private ParroquiaDto parroquiaDto;

    public ParroquiaDto getParroquiaDto()
    {
        return parroquiaDto;
    }

    public void setParroquiaDto( ParroquiaDto parroquiaDto )
    {
        this.parroquiaDto = parroquiaDto;
    }

    private Nivel_AcademicoDto nivel_AcademicoDto;

    public Nivel_AcademicoDto getNivel_AcademicoDto()
    {
        return nivel_AcademicoDto;
    }

    public void setNivel_AcademicoDto( Nivel_AcademicoDto nivel_AcademicoDto )
    {
        this.nivel_AcademicoDto = nivel_AcademicoDto;
    }
}
