package ucab.dsw.dtos;


import java.util.Date;

public class SolicitudEstudioDto extends BaseDto {


    public SolicitudEstudioDto()
    {
    }

    public SolicitudEstudioDto ( long id ) throws Exception
    {
        super( id );
    }

    private Date fecha_inicio;

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }


    private Date fecha_fin;

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }


    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    private String resultadoanalista;

    public String getResultadoanalista() {
        return resultadoanalista;
    }

    public void setResultadoanalista(String resultadoanalista) {
        this.resultadoanalista = resultadoanalista;
    }


    private String modoencuesta;

    public String getModoencuesta() {
        return modoencuesta;
    }

    public void setModoencuesta(String modoencuesta) {
        this.modoencuesta = modoencuesta;
    }


    private EncuestaDto encuestaDto;

    public EncuestaDto getEncuestaDto()
    {
        return encuestaDto;
    }

    public void setEncuestaDto( EncuestaDto encuestaDto )
    {
        this.encuestaDto = encuestaDto;
    }

    private MarcaDto marcaDto;

    public MarcaDto getMarcaDto()
    {
        return marcaDto;
    }

    public void setMarcaDto( MarcaDto marcaDto)
    {
        this.marcaDto = marcaDto;
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

    private UsuarioDto usuarioDto2;

    public UsuarioDto getUsuarioDto2()
    {
        return usuarioDto2;
    }

    public void setUsuarioDto2( UsuarioDto usuarioDto2 )
    {
        this.usuarioDto2 = usuarioDto2;
    }

    private ClienteDto clienteDto;

    public ClienteDto getClienteDto()
    {
        return clienteDto;
    }

    public void setClienteDto( ClienteDto clienteDto )
    {
        this.clienteDto = clienteDto;
    }

    private CaracteristicaDemograficaDto caracteristica_DemograficaDto;

    public CaracteristicaDemograficaDto getCaracteristica_DemograficaDto()
    {
        return caracteristica_DemograficaDto;
    }

    public void setCaracteristica_DemograficaDto( CaracteristicaDemograficaDto caracteristica_DemograficaDto )
    {
        this.caracteristica_DemograficaDto = caracteristica_DemograficaDto;
    }


}
