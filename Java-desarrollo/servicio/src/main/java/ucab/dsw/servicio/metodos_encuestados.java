package ucab.dsw.servicio;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;

import javax.validation.constraints.Null;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.json.JsonArrayBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path( "/encuestado" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class metodos_encuestados {

    /**
     * Esta funcion consiste en enviar los datos de los estudios que tiene asignado un encuestado
     * en especifico
     * @author Carlos Silva
     * @param _id corresponde al id del encuestado
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/estudios-asignados/{id}" )
    public Response consultaEstudios_asignados(@PathParam("id")long  _id)
    {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<Participacion> resultado = null;

            DaoParticipacion dao = new DaoParticipacion();
            DaoMarca daoMarca = new DaoMarca();
            DaoSolicitudEstudio  daoSolicitudEstudio = new DaoSolicitudEstudio();
            DaoSubcategoria daoSubcategoria = new DaoSubcategoria ();
            DaoCategoria daoCategoria = new DaoCategoria();

            Class<Participacion> type = Participacion.class;

            resultado = dao.findAll(type);
            for (Participacion obj : resultado) {
                Participacion participacion = dao.find(obj.get_id(), Participacion.class);
                Marca marca = daoMarca.find(participacion.get_solicitudestudio().get_marca().get_id(), Marca.class);
                Subcategoria subcategoria = daoSubcategoria.find(marca.get_subcategoria().get_id(),Subcategoria.class);
                Categoria categoria = daoCategoria.find(subcategoria.get_categoria().get_id(),Categoria.class);
                if (participacion.get_encuestado().get_id() == _id && participacion.get_solicitudestudio().get_estado().equals("en progreso") && participacion.get_estado().equals("activo") ) {
                    JsonObject encuesta = Json.createObjectBuilder().add("Marca", marca.get_nombre())
                            .add("idcategoria", categoria.get_id())
                            .add("Categoria", categoria.get_nombre())
                            .add("idsubcategoria", subcategoria.get_id())
                            .add("Subcategoria", subcategoria.get_nombre()).build();

                    SolicitudEstudio solicitudEstudio = daoSolicitudEstudio.find(participacion.get_solicitudestudio().get_id(), SolicitudEstudio.class);

                    String nombre_encuesta = "";
                    if (solicitudEstudio.get_encuesta()==null){
                        nombre_encuesta = "Encuesta sin nombre";
                    }else{
                        nombre_encuesta = solicitudEstudio.get_encuesta().get_nombre();
                    }
                    JsonObject tipo = Json.createObjectBuilder().add("id", participacion.get_solicitudestudio().get_id())
                            .add("fecha", solicitudEstudio.get_fecha_inicio().toString())
                            .add("caracteristicas", encuesta)
                            .add("estado",solicitudEstudio.get_estado())
                            .add("modo_encuesta", solicitudEstudio.get_modoencuesta())
                            .add("nombre_encuesta",nombre_encuesta).build();


                    builder.add(tipo);
                    System.out.println("id" + obj.get_id());
                }
            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("estudios",builder).build();

        }
        catch (Exception ex){
            String problema = ex.getMessage();
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        //builder.build();
        return Response.status(Response.Status.OK).entity(data).build();
    }

    /**
     * Esta funcion consiste en enviar los datos de las preguntas relacionadas con la encuesta de un estudio
     * en especifico
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/encuesta-estudio/{id}" )
    public Response encuesta_estudio(@PathParam("id")long  _id)
    {
        JsonObject data;
        JsonArrayBuilder opciones =Json.createArrayBuilder();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<PreguntaEncuesta> resultado = null;
            List<Opcion_Simple_Multiple_Pregunta> resultado2 = null;

            DaoPreguntaEncuesta dao = new DaoPreguntaEncuesta();
            DaoSolicitudEstudio dao2 = new DaoSolicitudEstudio();
            DaoOpcion_Simple_Multiple_Pregunta dao3 = new DaoOpcion_Simple_Multiple_Pregunta();

            SolicitudEstudio solicitudEstudio = new SolicitudEstudio();

            solicitudEstudio = dao2.find(_id,SolicitudEstudio.class);

            Class<PreguntaEncuesta> type = PreguntaEncuesta.class;

            resultado = dao.findAll(type);

            Class<Opcion_Simple_Multiple_Pregunta> type2 = Opcion_Simple_Multiple_Pregunta.class;

            resultado2 = dao3.findAll(type2);
            for (PreguntaEncuesta obj : resultado) {
                PreguntaEncuesta preguntaEncuesta = dao.find(obj.get_id(), PreguntaEncuesta.class);

                if (preguntaEncuesta.get_encuesta().get_id() == solicitudEstudio.get_encuesta().get_id() ) {


                    if (preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Opcion simple") || preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Opcion multiple") ){
                        for (Opcion_Simple_Multiple_Pregunta obj2 : resultado2) {
                            Opcion_Simple_Multiple_Pregunta opcion = dao3.find(obj2.get_id(), Opcion_Simple_Multiple_Pregunta.class);
                            if(opcion.get_pregunta().get_id() == preguntaEncuesta.get_pregunta().get_id()){

                                opciones.add(Json.createObjectBuilder().add("id", opcion.get_id())
                                        .add("opcion", opcion.get_opcionsimplemultiple().get_opcion()));
                            }


                        }
                        JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                .add("descripcion", preguntaEncuesta.get_pregunta().get_descripcion())
                                .add("tipopregunta", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                .add("opciones",opciones)
                                .build();

                        builder.add(preguntas);
                    }
                    else {

                        if (preguntaEncuesta.get_pregunta().get_valormax() !=0){
                            JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                    .add("descripcion", preguntaEncuesta.get_pregunta().get_descripcion())
                                    .add("tipopregunta", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                    .add("minimo", preguntaEncuesta.get_pregunta().get_valormin())
                                    .add("maximo", preguntaEncuesta.get_pregunta().get_valormax()).build();
                            builder.add(preguntas);

                        }else {
                            JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                    .add("descripcion", preguntaEncuesta.get_pregunta().get_descripcion())
                                    .add("tipopregunta", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                    .build();

                            builder.add(preguntas);
                        }

                    }

                }
            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("Preguntas",builder).build();

        }
        catch (Exception ex){
            String problema = ex.getMessage();
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        //builder.build();
        return Response.status(Response.Status.OK).entity(data).build();
    }
    /**
     * Esta funcion consiste en ingresar las respuestas de un encuestado
     * @author Carlos Silva
     * @param _id corresponde al id de la pregunta encuesta
     * @param _id2 corresponde al id del estudio
     * @param _id3 corresponde al id de la participacion
     * @param respuestaDto corresponde al objeto de la capa web que contiene los nuevos datos
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @PUT
    @Path( "/Respuesta/{id}/{id2}/{id3}" )
    public Response addRespuesta(@PathParam("id") long  _id,@PathParam("id2") long  _id2,@PathParam("id3") long  _id3,RespuestaDto respuestaDto)
    {
        RespuestaDto resultado = new RespuestaDto();
        JsonObject data;
        try
        {
            DaoRespuesta daoRespuesta = new DaoRespuesta();
            DaoPreguntaEncuesta daoPreguntaEncuesta= new DaoPreguntaEncuesta();
            DaoParticipacion daoParticipacion = new DaoParticipacion();

            List<Participacion> participacion = null;
            Class<Participacion> type = Participacion.class;
            participacion = daoParticipacion.findAll(type);

            Respuesta respuesta = new Respuesta();
            PreguntaEncuesta pregunta = daoPreguntaEncuesta.find(_id,PreguntaEncuesta.class);
            respuesta.set_preguntaencuesta(pregunta);

            for (Participacion obj : participacion) {
                if (obj.get_solicitudestudio().get_id()==_id2 && obj.get_encuestado().get_id()==_id3) {
                    Participacion participacion1 = daoParticipacion.find(obj.get_id(), Participacion.class);
                    respuesta.set_participacion(participacion1);
                }
            }

            respuesta.set_respuestarango(respuestaDto.getRespuestarango());
            respuesta.set_respuestadesarrollo(respuestaDto.getRespuestadesarrollo());
            respuesta.set_respuestaboolean(respuestaDto.getRespuestaboolean());



            Respuesta resul = daoRespuesta.insert( respuesta);
            resultado.setId( resul.get_id() );

            if (respuestaDto.getOpciones()!=null) {

                List<Opcion_Simple_Multiple_PreguntaDto> opciones = respuestaDto.getOpciones();

                for (Opcion_Simple_Multiple_PreguntaDto obj : opciones) {
                    DaoOpcion_Simple_Multiple_Pregunta daoOpcion_simple_multiple_pregunta= new DaoOpcion_Simple_Multiple_Pregunta();
                    DaoRespuestaOpcion daoRespuestaOpcion= new DaoRespuestaOpcion();
                    Respuesta_Opcion resultado2 = new Respuesta_Opcion();
                    RespuestaOpcion respuestaOpcion = new RespuestaOpcion();
                    respuestaOpcion.set_respuesta(resul);
                    Opcion_Simple_Multiple_Pregunta opcion =daoOpcion_simple_multiple_pregunta.find(obj.getId(), Opcion_Simple_Multiple_Pregunta.class);
                    respuestaOpcion.set_opcionsimplemultiple(opcion);

                    RespuestaOpcion resul2 = daoRespuestaOpcion.insert(respuestaOpcion);
                    resultado2.setId(resul2.get_id());


                }
            }
            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();

        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();

            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();


            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        return Response.status(Response.Status.OK).entity(data).build();
    }
    /**
     * Esta funcion consiste en enviar los datos de las preguntas que el encuestado no a contestado
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @param _id2 corresponde al id de la participacion
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path( "/pregunta-estudio/{id}/{id2}" )
    public Response pregunta_estudio(@PathParam("id")long  _id,@PathParam("id2")long  _id2)
    {
        JsonObject data;
        JsonArrayBuilder opciones =Json.createArrayBuilder();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<PreguntaEncuesta> resultado = null;
            List<Opcion_Simple_Multiple_Pregunta> resultado2 = null;

            DaoPreguntaEncuesta dao = new DaoPreguntaEncuesta();
            DaoSolicitudEstudio dao2 = new DaoSolicitudEstudio();
            DaoOpcion_Simple_Multiple_Pregunta dao3 = new DaoOpcion_Simple_Multiple_Pregunta();
            DaoParticipacion daoParticipacion = new DaoParticipacion();
            DaoRespuesta daoRespuesta = new DaoRespuesta();
            DaoPregunta daoPregunta = new DaoPregunta();

            SolicitudEstudio solicitudEstudio = new SolicitudEstudio();
            solicitudEstudio = dao2.find(_id,SolicitudEstudio.class);
            Class<PreguntaEncuesta> type = PreguntaEncuesta.class;
            resultado = dao.findAll(type);

            Class<Opcion_Simple_Multiple_Pregunta> type2 = Opcion_Simple_Multiple_Pregunta.class;
            resultado2 = dao3.findAll(type2);


            List<Participacion> participacion = null;
            Class<Participacion> type3 = Participacion.class;
            participacion = daoParticipacion.findAll(type3);

            List<Respuesta> respuesta = null;
            Class<Respuesta> type4 = Respuesta.class;
            respuesta = daoRespuesta.findAll(type4);
            Participacion participacion1 = new Participacion();

            for (Participacion obj : participacion) {
                if (obj.get_solicitudestudio().get_id()==_id && obj.get_encuestado().get_id()==_id2) {
                    participacion1 = daoParticipacion.find(obj.get_id(), Participacion.class);
                }
            }

            for (PreguntaEncuesta obj : resultado) {
                PreguntaEncuesta preguntaEncuesta = dao.find(obj.get_id(), PreguntaEncuesta.class);
                Pregunta pregunta1 = daoPregunta.find(preguntaEncuesta.get_pregunta().get_id(),Pregunta.class);
                int pregunta = 0;
                if (preguntaEncuesta.get_encuesta().get_id() == solicitudEstudio.get_encuesta().get_id()) {
                    for (Respuesta obj3 : respuesta) {

                        if (obj3.get_preguntaencuesta().get_id() == preguntaEncuesta.get_id() && obj3.get_participacion().get_id()==participacion1.get_id()) {
                            pregunta=1;
                        }
                    }
                    if(pregunta==0) {
                        if (preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Opcion simple") || preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Opcion multiple")) {

                            for (Opcion_Simple_Multiple_Pregunta obj2 : resultado2) {
                                Opcion_Simple_Multiple_Pregunta opcion = dao3.find(obj2.get_id(), Opcion_Simple_Multiple_Pregunta.class);
                                if (opcion.get_pregunta().get_id() == preguntaEncuesta.get_pregunta().get_id()) {

                                    opciones.add(Json.createObjectBuilder().add("id", opcion.get_id())
                                            .add("opcion", opcion.get_opcionsimplemultiple().get_opcion()));

                                }
                            }
                            JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                    .add("descripcion", pregunta1.get_descripcion())
                                    .add("tipopregunta", pregunta1.get_tipopregunta())
                                    .add("opciones", opciones)
                                    .build();

                            builder.add(preguntas);
                        } else {

                            if (preguntaEncuesta.get_pregunta().get_valormax() != 0) {
                                JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                        .add("descripcion", pregunta1.get_descripcion())
                                        .add("tipopregunta", pregunta1.get_tipopregunta())
                                        .add("minimo", pregunta1.get_valormin())
                                        .add("maximo", pregunta1.get_valormax()).build();
                                builder.add(preguntas);

                            } else {
                                JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                        .add("descripcion", pregunta1.get_descripcion())
                                        .add("tipopregunta", pregunta1.get_tipopregunta())
                                        .build();

                                builder.add(preguntas);
                            }
                        }
                    }
                }
            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("Preguntas",builder).build();

        }
        catch (Exception ex){
            String problema = ex.getMessage();
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        //builder.build();
        return Response.status(Response.Status.OK).entity(data).build();
    }

    /**
     * Esta funcion consiste en cambiar el estado de la participacion del encuestado a inactivo cuando responde todas las preguntas
     * ademas en el caso de que sea el ultimo encuestado en responder el estado del estudio cambia a finalizado
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @param _id2 corresponde al id de la participacion
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @DELETE
    @Path( "/finalizar/{id}/{id2}" )
    public Response finalizarParticipacion(@PathParam("id") long  _id,@PathParam("id2")long  _id2)
    {
        JsonObject data;
        ParticipacionDto resultado = new ParticipacionDto();
        try
        {
            int restantes=0;
            DaoParticipacion dao = new DaoParticipacion();
            List<Participacion> participacion = null;
            Class<Participacion> type = Participacion.class;
            participacion = dao.findAll(type);

            for (Participacion obj : participacion) {
                if (obj.get_solicitudestudio().get_id()==_id && obj.get_encuestado().get_id()==_id2) {
                    DaoParticipacion dao2 = new DaoParticipacion();
                    Participacion participacion1 = dao2.find(obj.get_id(), Participacion.class);
                    participacion1.set_estado("inactivo");

                    Participacion resul = dao.update(participacion1);
                    resultado.setId( resul.get_id() );
                }
            }
            for (Participacion obj : participacion) {
                if (obj.get_solicitudestudio().get_id()==_id && obj.get_estado().equals("activo")) {
                    restantes=1;
                }
            }
            if (restantes==0){
                DaoSolicitudEstudio daoSolicitudEstudio = new DaoSolicitudEstudio();
                SolicitudEstudio solicitudEstudio = daoSolicitudEstudio.find(_id,SolicitudEstudio.class);

                solicitudEstudio.set_estado("finalizado");

                SolicitudEstudio resul = daoSolicitudEstudio.update(solicitudEstudio);
                resultado.setId( resul.get_id() );

            }



            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();
        }
        catch ( Exception ex )
        {
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        return Response.status(Response.Status.OK).entity(data).build();
    }


    /**
    * Esta funcion consiste en obtener el id del encuestado
    * @author Gabriel Romero
    * @param _id corresponde al id del usuario
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, encuestado_id 
    *         y mensaje.
    */
    @GET
    @Path("/get-id/{_id}")
    public Response getEncuestadoId(@PathParam("_id") long _id) {

        JsonObject data;

        DaoEncuestado dao = new DaoEncuestado();
        try {

            Encuestado encuestado= dao.getEncuestadoId(_id);

            data= Json.createObjectBuilder().add("estado","success")
                    .add("mensaje","uid del encuestado es: "+ _id)
                    .add("codigo",200)
                    .add("encuestado_id",encuestado.get_id()).build();
        }
        catch (Exception ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje",ex.getMessage())
                    .add("codigo",500).build();

            System.out.println(data);
            return Response.status(Response.Status.OK).entity(data).build();

        }
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }
}
