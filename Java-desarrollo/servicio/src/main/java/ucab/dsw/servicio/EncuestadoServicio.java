package ucab.dsw.servicio;
import ucab.dsw.dtos.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.encuestado.*;
import ucab.dsw.logica.fabrica.Fabrica;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import ucab.dsw.jwt.Jwt;
import javax.ws.rs.*;

@Path( "/encuestado" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class EncuestadoServicio {

    /**
     * Esta funcion consiste en enviar los datos de los estudios que tiene asignado un encuestado
     * en especifico
     * @author Carlos Silva
     * @param _id corresponde al id del encuestado
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/estudios-asignados/{id}" )
    public Response consultaEstudios_asignados(@HeaderParam("authorization") String token,@PathParam("id")long  _id){
    JsonObject resul;

        try {
            if (Jwt.verificarToken(token)) {
            ConsultaEstudiosAsignadosComando comando = Fabrica.crearComandoConId(ConsultaEstudiosAsignadosComando.class, _id);
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
                    .add("codigo", "S-EX-ENC01")
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en enviar los datos de las preguntas relacionadas con la encuesta de un estudio
     * en especifico
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/encuesta-estudio/{id}" )
    public Response encuesta_estudio(@HeaderParam("authorization") String token,@PathParam("id")long  _id){
    JsonObject resul;

        try {
            if (Jwt.verificarToken(token)) {
            EncuestaEstudioComando comando = Fabrica.crearComandoConId(EncuestaEstudioComando.class, _id);
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
                    .add("codigo", "S-EX-ENC02")
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }
    /**
     * Esta funcion consiste en ingresar las respuestas de un encuestado
     * @author Carlos Silva
     * @param _id corresponde al id de la pregunta encuesta
     * @param _id2 corresponde al id del estudio
     * @param _id3 corresponde al id del encuestado
     * @param respuestaDto corresponde al objeto de la capa web que contiene los nuevos datos
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @PUT
    @Path( "/Respuesta/{id}/{id2}/{id3}" )
    public Response addRespuesta(@HeaderParam("authorization") String token,@PathParam("id") long  _id,@PathParam("id2") long  _id2,@PathParam("id3") long  _id3,RespuestaDto respuestaDto) {

        JsonObject resul;
        try {
            if (Jwt.verificarToken(token)) {
            AddRespuestaComando comando = Fabrica.crearComandoBoth3(AddRespuestaComando.class, _id,_id2,_id3,respuestaDto);
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
                    .add("codigo", "S-EX-ENC03")
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }
    /**
     * Esta funcion consiste en enviar los datos de las preguntas que el encuestado no a contestado
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @param _id2 corresponde al id del encuestado
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/pregunta-estudio/{id}/{id2}" )
    public Response pregunta_estudio(@HeaderParam("authorization") String token,@PathParam("id")long  _id,@PathParam("id2")long  _id2){
        JsonObject resul;

        try {
            if (Jwt.verificarToken(token)) {
            PreguntaEstudioComando comando = Fabrica.crearComandoConDobleId(PreguntaEstudioComando.class, _id,_id2);
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
                    .add("codigo", "S-EX-ENC04")
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }

    /**
     * Esta funcion consiste en cambiar el estado de la participacion del encuestado a inactivo cuando responde todas las preguntas
     * ademas en el caso de que sea el ultimo encuestado en responder el estado del estudio cambia a finalizado
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @param _id2 corresponde al id de la encuestado
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @DELETE
    @Path( "/finalizar/{id}/{id2}" )
    public Response finalizarParticipacion(@HeaderParam("authorization") String token,@PathParam("id") long  _id,@PathParam("id2")long  _id2){
        JsonObject resul;

        try {
            if (Jwt.verificarToken(token)) {
            FinalizarParticipacionComando comando = Fabrica.crearComandoConDobleId(FinalizarParticipacionComando.class, _id,_id2);
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
                    .add("codigo", "S-EX-ENC05")
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }


    /**
    * Esta funcion consiste en obtener el id del encuestado
    * @author Gabriel Romero
    * @param _id corresponde al id del usuario
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, encuestado_id 
    *         y mensaje.
    */
    @GET
    @Path("/get-id/{_id}")
    public Response getEncuestadoId(@HeaderParam("authorization") String token,@PathParam("_id") long _id) {
        JsonObject resul;

        try {
            if (Jwt.verificarToken(token)) {
            GetEncuestadoIdComando comando = Fabrica.crearComandoConId(GetEncuestadoIdComando.class, _id);
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
                    .add("codigo", "S-EX-ENC06")
                    .add("mensaje", "Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
    }
}
