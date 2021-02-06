package ucab.dsw.servicio;

import ucab.dsw.logica.comando.estado.AllEstadosComando;
import ucab.dsw.logica.fabrica.Fabrica;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Una clase para la administracion de los estados
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 */
@Path( "/estado" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class EstadoServicio extends AplicacionBase {

    /**
    * Esta funcion consiste el traer todas los estados disponibles
    * @author Gabriel Romero
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, estados (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones.
    */
    @GET
    @Path( "/all" )
    public Response getAllEstados()
    {
        JsonObject resul;
        try
        {
            AllEstadosComando comando= Fabrica.crear(AllEstadosComando.class);
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
