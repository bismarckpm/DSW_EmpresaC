package ucab.dsw.servicio;

import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.directorio.RecuperacionPass;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.excepciones.UsuarioExistenteExcepcion;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.logica.comando.recuperacion.RecuperacionComando;
import ucab.dsw.logica.fabrica.Fabrica;

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
            RecuperacionComando comando = Fabrica.crearComandoConDto(RecuperacionComando.class, usuarioLdapDto);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }catch ( UsuarioExistenteExcepcion ex ){
            ex.printStackTrace();
            System.out.println(ex.getMensaje());
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","unauthorized")
                    .add("mensaje_soporte",ex.getMensaje())
                    .add("mensaje","Usuario no existente").build();

            return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
        } catch ( Exception ex ) {
            ex.printStackTrace();
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","internal_server_error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }

    }
}
