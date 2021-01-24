package ucab.dsw.servicio;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import ucab.dsw.logica.comando.admin.*;

import javax.ws.rs.*;

@Path( "/admin" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class AdminServicio {

    /**
     * Esta funcion consiste en enviar los datos de los estudios que tiene asignado un admin
     * en especifico y que ya se le asigno una encuesta
     *
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     * se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     * en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     * y mensaje en caso de ocurrir alguna de las excepciones
     * @author Carlos Silva
     */


    @GET
    @Path("/estudios-asignados/{id}")
    public Response consultaEstudios_asignados(@PathParam("id") long _id) {
        JsonObject resul;

        try {
            ConsultaEstudiosAsignadosComando comando = Fabrica.crearComandoConId(ConsultaEstudiosAsignadosComando.class, _id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "internal_server_error")
                    .add("mensaje_soporte", ex.getMessage())
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en enviar los datos de los estudios que tiene asignado un admin
     * en especifico y que no se le a asignado una encuesta
     *
     * @param _id corresponde al id del admin
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     * se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     * en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     * y mensaje en caso de ocurrir alguna de las excepciones
     * @author Carlos Silva
     */
    @GET
    @Path("/estudios-no-asignados/{id}")
    public Response consultaEstudios_no_asignados(@PathParam("id") long _id) {
        JsonObject resul;

        try {
            ConsultaEstudiosNoAsignadosComando comando = Fabrica.crearComandoConId(ConsultaEstudiosNoAsignadosComando.class, _id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "internal_server_error")
                    .add("mensaje_soporte", ex.getMessage())
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en cambiar el estado de un estudio a inactivo
     *
     * @param _id corresponde al id del estudio
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     * se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     * en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     * y mensaje en caso de ocurrir alguna de las excepciones
     * @author Carlos Silva
     */

    @DELETE
    @Path("/delete-solicitud/{id}")
    public Response EliminarEstudio(@PathParam("id") long _id) {
        JsonObject resul;
        try {
            EliminarEstudioComando comando = Fabrica.crearComandoConId(EliminarEstudioComando.class, _id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "internal_server_error")
                    .add("mensaje_soporte", ex.getMessage())
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en asignarle una encuesta a un estudio creandolo y
     * asignandole un analista aleatorio, aparte tambien recibe una lista de preguntas
     * que se le asignan a la encuesta y otra lista de participantes que se le asignaron al estudio
     *
     * @param _id         corresponde al id de la marca
     * @param _id2        corresponde al id del estudio
     * @param encuestaDto corresponde al objeto de la capa web que contiene los nuevos datos
     *                    que se van a ingresar y las listas de participantes y preguntas
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     * se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     * en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     * y mensaje en caso de ocurrir alguna de las excepciones
     * @author Carlos Silva
     */
    @PUT
    @Path("/addEncuesta/{id}/{id2}")
    public Response addEncuesta(@PathParam("id") long _id, @PathParam("id2") long _id2, EncuestaDto encuestaDto) {
        JsonObject resul;
        try {
            AddEncuestaComando comando = Fabrica.crearComandoBoth2(AddEncuestaComando.class, _id,_id2,encuestaDto);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "internal_server_error")
                    .add("mensaje_soporte", ex.getMessage())
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en ingresar nuevas preguntas y si la pregunta es de tipo opcion
     * simple o multiple ingresar nuevas opciones para ellas
     *
     * @param preguntaDto corresponde al objeto de la capa web que contiene los nuevos datos
     *                    que se van a ingresar y la lista de opnciones que se le puede asignar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     * se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     * en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     * y mensaje en caso de ocurrir alguna de las excepciones
     * @author Carlos Silva
     */
    @PUT
    @Path("/addPregunta")
    public Response addPregunta(PreguntaDto preguntaDto) {
        JsonObject resul;
        try {
            AddPreguntaComando comando = Fabrica.crearComandoConDto(AddPreguntaComando.class, preguntaDto);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "internal_server_error")
                    .add("mensaje_soporte", ex.getMessage())
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en enviar los datos de los participantes que tiene un estudio
     *
     * @param _id corresponde al del estudio
     *            que se van a ingresar y la lista de opnciones que se le puede asignar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     * se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     * en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     * y mensaje en caso de ocurrir alguna de las excepciones
     * @author Carlos Silva
     */
    @GET
    @Path("/estudios-participacion/{id}")
    public Response Participacion_estudio(@PathParam("id") long _id) {
        JsonObject resul;
        try {
            ParticipacionEstudioComando comando = Fabrica.crearComandoConId(ParticipacionEstudioComando.class, _id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "internal_server_error")
                    .add("mensaje_soporte", ex.getMessage())
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en enviar los datos de un estudio en especifico
     *
     * @param _id corresponde al del estudio
     *            que se van a ingresar y la lista de opnciones que se le puede asignar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     * se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     * en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     * y mensaje en caso de ocurrir alguna de las excepciones
     * @author Carlos Silva
     */
    @GET
    @Path("/estudio/{id}")
    public Response buscarEstudio(@PathParam("id") long _id) {
        JsonObject resul;
        try {
            BuscarEstudioComando comando = Fabrica.crearComandoConId(BuscarEstudioComando.class, _id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "internal_server_error")
                    .add("mensaje_soporte", ex.getMessage())
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en enviar los datos de las preguntas que estan relacionadas a una categoria
     *
     * @param _id corresponde al de la categoria
     *            que se van a ingresar y la lista de opnciones que se le puede asignar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     * se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     * en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     * y mensaje en caso de ocurrir alguna de las excepciones
     * @author Carlos Silva
     */

    @GET
    @Path("/preguntas-categoria/{id}")
    public Response Preguntas_categoria(@PathParam("id") long _id) {
        JsonObject resul;
        try {
            PreguntasCategoriaComando comando = Fabrica.crearComandoConId(PreguntasCategoriaComando.class, _id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "internal_server_error")
                    .add("mensaje_soporte", ex.getMessage())
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en enviar los datos de los posibles participantes de un estudio
     * verificando sus datos con las caracteristicas demograficas
     *
     * @param _id corresponde al del estudio
     *            que se van a ingresar y la lista de opnciones que se le puede asigbar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     * se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     * en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     * y mensaje en caso de ocurrir alguna de las excepciones
     * @author Carlos Silva
     */
    @GET
    @Path("/sugerencia-participacion/{id}")
    public Response add_Participacion(@PathParam("id") long _id) {
        JsonObject resul;
        try {
            AddParticipacionComando comando = Fabrica.crearComandoConId(AddParticipacionComando.class, _id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            resul = Json.createObjectBuilder()
                    .add("estado", "internal_server_error")
                    .add("mensaje_soporte", ex.getMessage())
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }
}