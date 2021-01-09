package ucab.dsw.dtos;


public class NuevoEncuestadoDto extends BaseDto {
    private UsuarioLdapDto usuarioLdap;
    private EncuestadoDto encuestado;
    private TelefonoDto telefono[];
    private MetodoConexionDto metodo_conexion[];
    private HijoDto hijo[];
    private OcupacionDto ocupacion[];

    public UsuarioLdapDto getUsuarioLdap() { return usuarioLdap; }

    public void setUsuarioLdap(UsuarioLdapDto usuarioLdap) { this.usuarioLdap = usuarioLdap; }

    public EncuestadoDto getEncuestado() { return encuestado; }

    public void setEncuestado(EncuestadoDto encuestado) { this.encuestado = encuestado; }

    public TelefonoDto[] getTelefono() { return telefono; }

    public void setTelefono(TelefonoDto[] telefono) { this.telefono = telefono; }

    public MetodoConexionDto[] getMetodo_conexion() { return metodo_conexion; }

    public void setMetodo_conexion(MetodoConexionDto[] metodo_conexion) { this.metodo_conexion = metodo_conexion; }

    public HijoDto[] getHijo() { return hijo; }

    public void setHijo(HijoDto[] hijo) { this.hijo = hijo; }

    public OcupacionDto[] getOcupacion() { return ocupacion; }

    public void setOcupacion(OcupacionDto[] ocupacion) { this.ocupacion = ocupacion; }
}
