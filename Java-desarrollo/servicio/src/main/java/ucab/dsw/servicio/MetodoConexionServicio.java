package ucab.dsw.servicio;


import ucab.dsw.logica.comando.metodoConexion.AllMetodoConexionComando;
import ucab.dsw.logica.fabrica.Fabrica;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Una clase para la administracion los metodos de conexion
 * @version 1.0, 02/01/2021
 * @author Jesus Requena
 */

@Path( "/metodos_conexion" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class MetodoConexionServicio {
    
    /**
    * Esta funcion consiste en traer todos los metodos de conexion disponibles
    * @author Jesus Requena
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, metodos_conexion (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones.
    */
    @GET
    @Path("/all")
    public Response getAllMetodoConexion() {
        JsonObject resul;
        try {
            AllMetodoConexionComando comando= Fabrica.crear(AllMetodoConexionComando.class);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        } catch (Exception ex) {

            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }

    }


}
