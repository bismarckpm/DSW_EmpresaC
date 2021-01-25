package ucab.dsw.servicio;
import ucab.dsw.dtos.*;
import ucab.dsw.logica.comando.solicitud.AddSolicitudComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.json.Json;
import javax.ws.rs.core.Response;


/**
 * Una clase para la administracion completa de las solicitudes de estudio
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 */
@Path( "/solicitud" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class SolicitudEstudioServicio {

    /**
    * Esta funcion consiste en insertar una nueva solicitud de estudio
    * @author Gabriel Romero
    * @param solicitudEstudioDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado y mensaje en caso de ocurrir
    *         alguna de las excepciones
    */
    @POST
    @Path( "/add" )
    public Response addSolicitud(SolicitudEstudioDto solicitudEstudioDto)
    {
        JsonObject resul;
        try
        {
            AddSolicitudComando comando= Fabrica.crearComandoConDto(AddSolicitudComando.class, solicitudEstudioDto);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        }
        catch(Exception ex){
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }

    }


}
