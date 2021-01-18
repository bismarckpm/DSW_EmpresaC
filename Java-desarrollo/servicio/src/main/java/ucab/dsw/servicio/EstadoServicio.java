package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoEstado;
import ucab.dsw.entidades.Estado;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

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
        JsonObject data;
        try
        {
            DaoEstado dao= Fabrica.crear(DaoEstado.class);
            List<Estado> resultado= dao.findAll(Estado.class);

            JsonArrayBuilder estadosArrayJson= Json.createArrayBuilder();

            for(Estado obj: resultado){

                JsonObject parroquia = Json.createObjectBuilder().add("id",obj.get_id())
                                                                .add("nombre",obj.get_nombre())
                                                                .add("pais_id",obj.get_pais().get_id()).build();

                estadosArrayJson.add(parroquia);

            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("estados",estadosArrayJson).build();


        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            System.out.println(data);
            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();


        }

        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();

    }
}
