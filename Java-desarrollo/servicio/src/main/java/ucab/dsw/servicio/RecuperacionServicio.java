package ucab.dsw.servicio;

import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.directorio.RecuperacionPass;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.jwt.Jwt;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Clase para la gestion de la recuperación de contraseñas a traves de un envio por correo electronico
 * @author Jesus Requena
 */
@Path( "" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class RecuperacionServicio extends AplicacionBase{

    /**
     * Metodo para enviar un correo electronico con una contraseñá alfanumerica generada aleatoriamente
     * despues de verificar la existencia del correo electronico en el Directorio Activo
     * @author Jesus Requena
     * @param usuarioLdapDto precargado con el correo electronico para enviar el mensaje
     * @return Response que incluye un estado de respuesta http (OK, UNAUTHHORIZED o BAD_REQUEST) para
     *         indicar si exectivamente se pudo completar la solicitud, no se encontró el correo electronico
     *         u ocurrió un fallo en la comunicación.
     */
    @POST
    @Path( "/recuperacion" )
    public Response recuperacion(UsuarioLdapDto usuarioLdapDto) {
        try {
            DirectorioActivo ldap = new DirectorioActivo();
            RecuperacionPass rec = new RecuperacionPass();
            String newPass = rec.newPass();
            String user = ldap.getUserFromMail(usuarioLdapDto);

            if(!user.equals("")){
                usuarioLdapDto.setCn(user);
                ldap.reSetPass(usuarioLdapDto,newPass);
                rec.recuperar(usuarioLdapDto.getCorreoelectronico(), newPass);
                return Response.status(Response.Status.OK).build();
            }else{
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        }catch ( Exception ex ) {
            System.out.println("Excepcion");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
}
