package ucab.dsw.servicio;
import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;
import javax.json.*;
import javax.persistence.PersistenceException;
import javax.json.JsonObject;
import javax.validation.constraints.Null;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Una clase que contiene un conjunto de metodos y/o funciones correspondiente al analista
 * @version 1.0, 02/01/2021
 * @author Carlos Silva
 * @author Gabriel Romero
 */

@Path( "/analista" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class analista_metodos {


    /**
    * Esta funcion consiste en traer los estudios que tiene un analista
    * @author Gabriel Romero
    * @param _id corresponde al id del analista
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones
    */
    @GET
    @Path("/estudios/{_id}")
    public Response consultaEstudios_asignados(@PathParam("_id") long _id) {

        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonArrayBuilder builderArrayEncuestado =Json.createArrayBuilder();
        JsonObject builderObject;
        JsonObject data;

        DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
        DaoMarca daoMarca = new DaoMarca();
        DaoSubcategoria daoSubcategoria = new DaoSubcategoria ();
        DaoCategoria daoCategoria = new DaoCategoria();
        DaoParticipacion daoParticipacion=new DaoParticipacion();

        DaoCaracteristica_Demografica daoCaracteristica_demografica = new DaoCaracteristica_Demografica();

        try {
            List<SolicitudEstudio>resultado = dao.getEstudiosByAnalista(_id);

            for (SolicitudEstudio obj : resultado) {

                Caracteristica_Demografica caracteristicas= daoCaracteristica_demografica.find(obj.get_caracteristicademografica().get_id(), Caracteristica_Demografica.class);

                builderObject= Json.createObjectBuilder().add("edad_min",caracteristicas.get_edad_min())
                                                         .add("edad_max",caracteristicas.get_edad_max())
                                                         .add("nivel_socieconomico",caracteristicas.get_nivel_socioeconomico())
                                                         .add("nacionalidad",caracteristicas.get_nacionalidad())
                                                         .add("cantidad_hijos",caracteristicas.get_cantidad_hijos())
                                                         .add("genero",caracteristicas.get_genero())
                                                         .add("parroquia",caracteristicas.get_Parroquia_demografia().get_nombre())
                                                         .add("estado",caracteristicas.get_Parroquia_demografia().get_ciudad().get_estado().get_nombre())
                                                         .add("ciudad",caracteristicas.get_Parroquia_demografia().get_ciudad().get_nombre())
                                                         .add("pais",caracteristicas.get_Parroquia_demografia().get_ciudad().get_estado().get_pais().get_nombre())
                                                         .add("nivel_academico",caracteristicas.get_nivel_academico_demografia().get_nombre()).build();

            
                List<Participacion> participacion= daoParticipacion.getParticipacionByEstudio(obj.get_id());

                
                if(participacion!=null){
                    for(Participacion j:participacion){
                    Participacion participacion1 = daoParticipacion.find(j.get_id(), Participacion.class);

                    builderArrayEncuestado.add(Json.createObjectBuilder().add("participacion_id", participacion1.get_id())
                                                                        .add("doc_id", participacion1.get_encuestado().get_doc_id())
                                                                        .add("id_encuestado", participacion1.get_encuestado().get_id())
                                                                        .add("usuario",participacion1.get_encuestado().get_usuario_encuestado().get_usuario())
                                                                        .add("correo",participacion1.get_encuestado().get_correo())
                                                                        .add("nombre",participacion1.get_encuestado().get_nombre())
                                                                        .add("apellido",participacion1.get_encuestado().get_apellido())
                                                                        .add("estado",participacion1.get_estado()));


                    }
                }

                String resultadoAnalista="";
                SolicitudEstudio solicitudEstudio = dao.find(obj.get_id(),SolicitudEstudio.class);
                Marca marca = daoMarca.find(solicitudEstudio.get_marca().get_id(), Marca.class);
                if (solicitudEstudio.get_resultadoanalista() != null){
                    resultadoAnalista = solicitudEstudio.get_resultadoanalista();
                }else{
                    resultadoAnalista="";
                }
                builder.add(Json.createObjectBuilder().add("id", solicitudEstudio.get_id())
                                                      .add("fecha", solicitudEstudio.get_fecha_inicio().toString())
                                                      .add("modo_encuesta",solicitudEstudio.get_modoencuesta())
                                                      .add("caracteristica_demografica",builderObject)
                                                      .add("marca",marca.get_nombre())
                                                      .add("subcategoria",marca.get_subcategoria().get_nombre())
                                                      .add("categoria",marca.get_subcategoria().get_categoria().get_nombre())
                                                      .add("participacion",builderArrayEncuestado)
                                                      .add("resultado",resultadoAnalista)
                                                      .add("estado", solicitudEstudio.get_estado()));


            }


            data= Json.createObjectBuilder().add("estado","success")
                                            .add("mensaje","Estudios del analista "+ _id)
                                            .add("codigo",500)
                                            .add("estudios",builder).build();
        }
        catch (Exception ex){
            ex.printStackTrace();
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
    /**
     * Esta funcion consiste en cambiar el estado de un estudio al que ya se le asigno una encuesta
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @PUT
    @Path( "/empezar-estudio/{id}" )
    public Response Empezar_estudio(@PathParam("id") long  _id )
    {
        JsonObject data;
        SolicitudEstudioDto resultado = new SolicitudEstudioDto();
        try
        {
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio = dao.find(_id,SolicitudEstudio.class);

            solicitudEstudio.set_estado( "en progreso" );

            SolicitudEstudio resul = dao.update(solicitudEstudio);
            resultado.setId( resul.get_id() );

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
     * Esta funcion consiste en cambiar el estado de una una participacion a inactivo
     * @author Carlos Silva
     * @param _id corresponde al id de la participacion
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @PUT
    @Path( "/eliminar-participacion/{id}" )
    public Response Eliminar_Participacion(@PathParam("id") long  _id )
    {
        JsonObject data;
        SolicitudEstudioDto resultado = new SolicitudEstudioDto();
        try
        {
            DaoParticipacion dao = new DaoParticipacion();
            Participacion participacion = dao.find(_id,Participacion.class);
            participacion.set_estado("inactivo");
            Participacion resul = dao.update(participacion);
            resultado.setId( resul.get_id() );

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
    * Esta funcion consiste en traer los estudios por telefono que tiene un analista
    * @author Carlos Silva
    * @author Gabriel Romero
    * @param _id corresponde al id del analista
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones
    */
    @GET
    @Path("/estudios-telefono/{_id}")
    public Response consultaEstudios_telefono(@PathParam("_id") long _id) {

        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonArrayBuilder builderArrayEncuestado =Json.createArrayBuilder();
        JsonObject builderObject;
        JsonObject data;

        DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
        DaoMarca daoMarca = new DaoMarca();
        DaoSubcategoria daoSubcategoria = new DaoSubcategoria ();
        DaoCategoria daoCategoria = new DaoCategoria();
        DaoParticipacion daoParticipacion=new DaoParticipacion();

        DaoCaracteristica_Demografica daoCaracteristica_demografica = new DaoCaracteristica_Demografica();

        try {
            List<SolicitudEstudio>resultado = dao.getEstudiosByAnalista(_id);

            for (SolicitudEstudio obj : resultado) {

                if(obj.get_modoencuesta().equals("telefono")) {

                    Caracteristica_Demografica caracteristicas = daoCaracteristica_demografica.find(obj.get_caracteristicademografica().get_id(), Caracteristica_Demografica.class);

                    builderObject = Json.createObjectBuilder().add("edad_min", caracteristicas.get_edad_min())
                            .add("edad_max", caracteristicas.get_edad_max())
                            .add("nivel_socieconomico", caracteristicas.get_nivel_socioeconomico())
                            .add("nacionalidad", caracteristicas.get_nacionalidad())
                            .add("cantidad_hijos", caracteristicas.get_cantidad_hijos())
                            .add("genero", caracteristicas.get_genero())
                            .add("parroquia", caracteristicas.get_Parroquia_demografia().get_nombre())
                            .add("estado", caracteristicas.get_Parroquia_demografia().get_ciudad().get_estado().get_nombre())
                            .add("ciudad", caracteristicas.get_Parroquia_demografia().get_ciudad().get_nombre())
                            .add("pais", caracteristicas.get_Parroquia_demografia().get_ciudad().get_estado().get_pais().get_nombre())
                            .add("nivel_academico", caracteristicas.get_nivel_academico_demografia().get_nombre()).build();


                    List<Participacion> participacion = daoParticipacion.getParticipacionByEstudio(obj.get_id());

                    for (Participacion j : participacion) {
                        Participacion participacion1 = daoParticipacion.find(j.get_id(), Participacion.class);
                        builderArrayEncuestado.add(Json.createObjectBuilder().add("id", participacion1.get_id())
                                .add("doc_id", participacion1.get_encuestado().get_doc_id())
                                .add("usuario", participacion1.get_encuestado().get_usuario_encuestado().get_usuario())
                                .add("correo", participacion1.get_encuestado().get_correo())
                                .add("Nombre", participacion1.get_encuestado().get_nombre())
                                .add("Apellido", participacion1.get_encuestado().get_apellido())
                                .add("Estado", participacion1.get_estado()));


                    }
                    SolicitudEstudio solicitudEstudio = dao.find(obj.get_id(),SolicitudEstudio.class);
                    Marca marca = daoMarca.find(solicitudEstudio.get_marca().get_id(), Marca.class);
                    JsonObject encuesta = Json.createObjectBuilder().add("Marca",marca.get_nombre())
                            .add("Categoria",marca.get_subcategoria().get_categoria().get_nombre())
                            .add("Subcategoria",marca.get_subcategoria().get_nombre()).build();

                    builder.add(Json.createObjectBuilder().add("id", solicitudEstudio.get_id())
                            .add("fecha", solicitudEstudio.get_fecha_inicio().toString())
                            .add("modo_encuesta", solicitudEstudio.get_modoencuesta())
                            .add("caracteristica_demografica", builderObject)
                            .add("caracteristicas",encuesta)
                            .add("Participantes", builderArrayEncuestado)
                            .add("estatus", solicitudEstudio.get_estado()));

                }
            }


            data= Json.createObjectBuilder().add("estado","success")
                    .add("mensaje","Estudios del analista "+ _id)
                    .add("codigo",500)
                    .add("estudios",builder).build();
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

    /**
     * Esta funcion permite que el analista ponga su comentario final en el estudio
     * @author Carlos Silva
     * @param respuestaAnalistaDto corresponde al objeto de la capa web que contiene los nuevos datos que se desean insertar
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @PUT
    @Path( "/responder-solicitud" )
    public Response ResponderEstudio( Respuesta_analistaDto respuestaAnalistaDto)
    {
        JsonObject data;
        SolicitudEstudioDto resultado = new SolicitudEstudioDto();
        try
        {
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio = dao.find(respuestaAnalistaDto.getSolicituEstudioDto().getId(), SolicitudEstudio.class);

            solicitudEstudio.set_resultadoanalista(respuestaAnalistaDto.getRespueta());
            Date fecha=new Date();
            solicitudEstudio.set_fecha_fin(fecha);

            SolicitudEstudio resul = dao.update(solicitudEstudio);
            resultado.setId( resul.get_id() );

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
     * Esta funcion consiste en mostrar todos todas las respuestas que tiene una encuesta segun su estudio
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @GET
    @Path( "/respuestas-estudio/{id}" )
    public Response respuestas_estudio(@PathParam("id")long  _id)
    {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonArrayBuilder respuesta_opcion =Json.createArrayBuilder();
        JsonArrayBuilder respuesta_participacion =Json.createArrayBuilder();
        JsonObject builderObject;
        try {
            List<Respuesta> resultado = null;
            List<RespuestaOpcion> resultado2 = null;
            List<Participacion> resultado3 = null;

            DaoRespuesta dao = new DaoRespuesta();
            DaoRespuestaOpcion dao2 = new DaoRespuestaOpcion();
            DaoSolicitudEstudio dao3 = new DaoSolicitudEstudio();
            DaoParticipacion dao4 = new DaoParticipacion();
            DaoEncuestado dao5 = new DaoEncuestado();
            DaoUsuario dao6 = new DaoUsuario();

            SolicitudEstudio solicitudEstudio = new SolicitudEstudio();
            solicitudEstudio = dao3.find(_id,SolicitudEstudio.class);

            Class<Respuesta> type = Respuesta.class;
            Class<RespuestaOpcion> type2 = RespuestaOpcion.class;
            Class<Participacion> type3 = Participacion.class;

            resultado = dao.findAll(type);
            resultado2 = dao2.findAll(type2);
            resultado3 = dao4.findAll(type3);

            for (Participacion obj3 : resultado3) {
                Participacion participacion = dao4.find(obj3.get_id(), Participacion.class);
                if (participacion.get_solicitudestudio().get_id() == solicitudEstudio.get_id() && participacion.get_estado().equals("inactivo")) {
                    for (Respuesta obj : resultado) {
                        Respuesta respuesta = dao.find(obj.get_id(), Respuesta.class);
                        if (respuesta.get_participacion().get_id() == participacion.get_id()) {

                            if (respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta().equals("desarrollo")) {
                                respuesta_participacion.add(Json.createObjectBuilder()
                                        .add("pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_descripcion())
                                        .add("tipo_pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta())
                                        .add("respuesta", respuesta.get_respuestadesarrollo()));
                            }
                            if (respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta().equals("booleana")) {
                                
                                String respuestaBooleana;
                                if(respuesta.get_respuestaboolean()==1){
                                    respuestaBooleana="verdadero";
                                }
                                else{
                                    respuestaBooleana="falso";
                                }
                                respuesta_participacion.add(Json.createObjectBuilder()
                                        .add("pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_descripcion())
                                        .add("tipo_pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta())
                                        .add("respuesta", respuestaBooleana));
                            }
                            if (respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta().equals("Rango")) {
                                respuesta_participacion.add(Json.createObjectBuilder()
                                        .add("pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_descripcion())
                                        .add("tipo_pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta())
                                        .add("respuesta", respuesta.get_respuestarango()));
                            }

                            if (respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta().equals("Opcion simple") || respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta().equals("Opcion multiple")) {
                                for (RespuestaOpcion obj2 : resultado2) {
                                    RespuestaOpcion respuesta_Opcion = dao2.find(obj2.get_id(), RespuestaOpcion.class);
                                    if (respuesta.get_id() == respuesta_Opcion.get_respuesta().get_id()) {
                                        respuesta_opcion.add(Json.createObjectBuilder().add("respuestaOpcion", respuesta_Opcion.get_opcionsimplemultiple().get_opcionsimplemultiple().get_opcion()));
                                    }
                                }
                                respuesta_participacion.add(Json.createObjectBuilder()
                                        .add("pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_descripcion())
                                        .add("tipo_pregunta", respuesta.get_preguntaencuesta().get_pregunta().get_tipopregunta())
                                        .add("respuesta", respuesta_opcion));
                            }
                        }


                    }


                    Encuestado encuestado=dao5.find(participacion.get_encuestado().get_id(),Encuestado.class);
                    Usuario usuario=dao6.find(encuestado.get_usuario_encuestado().get_id(),Usuario.class);

                    JsonObject p = Json.createObjectBuilder()
                            .add("participacion_id",participacion.get_id())
                            .add("usuario", usuario.get_usuario())
                            .add("encuestado", encuestado.get_nombre())
                            .add("respuestas", respuesta_participacion).build();
                    builder.add(p);

                }
            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("participaciones",builder).build();

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
     * Esta funcion consiste en enviar los datos que requieren las graficas de respuesra por estudio
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @GET
    @Path( "/graficos-estudio/{id}" )
    public Response respuestas_porcentaje(@PathParam("id")long  _id)
    {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonArrayBuilder respuesta_opcion =Json.createArrayBuilder();
        JsonObject builderObject;
        try {
            List<Respuesta> resultado = null;
            List<RespuestaOpcion> resultado2 = null;
            List<PreguntaEncuesta> resultado3 = null;
            List<Opcion_Simple_Multiple_Pregunta> resultado4 = null;

            DaoRespuesta dao = new DaoRespuesta();
            DaoRespuestaOpcion dao2 = new DaoRespuestaOpcion();
            DaoSolicitudEstudio dao3 = new DaoSolicitudEstudio();
            DaoPreguntaEncuesta dao4 = new DaoPreguntaEncuesta();
            DaoOpcion_Simple_Multiple_Pregunta dao5 = new DaoOpcion_Simple_Multiple_Pregunta();

            SolicitudEstudio solicitudEstudio = new SolicitudEstudio();
            solicitudEstudio = dao3.find(_id,SolicitudEstudio.class);

            Class<Respuesta> type = Respuesta.class;
            Class<RespuestaOpcion> type2 = RespuestaOpcion.class;
            Class<PreguntaEncuesta> type3 = PreguntaEncuesta.class;
            Class<Opcion_Simple_Multiple_Pregunta> type4 = Opcion_Simple_Multiple_Pregunta.class;

            resultado = dao.findAll(type);
            resultado2 = dao2.findAll(type2);
            resultado3 = dao4.findAll(type3);
            resultado4 = dao5.findAll(type4);

            for (PreguntaEncuesta obj : resultado3) {
                PreguntaEncuesta preguntaEncuesta = dao4.find(obj.get_id(), PreguntaEncuesta.class);

                if (solicitudEstudio.get_encuesta().get_id() == preguntaEncuesta.get_encuesta().get_id()) {


                    if (preguntaEncuesta.get_pregunta().get_tipopregunta().equals("booleana")) {
                        int si = 0;
                        int no = 0;
                        int cont = 0;
                        for (Respuesta obj2 : resultado) {
                            Respuesta respuesta = dao.find(obj2.get_id(), Respuesta.class);
                            if (respuesta.get_preguntaencuesta().get_id() == preguntaEncuesta.get_id() && respuesta.get_participacion().get_solicitudestudio().get_id() == solicitudEstudio.get_id()) {
                                if (respuesta.get_respuestaboolean() == 0) {
                                    no = no + 1;
                                } else {
                                    si = si + 1;
                                }
                                cont = cont + 1;
                            }


                        }
                        //si=(si*100)/cont;
                        //no=(no*100)/cont;
                        respuesta_opcion.add(Json.createArrayBuilder().add("si").add(si));
                        respuesta_opcion.add(Json.createArrayBuilder().add("no").add(no));

                        JsonObject p = Json.createObjectBuilder()
                                .add("pregunta", preguntaEncuesta.get_pregunta().get_descripcion())
                                .add("tipo_pregunta", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                .add("resultado", respuesta_opcion).build();
                        builder.add(p);
                    }

                    if (preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Rango") ) {
                        int cont = 0;
                        int valor = preguntaEncuesta.get_pregunta().get_valormax();



                        for (Respuesta obj2 : resultado) {
                            Respuesta respuesta = dao.find(obj2.get_id(), Respuesta.class);
                            if (respuesta.get_preguntaencuesta().get_id() == preguntaEncuesta.get_id() && respuesta.get_participacion().get_solicitudestudio().get_id() == solicitudEstudio.get_id()) {
                                cont = cont + 1;
                            }
                        }
                        if (cont != 0) {
                            while (valor > 0) {
                                int rango = 0;
                                for (Respuesta obj2 : resultado) {
                                    Respuesta respuesta = dao.find(obj2.get_id(), Respuesta.class);
                                    if (respuesta.get_preguntaencuesta().get_id() == preguntaEncuesta.get_id() && respuesta.get_participacion().get_solicitudestudio().get_id() == solicitudEstudio.get_id()) {
                                        if (respuesta.get_respuestarango() == valor) {
                                            rango = rango + 1;
                                        }
                                    }
                                }
                                //rango = (rango * 100) / cont;
                                respuesta_opcion.add(Json.createArrayBuilder().add("valor" + valor).add(rango));
                                valor = valor - 1;
                            }
                        }

                        JsonObject p = Json.createObjectBuilder()
                                .add("pregunta", preguntaEncuesta.get_pregunta().get_descripcion())
                                .add("tipo_pregunta", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                .add("resultado", respuesta_opcion).build();
                        builder.add(p);
                    }

                    if (preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Opcion simple") ||preguntaEncuesta.get_pregunta().get_tipopregunta().equals("Opcion multiple")) {
                        int cont = 0;
                        int cont2=0;
                        for (RespuestaOpcion obj4 : resultado2) {
                            RespuestaOpcion respuestaOpcion = dao2.find(obj4.get_id(), RespuestaOpcion.class);
                            if ( respuestaOpcion.get_respuesta().get_preguntaencuesta().get_id() == preguntaEncuesta.get_id() && respuestaOpcion.get_respuesta().get_participacion().get_solicitudestudio().get_id() == solicitudEstudio.get_id()) {
                                cont = cont + 1;
                            }
                        }
                        if (cont!=0){
                            for (Opcion_Simple_Multiple_Pregunta obj3 : resultado4) {
                                int opcion = 0;
                                Opcion_Simple_Multiple_Pregunta opcion_simple_multiple_pregunta = dao5.find(obj3.get_id(), Opcion_Simple_Multiple_Pregunta.class);
                                if (opcion_simple_multiple_pregunta.get_pregunta().get_id() == preguntaEncuesta.get_pregunta().get_id()) {

                                    for (RespuestaOpcion obj4 : resultado2) {
                                        RespuestaOpcion respuestaOpcion = dao2.find(obj4.get_id(), RespuestaOpcion.class);
                                        if ( respuestaOpcion.get_opcionsimplemultiple().get_id() == opcion_simple_multiple_pregunta.get_id() && respuestaOpcion.get_respuesta().get_participacion().get_solicitudestudio().get_id()==solicitudEstudio.get_id()) {
                                            opcion = opcion + 1;
                                        }
                                    }

                                    cont2 = cont2 + 1;
                                    //opcion = (opcion * 100) / cont;
                                    respuesta_opcion.add(Json.createArrayBuilder().add(opcion_simple_multiple_pregunta.get_opcionsimplemultiple().get_opcion()).add(opcion));

                                }
                            }

                        }


                        JsonObject p = Json.createObjectBuilder()
                                .add("pregunta", preguntaEncuesta.get_pregunta().get_descripcion())
                                .add("tipo_pregunta", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                .add("resultado", respuesta_opcion).build();
                        builder.add(p);
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


}
