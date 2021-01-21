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
import ucab.dsw.logica.comando.admin.*;

import javax.ws.rs.*;

@Path( "/admin" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class AdminServicio {

    /**
     * Esta funcion consiste en enviar los datos de los estudios que tiene asignado un admin
     * en especifico y que ya se le asigno una encuesta
     * @author Carlos Silva
     * @return retorna una Response con un estado de respuesta http indicando si la operacion
     *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto
     *         en formato JSON con los siguiente atributos: codigo, estado, estudios (array de objetos)
     *         y mensaje en caso de ocurrir alguna de las excepciones
     */



    @GET
    @Path( "/estudios-asignados/{id}" )
    public Response consultaEstudios_asignados(@PathParam("id") long  _id)
    {
        JsonObject resul;

        try
        {
            ConsultaEstudiosAsignadosComando comando= Fabrica.crearComandoConId(ConsultaEstudiosAsignadosComando.class,_id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","internal_server_error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
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
        JsonObject resul;

        try
        {
            ConsultaEstudiosNoAsignadosComando comando= Fabrica.crearComandoConId(ConsultaEstudiosNoAsignadosComando.class,_id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","internal_server_error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
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
        JsonObject resul;
        try
        {
            EliminarEstudioComando comando=Fabrica.crearComandoConId(EliminarEstudioComando.class,_id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","internal_server_error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
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
        JsonObject resul;
        try
        {
            AddPreguntaComando comando=Fabrica.crearComandoConDto(AddPreguntaComando.class,preguntaDto);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","internal_server_error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
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
        JsonObject resul;
        try
        {
            ParticipacionEstudioComando comando=Fabrica.crearComandoConId(ParticipacionEstudioComando.class,_id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","internal_server_error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
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
        JsonObject resul;
        try
        {
            BuscarEstudioComando comando=Fabrica.crearComandoConId(BuscarEstudioComando.class,_id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","internal_server_error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
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
        JsonObject resul;
        try
        {
            PreguntasCategoriaComando comando=Fabrica.crearComandoConId(PreguntasCategoriaComando.class,_id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            resul= Json.createObjectBuilder()
                    .add("estado","internal_server_error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(resul).build();
        }
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