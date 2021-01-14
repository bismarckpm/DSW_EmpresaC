package ucab.dsw.servicio;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.json.JsonArrayBuilder;

import javax.ws.rs.*;

@Path( "/admin" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class AdminServicio {

    /**
     * Esta funcion consiste en enviar los datos de los estudios que tiene asignado un admin
     * en especifico y que ya se le asigno una encuesta
     * @author Carlos Silva
     * @param _id corresponde al id del admin
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path("/estudios-asignados/{id}")
    public Response consultaEstudios_asignados(@PathParam("id") long _id) {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<SolicitudEstudio> resultado = null;

            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            DaoMarca daoMarca = new DaoMarca();
            DaoSubcategoria daoSubcategoria = new DaoSubcategoria();
            DaoCategoria daoCategoria = new DaoCategoria();
            Class<SolicitudEstudio> type = SolicitudEstudio.class;

            resultado = dao.findAll(type);
            for (SolicitudEstudio obj : resultado) {
                SolicitudEstudio solicitudEstudio = dao.find(obj.get_id(), SolicitudEstudio.class);

                if (solicitudEstudio.get_usuario2() != null) {
                    if (solicitudEstudio.get_encuesta() != null && solicitudEstudio.get_usuario2().get_id() == _id && solicitudEstudio.get_estado().equals("en progreso")) {
                        Marca marca = daoMarca.find(solicitudEstudio.get_marca().get_id(), Marca.class);
                        Subcategoria subcategoria = daoSubcategoria.find(marca.get_subcategoria().get_id(),Subcategoria.class);
                        Categoria categoria = daoCategoria.find(subcategoria.get_categoria().get_id(),Categoria.class);

                        JsonObject encuesta = Json.createObjectBuilder().add("Marca", marca.get_nombre())
                                .add("idcategoria", categoria.get_id())
                                .add("Categoria", categoria.get_nombre())
                                .add("idsubcategoria", subcategoria.get_id())
                                .add("Subcategoria", subcategoria.get_nombre()).build();

                        JsonObject tipo = Json.createObjectBuilder().add("id", solicitudEstudio.get_id())
                                .add("fecha", solicitudEstudio.get_fecha_inicio().toString())
                                .add("caracteristicas", encuesta)
                                .add("estatus",solicitudEstudio.get_estado()).build();

                        builder.add(tipo);


                    } else {
                        System.out.println("");
                    }
                }

            }

            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200)
                    .add("estudios", builder).build();

        } catch (Exception ex) {
            String problema = ex.getMessage();
            data = Json.createObjectBuilder()
                    .add("estado", "exception!!!")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 500).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        //builder.build();
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }
    /**
     * Esta funcion consiste en enviar los datos de los estudios que tiene asignado un admin
     * en especifico y que no se le a asignado una encuesta
     * @author Carlos Silva
     * @param _id corresponde al id del admin
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path("/estudios-no-asignados/{id}")
    public Response consultaEstudios_no_asignados(@PathParam("id") long _id) {
        JsonObject data;

        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            List<SolicitudEstudio> resultado = null;

            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            DaoMarca daoMarca = new DaoMarca();
            DaoSubcategoria daoSubcategoria = new DaoSubcategoria();
            DaoCategoria daoCategoria = new DaoCategoria();

            Class<SolicitudEstudio> type = SolicitudEstudio.class;

            resultado = dao.findAll(type);
            for (SolicitudEstudio obj : resultado) {
                SolicitudEstudio solicitudEstudio = dao.find(obj.get_id(), SolicitudEstudio.class);


                if (solicitudEstudio.get_usuario2() != null) {

                    if (solicitudEstudio.get_encuesta() == null && solicitudEstudio.get_usuario2().get_id() == _id) {

                        Marca marca = daoMarca.find(solicitudEstudio.get_marca().get_id(), Marca.class);  //Este fue el dao.find que falto
                        Subcategoria subcategoria = daoSubcategoria.find(marca.get_subcategoria().get_id(),Subcategoria.class);
                        Categoria categoria = daoCategoria.find(subcategoria.get_categoria().get_id(),Categoria.class);

                        JsonObject encuesta = Json.createObjectBuilder().add("Marca", marca.get_nombre())
                                .add("idcategoria", categoria.get_id())
                                .add("Categoria", categoria.get_nombre())
                                .add("idsubcategoria", subcategoria.get_id())
                                .add("Subcategoria", subcategoria.get_nombre()).build();
                        JsonObject tipo = Json.createObjectBuilder().add("id", solicitudEstudio.get_id())
                                .add("fecha", solicitudEstudio.get_fecha_inicio().toString())
                                .add("estatus", solicitudEstudio.get_estado())
                                .add("caracteristicas", encuesta)
                                .build();

                        builder.add(tipo);
                    }
                } else {
                    System.out.println("");
                }


            }

            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200)
                    .add("estudios", builder).build();

        } catch (Exception ex) {

            data = Json.createObjectBuilder()
                    .add("estado", "exception!!!")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 500).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        }
        //builder.build();
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }


    /**
     * Esta funcion consiste en cambiar el estado de un estudio a inactivo
     * @author Carlos Silva
     * @param _id corresponde al id del estudio
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @DELETE
    @Path("/delete-solicitud/{id}")
    public Response EliminarEstudio(@PathParam("id") long _id) {
        JsonObject data;
        SolicitudEstudioDto resultado = new SolicitudEstudioDto();
        try {
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio = dao.find(_id, SolicitudEstudio.class);

            solicitudEstudio.set_estado("inactivo");

            SolicitudEstudio resul = dao.update(solicitudEstudio);
            resultado.setId(resul.get_id());

            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200).build();
        } catch (Exception ex) {
            String problema = ex.getMessage();
            data = Json.createObjectBuilder()
                    .add("estado", "exception!!!")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 500).build();


            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        return Response.status(Response.Status.OK).entity(data).build();
    }
    /**
     * Esta funcion consiste en asignarle una encuesta a un estudio creandolo y
     * asignandole un analista aleatorio, aparte tambien recibe una lista de preguntas
     * que se le asignan a la encuesta y otra lista de participantes que se le asignaron al estudio
     * @author Carlos Silva
     * @param _id corresponde al id de la marca
     * @param _id2 corresponde al id del estudio
     * @param encuestaDto corresponde al objeto de la capa web que contiene los nuevos datos
     * que se van a ingresar y las listas de participantes y preguntas
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @PUT
    @Path("/addEncuesta/{id}/{id2}")
    public Response addEncuesta(@PathParam("id") long _id, @PathParam("id2") long _id2, EncuestaDto encuestaDto) {
        EncuestaDto resultado = new EncuestaDto();
        SolicitudEstudioDto resultado3 = new SolicitudEstudioDto();
        JsonObject data;
        int analista_random = 0;
        Usuario analista_elegido = null;
        try {
            DaoEncuesta dao = new DaoEncuesta();
            DaoPreguntaEncuesta dao2 = new DaoPreguntaEncuesta();
            DaoParticipacion dao5 = new DaoParticipacion();

            Encuesta encuesta = new Encuesta();
            encuesta.set_nombre(encuestaDto.getNombre());
            encuesta.set_estado("activo");

            Marca marca = new Marca(_id);
            encuesta.set_marca(marca);

            Encuesta resul = dao.insert(encuesta);
            resultado.setId(resul.get_id());

            DaoSolicitudEstudio dao3 = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio = dao3.find(_id2, SolicitudEstudio.class);

            DaoUsuario daoUsuario = new DaoUsuario();

            //PARA PRODUCCIÓN
            /*List<Usuario> analista= daoUsuario.getAnalistas();
            analista_random=(int)(Math.random()* analista.size());
            System.out.println("analista random");
            System.out.println(analista_random);
            analista_elegido=analista.get(analista_random);*/

            //PARA DESARROLLO
            Usuario analista = new Usuario(15);
            analista = daoUsuario.find(analista.get_id(), Usuario.class);
            solicitudEstudio.set_usuario(analista);

            solicitudEstudio.set_estado("pendiente");
            solicitudEstudio.set_encuesta(resul);

            SolicitudEstudio resul3 = dao3.update(solicitudEstudio);
            resultado3.setId(resul3.get_id());

            if (encuestaDto.getPreguntas() != null) {

                List<PreguntaDto> pregunta = encuestaDto.getPreguntas();

                for (PreguntaDto obj : pregunta) {

                    PreguntaEncuestaDto resultado2 = new PreguntaEncuestaDto();
                    PreguntaEncuesta preguntaEncuesta = new PreguntaEncuesta();
                    preguntaEncuesta.set_encuesta(resul);
                    Pregunta pregunta1 = new Pregunta();
                    DaoPregunta dao4 = new DaoPregunta();
                    pregunta1 = dao4.find(obj.getId(), Pregunta.class);

                    preguntaEncuesta.set_pregunta(pregunta1);

                    PreguntaEncuesta resul2 = dao2.insert(preguntaEncuesta);
                    resultado2.setId(resul2.get_id());


                }
            }

            if (encuestaDto.getEncuestado() != null) {

                List<EncuestadoDto> encuestado = encuestaDto.getEncuestado();

                for (EncuestadoDto obj : encuestado) {

                    ParticipacionDto resultado2 = new ParticipacionDto();
                    Participacion participacion = new Participacion();
                    participacion.set_solicitudestudio(solicitudEstudio);
                    participacion.set_estado("activo");
                    Encuestado encuestado1 = new Encuestado();
                    DaoEncuestado dao4 = new DaoEncuestado();
                    encuestado1 = dao4.find(obj.getId(), Encuestado.class);

                    participacion.set_encuestado(encuestado1);

                    Participacion resul2 = dao5.insert(participacion);
                    resultado2.setId(resul2.get_id());


                }
            }


            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200).build();

        } catch (Exception ex) {
            String problema = ex.getMessage();

            data = Json.createObjectBuilder()
                    .add("estado", "exception!!!")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 500).build();


            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        return Response.status(Response.Status.OK).entity(data).build();
    }
    /**
     * Esta funcion consiste en ingresar nuevas preguntas y si la pregunta es de tipo opcion
     * simple o multiple ingresar nuevas opciones para ellas
     * @author Carlos Silva
     * @param preguntaDto corresponde al objeto de la capa web que contiene los nuevos datos
     * que se van a ingresar y la lista de opnciones que se le puede asignar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @PUT
    @Path("/addPregunta")
    public Response addPregunta(PreguntaDto preguntaDto) {
        PreguntaDto resultado = new PreguntaDto();
        JsonObject data;
        try {
            DaoPregunta dao = new DaoPregunta();
            DaoOpcionSimpleMultiple dao2 = new DaoOpcionSimpleMultiple();
            DaoOpcionSimpleMultiplePregunta dao3 = new DaoOpcionSimpleMultiplePregunta();
            Pregunta pregunta = new Pregunta();
            pregunta.set_descripcion(preguntaDto.getDescripcion());
            pregunta.set_tipopregunta(preguntaDto.getTipopregunta());
            pregunta.set_estado("activo");
            if (preguntaDto.getTipopregunta().equals("Rango")) {
                pregunta.set_valormax(preguntaDto.getValormax());
                pregunta.set_valormin(preguntaDto.getValormin());
            }
            Pregunta resul = dao.insert(pregunta);
            resultado.setId(resul.get_id());

            JsonObject p = Json.createObjectBuilder().add("id", resul.get_id())
                    .build();

            System.out.println("Id: " + resul.get_id());
            System.out.println("Descripcion: " + preguntaDto.getDescripcion());
            System.out.println("Tipo de pregunta: " + preguntaDto.getTipopregunta());
            if (preguntaDto.getValormax() != 0) {
                System.out.println("Rango minimo: " + preguntaDto.getValormin());
                System.out.println("Rango maximo: " + preguntaDto.getValormax());
            }


            if (preguntaDto.getOpciones() != null) {

                List<OpcionSimpleMultipleDto> opcion = preguntaDto.getOpciones();

                for (OpcionSimpleMultipleDto obj : opcion) {
                    OpcionSimpleMultipleDto resultado2 = new OpcionSimpleMultipleDto();
                    OpcionSimpleMultiple opcionSimpleMultiple = new OpcionSimpleMultiple();
                    opcionSimpleMultiple.set_estado("activo");
                    opcionSimpleMultiple.set_opcion(obj.getOpcion());

                    OpcionSimpleMultiple resul2 = dao2.insert(opcionSimpleMultiple);
                    resultado2.setId(resul2.get_id());

                    OpcionSimpleMultiplePreguntaDto resultado3 = new OpcionSimpleMultiplePreguntaDto();
                    OpcionSimpleMultiplePregunta opcion_Simple_Multiple_Pregunta = new OpcionSimpleMultiplePregunta();
                    opcion_Simple_Multiple_Pregunta.set_opcion_Simple_Multiple_Pregunta(resul2);
                    opcion_Simple_Multiple_Pregunta.set_pregunta(resul);

                    OpcionSimpleMultiplePregunta resul3 = dao3.insert(opcion_Simple_Multiple_Pregunta);
                    resultado3.setId(resul3.get_id());
                }
            }

            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200)
                    .add("Pregunta", p).build();


        } catch (Exception ex) {
            String problema = ex.getMessage();

            data = Json.createObjectBuilder()
                    .add("estado", "exception!!!")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 500).build();


            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        return Response.status(Response.Status.OK).entity(data).build();
    }

    /**
     * Esta funcion consiste en enviar los datos de los participantes que tiene un estudio
     * @author Carlos Silva
     * @param _id corresponde al del estudio
     * que se van a ingresar y la lista de opnciones que se le puede asignar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path("/estudios-participacion/{id}")
    public Response Participacion_estudio(@PathParam("id") long _id) {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<Participacion> resultado = null;

            DaoParticipacion dao = new DaoParticipacion();
            DaoEncuestado daoEncuestado = new DaoEncuestado();
            Class<Participacion> type = Participacion.class;

            resultado = dao.findAll(type);
            for (Participacion obj : resultado) {
                Participacion participacion = dao.find(obj.get_id(), Participacion.class);
                Encuestado encuestado = daoEncuestado.find(participacion.get_encuestado().get_id(),Encuestado.class);

                if (obj.get_solicitudestudio().get_id() == _id) {

                    JsonObject p = Json.createObjectBuilder().add("id", participacion.get_id())
                            .add("Nombre", encuestado.get_nombre())
                            .add("Estado", participacion.get_estado()).build();

                    builder.add(p);

                } else {
                    System.out.println("");
                }
            }

            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200)
                    .add("Participantes", builder).build();


        } catch (Exception ex) {
            String problema = ex.getMessage();

            data = Json.createObjectBuilder()
                    .add("estado", "exception!!!")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 500).build();


            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        //builder.build();
        return Response.status(Response.Status.OK).entity(data).build();
    }
    /**
     * Esta funcion consiste en enviar los datos de un estudio en especifico
     * @author Carlos Silva
     * @param _id corresponde al del estudio
     * que se van a ingresar y la lista de opnciones que se le puede asignar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path("/estudio/{id}")
    public Response buscarEstudio(@PathParam("id") long _id) {
        JsonObject data;
        JsonObject builder;
        try {
            SolicitudEstudio obj = null;

            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            DaoMarca daoMarca = new DaoMarca();
            DaoSubcategoria daoSubcategoria = new DaoSubcategoria ();
            DaoCategoria daoCategoria = new DaoCategoria();

            Class<SolicitudEstudio> type = SolicitudEstudio.class;

            obj = dao.find(_id, type);

            Marca marca = daoMarca.find(obj.get_marca().get_id(), Marca.class);
            Subcategoria subcategoria = daoSubcategoria.find(marca.get_subcategoria().get_id(),Subcategoria.class);
            Categoria categoria = daoCategoria.find(subcategoria.get_categoria().get_id(),Categoria.class);

            JsonObject encuesta = Json.createObjectBuilder().add("Marca", marca.get_nombre())
                    .add("Categoria", categoria.get_nombre())
                    .add("idcategoria", categoria.get_id())
                    .add("idMarca", marca.get_id())
                    .add("idsubcategoria", subcategoria.get_id())
                    .add("Subcategoria", subcategoria.get_nombre()).build();
            JsonObject tipo = Json.createObjectBuilder().add("id", obj.get_id())
                    .add("fecha", obj.get_fecha_inicio().toString())
                    .add("estatus", obj.get_estado())
                    .add("caracteristicas", encuesta)
                    .build();


            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200)
                    .add("estudio", tipo).build();


        } catch (Exception ex) {
            String problema = ex.getMessage();

            data = Json.createObjectBuilder()
                    .add("estado", "exception!!!")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 500).build();


            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        //builder.build();
        return Response.status(Response.Status.OK).entity(data).build();
    }

    /**
     * Esta funcion consiste en enviar los datos de las preguntas que estan relacionadas a una categoria
     * @author Carlos Silva
     * @param _id corresponde al de la categoria
     * que se van a ingresar y la lista de opnciones que se le puede asignar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */

    @GET
    @Path("/preguntas-categoria/{id}")
    public Response Preguntas_categoria(@PathParam("id") long _id) {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<PreguntaEncuesta> resultado = null;
            List<Pregunta> resultado2 = null;

            DaoPregunta daoPregunta = new DaoPregunta();
            DaoEncuesta daoEncuesta = new DaoEncuesta();
            DaoMarca daoMarca = new DaoMarca ();
            DaoPreguntaEncuesta dao = new DaoPreguntaEncuesta();
            DaoSubcategoria daoSubcategoria = new DaoSubcategoria();
            DaoCategoria daoCategoria = new DaoCategoria();

            Class<PreguntaEncuesta> type = PreguntaEncuesta.class;
            Class<Pregunta> type2 = Pregunta.class;



            resultado = dao.findAll(type);
            resultado2 = daoPregunta.findAll(type2);
            for (Pregunta obj2 : resultado2) {
                int cont = 0;
                Pregunta pregunta2 = daoPregunta.find(obj2.get_id(), Pregunta.class);
                for (PreguntaEncuesta obj : resultado) {

                    PreguntaEncuesta preguntaEncuesta = dao.find(obj.get_id(), PreguntaEncuesta.class);
                    Pregunta pregunta = daoPregunta.find(preguntaEncuesta.get_pregunta().get_id(), Pregunta.class);
                    Encuesta encuesta = daoEncuesta.find(preguntaEncuesta.get_encuesta().get_id(), Encuesta.class);
                    Marca marca = daoMarca.find(encuesta.get_marca().get_id(), Marca.class);
                    Subcategoria subcategoria = daoSubcategoria.find(marca.get_subcategoria().get_id(),Subcategoria.class);
                    Categoria categoria = daoCategoria.find(subcategoria.get_categoria().get_id(),Categoria.class);

                    if (categoria.get_id() == _id && pregunta.get_id() == pregunta2.get_id()) {
                        cont=cont+1;
                    }
                }

                if (cont !=0 || pregunta2.get_preguntaencuesta().isEmpty()) {
                    JsonObject p = Json.createObjectBuilder().add("id", pregunta2.get_id())
                            .add("descripcion", pregunta2.get_descripcion())
                            .add("tipopregunta", pregunta2.get_tipopregunta())
                            .build();


                    builder.add(p);
                }

            }
            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200)
                    .add("Preguntas", builder).build();


        } catch (Exception ex) {
            String problema = ex.getMessage();

            data = Json.createObjectBuilder()
                    .add("estado", "exception!!!")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 500).build();


            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        //builder.build();
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }
    /**
     * Esta funcion consiste en enviar los datos de los posibles participantes de un estudio
     * verificando sus datos con las caracteristicas demograficas
     * @author Carlos Silva
     * @param _id corresponde al del estudio
     * que se van a ingresar y la lista de opnciones que se le puede asigbar
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */
    @GET
    @Path("/sugerencia-participacion/{id}")
    public Response add_Participacion(@PathParam("id")  long _id){
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        JsonArrayBuilder aprobado =Json.createArrayBuilder();
        try {
            DaoSolicitudEstudio daoSolicitudEstudio = new DaoSolicitudEstudio();
            DaoEncuestado daoEncuestado = new DaoEncuestado();
            DaoHijo daoHijo = new DaoHijo();
            DaoCaracteristicaDemografica daoCaracteristicaDemografica=new DaoCaracteristicaDemografica();
            DaoUsuario daoUsuario = new DaoUsuario();

            SolicitudEstudio solicitudEstudio = daoSolicitudEstudio.find(_id, SolicitudEstudio.class);

            List<Encuestado> resultado = null;
            Class<Encuestado> type = Encuestado.class;
            resultado = daoEncuestado.findAll(type);

            for (Encuestado obj : resultado) {
                Encuestado encuestado = daoEncuestado.find(obj.get_id(), Encuestado.class);
                Usuario usuario = daoUsuario.find(encuestado.get_usuario_encuestado().get_id(),Usuario.class);
                Date fecha = new Date();

                ZoneId defaultZoneId = ZoneId.systemDefault();

                Instant instant = fecha.toInstant();

                LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();

                ZoneId defaultZoneId2 = ZoneId.systemDefault();

                Instant instant2 = encuestado.get_fecha_nacimiento().toInstant();

                LocalDate localDate2 = instant2.atZone(defaultZoneId2).toLocalDate();

                int edad = Period.between(localDate2, localDate).getYears();
                int hijos = 0;

                List<Hijo> hijo = null;
                Class<Hijo> type2 = Hijo.class;
                hijo = daoHijo.findAll(type2);

                for (Hijo obj2 : hijo) {
                    if (obj2.get_encuestado_hijo().get_id() == encuestado.get_id()) {
                        hijos = hijos + 1;
                    }
                }

                CaracteristicaDemografica caracteristicaDemografica=daoCaracteristicaDemografica.find(solicitudEstudio.get_caracteristicademografica().get_id(), CaracteristicaDemografica.class);

                    if (caracteristicaDemografica.get_edad_min() <= edad && solicitudEstudio.get_caracteristicademografica().get_edad_max() >= edad) {

                        if (caracteristicaDemografica.get_Parroquia_demografia().get_nombre().equals(encuestado.get_Parroquia_encuestado().get_nombre())) {
                            int cont =0;

                            if (caracteristicaDemografica.get_nacionalidad().equals(encuestado.get_Parroquia_encuestado().get_ciudad().get_estado().get_pais().get_nacionalidad())) {
                                cont=cont+1;
                                aprobado.add(Json.createObjectBuilder()
                                        .add("cumple_con_la_nacionalidad", encuestado.get_Parroquia_encuestado().get_ciudad().get_estado().get_pais().get_nacionalidad()));
                            }

                            if (caracteristicaDemografica.get_cantidad_hijos() == hijos) {
                                cont=cont+1;
                                aprobado.add(Json.createObjectBuilder()
                                        .add("cumple_con_la_cantidad_de_hijos", hijos));

                            }

                            if (caracteristicaDemografica.get_genero().equals(encuestado.get_genero())) {
                                cont=cont+1;
                                aprobado.add(Json.createObjectBuilder()
                                        .add("cumple_con_el_genero", encuestado.get_genero()));
                            }

                            if (caracteristicaDemografica.get_nivel_academico_demografia().get_nombre().equals(encuestado.get_nivel_academico_encuestado().get_nombre())) {
                                cont=cont+1;
                                aprobado.add(Json.createObjectBuilder()
                                        .add("cumple_con_el_nivel_academico", encuestado.get_nivel_academico_encuestado().get_nombre()));
                            }

                            if (cont>=2){

                                JsonObject p = Json.createObjectBuilder().add("id", encuestado.get_id())
                                        .add("nombre", encuestado.get_nombre())
                                        .add("apellido", encuestado.get_apellido())
                                        .add("username", usuario.get_usuario())
                                        .add("campos_aprobados", aprobado)
                                        .build();

                                builder.add(p);
                            }

                        }
                    }
                }
                data = Json.createObjectBuilder()
                        .add("estado", "success")
                        .add("codigo", 200)
                        .add("Preguntas", builder).build();


        } catch (Exception ex) {
            String problema = ex.getMessage();

            data = Json.createObjectBuilder()
                    .add("estado", "exception!!!")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 500).build();


            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        //builder.build();
        return Response.status(Response.Status.OK).entity(data).build();
    }
}