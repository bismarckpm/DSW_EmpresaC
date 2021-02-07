package ucab.dsw.servicio;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.UsuarioExistenteExcepcion;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.logica.comando.registro.RegistroComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Clase para la gestion del registro de un nuevo encuestado
 * @author Jesus Requena
 */
@Path( "" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class RegistroServicio extends AplicacionBase{

    /**
     * Metodo para añadrir un nuevo encuestado e iniciar sesión
     * despues de verificar que no existen coincidencias con el registro en la base de datos
     * @author Jesus Requena
     * @param nuevoEncuestadoDto precargado con el conjunto de datos a ingresar en el registro
     * @return Response que incluye un estado de respuesta http (OK, UNAUTHHORIZED o BAD_REQUEST) para
     *         indicar si exectivamente se pudo completar la solicitud, se encontro un usuario o correo electronico
     *         ya registrado u ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json con los datos de inicio de sesion (id, token y rol) en caso de
     *         respuesta OK
     */
    @POST
    @Path( "/registro" )
    public Response registro( NuevoEncuestadoDto nuevoEncuestadoDto ){
        try{
            RegistroComando comando = Fabrica.crearComandoConDto(RegistroComando.class, nuevoEncuestadoDto);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        } catch ( EmpresaException ex ) {
            ex.printStackTrace();
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        } catch ( Exception ex ) {
            ex.printStackTrace();
            JsonObject data = Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-RG01")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(data).build();
        }
    }
}
