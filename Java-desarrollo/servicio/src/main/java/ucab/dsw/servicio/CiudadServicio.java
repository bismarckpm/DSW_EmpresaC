package ucab.dsw.servicio;

import ucab.dsw.logica.comando.ciudad.AllCiudadesComando;
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
 * Una clase para la administracion de las ciudades 
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 */
@Path( "/ciudad" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class CiudadServicio extends AplicacionBase{

    /**
    * Esta funcion consiste el traer todas las ciudades disponibles
    * @author Gabriel Romero
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, ciudades (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones.
    */
    @GET
    @Path( "/all" )
    public Response getAllCiudades()
    {
        JsonObject resul;
        try
        {
            AllCiudadesComando comando= Fabrica.crear(AllCiudadesComando.class);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();


        }

    }
}
