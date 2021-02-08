package ucab.dsw.servicio;


import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.logica.comando.pais.AllPaisComando;
import ucab.dsw.logica.fabrica.Fabrica;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Una clase para la administracion de los paises
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 */
@Path( "/pais" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class PaisServicio {

    /**
    * Esta funcion consiste el traer todas los paises disponibles
    * @author Gabriel Romero
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, paises (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones.
    */
    @GET
    @Path( "/all" )
    public Response getAllPaises(@HeaderParam("authorization") String token)
    {
        JsonObject resul;
        try
        {
            if(Jwt.verificarToken(token)){
                AllPaisComando comando= Fabrica.crear(AllPaisComando.class);
                comando.execute();

                return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            }
            else{
                resul= Json.createObjectBuilder()
                        .add("estado","unauthorized")
                        .add("codigo","UNAUTH")
                        .add("mensaje","No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(resul).build();
            }

        }
        catch ( EmpresaException ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo",ex.getCodigo())
                    .add("mensaje",ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("codigo","S-EX-PAI01")
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();

        }
    }
}
