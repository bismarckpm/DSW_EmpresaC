package ucab.dsw.servicio;

import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.cliente.*;
import ucab.dsw.logica.fabrica.Fabrica;
import javax.json.*;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.*;

/**
 * Una clase que contiene un conjunto de metodos y/o funciones correspondiente al cliente
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 */

@Path( "/cliente" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class ClienteServicio {


    /**
    * Esta funcion consiste en traer los estudios que tiene un cliente
    * @author Gabriel Romero
    * @param _id corresponde al id del cliente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones
    */
    @GET
    @Path("/estudios/{_id}")
    public Response consultaEstudios_Solicitados(@PathParam("_id") long _id) {
        JsonObject resul;
        try {
            if(Jwt.verificarToken(token)){
            ConsultaEstudiosSolicitadosComando comando = Fabrica.crearComandoConId(ConsultaEstudiosSolicitadosComando.class, _id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                resul = Json.createObjectBuilder()
                        .add("estado", "unauthorized")
                        .add("codigo", "UNAUTH")
                        .add("mensaje", "No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(resul).build();
            }

        } catch (EmpresaException ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "error")
                    .add("codigo", ex.getCodigo())
                    .add("mensaje", ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "error")
                    .add("codigo", "S-EX-CLI01")
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
    * Esta funcion consiste en obtener el id del cliente
    * @author Gabriel Romero
    * @param _id corresponde al id del usuario
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, cliente_id 
    *         y mensaje.
    */
   @GET
    @Path("/get-id/{_id}")
    public Response getClienteId(@PathParam("_id") long _id) {
        JsonObject resul;
        try {
            if(Jwt.verificarToken(token)){
            GetClienteIdComando comando = Fabrica.crearComandoConId(GetClienteIdComando.class, _id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                resul = Json.createObjectBuilder()
                        .add("estado", "unauthorized")
                        .add("codigo", "UNAUTH")
                        .add("mensaje", "No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(resul).build();
            }

        } catch (EmpresaException ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "error")
                    .add("codigo", ex.getCodigo())
                    .add("mensaje", ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "error")
                    .add("codigo", "S-EX-CLI02")
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
   }
    /**
     * Esta funcion consiste en obtener la respuesta de un estudio en especifico
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, cliente_id
     *         y mensaje.
     */
    @GET
    @Path("/respuesta-analista/{id}")
    public Response respuesta_analista(@PathParam("id") long _id) {
        JsonObject resul;
        try {
            if(Jwt.verificarToken(token)){
            RespuestaAnalistaComando comando = Fabrica.crearComandoConId(RespuestaAnalistaComando.class, _id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
            } else {
                resul = Json.createObjectBuilder()
                        .add("estado", "unauthorized")
                        .add("codigo", "UNAUTH")
                        .add("mensaje", "No se encuentra autenticado. Inicie sesión").build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(resul).build();
            }

        } catch (EmpresaException ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "error")
                    .add("codigo", ex.getCodigo())
                    .add("mensaje", ex.getMensaje()).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(resul).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "error")
                    .add("codigo", "S-EX-CLI03")
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }
}
