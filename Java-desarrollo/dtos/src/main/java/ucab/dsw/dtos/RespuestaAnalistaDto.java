package ucab.dsw.dtos;

public class RespuestaAnalistaDto extends BaseDto
{
    private String respueta;

    public String getRespueta()
    {
        return respueta;
    }

    public void setRespueta( String respueta )
    {
        this.respueta = respueta;
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