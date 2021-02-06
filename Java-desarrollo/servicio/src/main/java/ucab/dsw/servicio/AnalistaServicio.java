package ucab.dsw.servicio;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.comando.analista.*;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.*;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Response;

import javax.ws.rs.*;


/**
 * Una clase que contiene un conjunto de metodos y/o funciones correspondiente al analista
 * @version 1.0, 02/01/2021
 * @author Carlos Silva
 * @author Gabriel Romero
 */

@Path( "/analista" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class AnalistaServicio {


    /**
    * Esta funcion consiste en traer los estudios que tiene un analista
    * @author Gabriel Romero
    * @param _id corresponde al id del analista
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones
    */
    @GET
    @Path("/estudios/{_id}")
    public Response consultaEstudios_asignados(@PathParam("_id") long _id) {

        JsonObject resul;

        try {
            EstudiosAsignadosComando comando= Fabrica.crearComandoConId(EstudiosAsignadosComando.class,_id);
            comando.execute();

            System.out.println(comando.getResult());
            return Response.status(Response.Status.OK).entity(comando.getResult()).build();



        }
        catch (Exception ex){
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }
    /**
     * Esta funcion consiste en cambiar el estado de un estudio al que ya se le asigno una encuesta
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @PUT
    @Path( "/empezar-estudio/{id}" )
    public Response Empezar_estudio(@PathParam("id") long  _id )
    {
        try {
            EmpezarEstudioComando comando= Fabrica.crearComandoConId(EmpezarEstudioComando.class,_id);
            comando.execute();

            System.out.println(comando.getResult());
            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            JsonObject resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();

        }


    }

    /**
     * Esta funcion consiste en cambiar el estado de una una participacion a inactivo
     * @author Carlos Silva
     * @param _id corresponde al id de la participacion
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @PUT
    @Path( "/eliminar-participacion/{id}" )
    public Response Eliminar_Participacion(@PathParam("id") long  _id )
    {
        try
        {
            DeleteParticipacionComando comando=Fabrica.crearComandoConId(DeleteParticipacionComando.class,_id);
            comando.execute();

            System.out.println(comando.getResult());
            return Response.status(Response.Status.OK).entity(comando.getResult()).build();


        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            JsonObject resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
    * Esta funcion consiste en traer los estudios por telefono que tiene un analista
    * @author Carlos Silva
    * @author Gabriel Romero
    * @param _id corresponde al id del analista
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones
    */
    @GET
    @Path("/estudios-telefono/{_id}")
    public Response consultaEstudios_telefono(@PathParam("_id") long _id) {

        try {
            EstudiosTelefonoComando comando= Fabrica.crearComandoConId(EstudiosTelefonoComando.class,_id);
            comando.execute();

            System.out.println(comando.getResult());
            return Response.status(Response.Status.OK).entity(comando.getResult()).build();


        }
        catch (Exception ex){
            ex.printStackTrace();
            JsonObject resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();

        }
    }

    /**
     * Esta funcion permite que el analista ponga su comentario final en el estudio
     * @author Carlos Silva
     * @param respuestaAnalistaDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @PUT
    @Path( "/responder-solicitud" )
    public Response ResponderEstudio( RespuestaAnalistaDto respuestaAnalistaDto)
    {
        try
        {
            ResponderEstudioComando comando= Fabrica.crearComandoConDto(ResponderEstudioComando.class,respuestaAnalistaDto);
            comando.execute();

            System.out.println(comando.getResult());
            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            JsonObject resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en mostrar todos todas las respuestas que tiene una encuesta segun su estudio
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @GET
    @Path( "/respuestas-estudio/{id}" )
    public Response respuestas_estudio(@PathParam("id")long  _id)
    {
        try {

            RespuestasEstudioComando comando= Fabrica.crearComandoConId(RespuestasEstudioComando.class,_id);
            comando.execute();

            System.out.println(comando.getResult());
            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        }
        catch (Exception ex){
            ex.printStackTrace();
            JsonObject resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en enviar los datos que requieren las graficas de respuesra por estudio
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @GET
    @Path( "/graficos-estudio/{id}" )
    public Response respuestas_porcentaje(@PathParam("id")long  _id)
    {
        try {
            RespuestasPorcentajeComando comando= Fabrica.crearComandoConId(RespuestasPorcentajeComando.class,_id);
            comando.execute();

            System.out.println(comando.getResult());
            return Response.status(Response.Status.OK).entity(comando.getResult()).build();

        }
        catch (Exception ex){
            ex.printStackTrace();
            JsonObject resul= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }


}
