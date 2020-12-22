package ucab.dsw.dtos;

public class ParticipacionDto extends DtoBase{


    private String estado;

    public String getEstado()
    {
        return estado;
    }

    public void setEstado( String estado )
    {
        this.estado = estado;
    }

    private EncuestadoDto encuestadoDto;

    public EncuestadoDto getEncuestadoDto()
    {
        return encuestadoDto;
    }

    public void setEncuestadoDto( EncuestadoDto encuestadoDto )
    {
        this.encuestadoDto = encuestadoDto;
    }

    private SolicitudEstudioDto solicituEstudioDto;

    public SolicitudEstudioDto getSolicituEstudioDto()
    {
        return solicituEstudioDto;
    }

    public void setSolicituEstudioDto( SolicitudEstudioDto solicituEstudioDto )
    {
        this.solicituEstudioDto = solicituEstudioDto;
    }

}
