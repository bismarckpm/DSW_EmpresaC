package ucab.dsw.servicio;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.UsuarioDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path( "/login" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class Login extends AplicacionBase{
    @POST
    @Path( "/enter" )
    public String addUser(UsuarioDto usuarioDto)
    {
        String token="";
        try
        {
            UsuarioDto user = new UsuarioDto();
            user.setCorreoelectronico( "bismarckpmpruebaLDAP@gmail.com" );
            user.setContrasena( "MARIAPEPE" );
            DirectorioActivo ldap = new DirectorioActivo();
            ldap.userAuthentication( user );

        }
        catch ( Exception ex )
        {
            return ex.getMessage();
        }
        return  token;
    }
}
