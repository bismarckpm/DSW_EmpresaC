package ucab.dsw.dtos;


public class EncuestadoDto extends BaseDto {

    private int doc_id;

    public int getDoc_id()
    {
        return doc_id;
    }

    public void setDoc_id( int doc_id )
    {
        this.doc_id = doc_id;
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

    private String apellido;

    public String getApellido()
    {
        return apellido;
    }

    public void setApellido( String apellido )
    {
        this.apellido = apellido;
    }

    private String correo;

    public String getCorreo()
    {
        return correo;
    }

    public void setCorreo( String correo )
    {
        this.correo = correo;
    }

    private String estado;

    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }

    private String fecha_nacimiento;

    public String getFecha_nacimiento()
    {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento( String fecha_nacimiento )
    {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    private int cant_personas_vivienda;

    public int getCant_personas_vivienda()
    {
        return cant_personas_vivienda;
    }

    public void setCant_personas_vivienda( int cant_personas_vivienda )
    {
        this.cant_personas_vivienda = cant_personas_vivienda;
    }

    private String genero;

    public String getGenero()
    {
        return genero;
    }

    public void setGenero( String genero )
    {
        this.genero = genero;
    }

    private UsuarioDto usuarioDto;

    public UsuarioDto getUsuarioDto()
    {
        return usuarioDto;
    }

    public void setUsuarioDto( UsuarioDto usuarioDto )
    {
        this.usuarioDto = usuarioDto;
    }

    private ParroquiaDto parroquiaDto;

    public ParroquiaDto getParroquiaDto()
    {
        return parroquiaDto;
    }

    public void setParroquiaDto( ParroquiaDto parroquiaDto )
    {
        this.parroquiaDto = parroquiaDto;
    }

    private NivelAcademicoDto nivel_AcademicoDto;

    public NivelAcademicoDto getNivel_AcademicoDto()
    {
        return nivel_AcademicoDto;
    }

    public void setNivel_AcademicoDto( NivelAcademicoDto nivel_AcademicoDto )
    {
        this.nivel_AcademicoDto = nivel_AcademicoDto;
    }
}
