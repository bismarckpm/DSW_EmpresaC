package ucab.dsw.dtos;

public class ParticipacionDto {


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

    private SolicituEstudioDto solicituEstudioDto;

    public SolicituEstudioDto getSolicituEstudioDto()
    {
        return solicituEstudioDto;
    }

    public void setSolicituEstudioDto( SolicituEstudioDto solicituEstudioDto )
    {
        this.solicituEstudioDto = solicituEstudioDto;
    }

}
