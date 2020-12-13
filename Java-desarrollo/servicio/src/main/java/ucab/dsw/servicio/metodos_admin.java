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

@Path( "/admin" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class metodos_admin {


    @GET
    @Path( "/estudios-asignados/{id}" )
    public Response consultaEstudios_asignados(@PathParam("id")long  _id)
    {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<SolicitudEstudio> resultado = null;

            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            Class<SolicitudEstudio> type = SolicitudEstudio.class;

            resultado = dao.findAll(type);
            for (SolicitudEstudio obj : resultado) {
                if (obj.get_usuario2() != null) {


                    if (obj.get_encuesta() != null && obj.get_usuario2().get_id() == _id && obj.get_estado().equals("en ejecucion")) {
                        JsonObject encuesta = Json.createObjectBuilder().add("Marca", obj.get_marca().get_nombre())
                                .add("idcategoria", obj.get_marca().get_subcategoria().get_categoria().get_id())
                                .add("Categoria", obj.get_marca().get_subcategoria().get_categoria().get_nombre())
                                .add("idsubcategoria", obj.get_marca().get_subcategoria().get_id())
                                .add("Subcategoria", obj.get_marca().get_subcategoria().get_nombre()).build();

                        JsonObject tipo = Json.createObjectBuilder().add("id", obj.get_id())
                                .add("fecha", obj.get_fecha_inicio().toString())
                                .add("caracteristicas", encuesta).build();

                        builder.add(tipo);


                    } else {
                        System.out.println("");
                    }
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

    @GET
    @Path( "/estudios-no-asignados/{id}" )
    public Response consultaEstudios_no_asignados(@PathParam("id")long  _id)
    {
        JsonObject data;

        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            List<SolicitudEstudio> resultado = null;

            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            Class<SolicitudEstudio> type = SolicitudEstudio.class;

            resultado = dao.findAll(type);
            for (SolicitudEstudio obj : resultado) {
                if (obj.get_usuario2() != null) {

                if (obj.get_encuesta() == null && obj.get_usuario2().get_id()== _id) {


                    JsonObject encuesta = Json.createObjectBuilder().add("Marca", obj.get_marca().get_nombre())
                            .add("idcategoria", obj.get_marca().get_subcategoria().get_categoria().get_id())
                            .add("Categoria", obj.get_marca().get_subcategoria().get_categoria().get_nombre())
                            .add("idsubcategoria", obj.get_marca().get_subcategoria().get_id())
                            .add("Subcategoria", obj.get_marca().get_subcategoria().get_nombre()).build();
                    JsonObject tipo = Json.createObjectBuilder().add("id", obj.get_id())
                            .add("fecha", obj.get_fecha_inicio().toString())
                            .add("estatus", obj.get_estado())
                            .add("caracteristicas", encuesta)
                            .build();

                    builder.add(tipo);
                }
                } else {
                    System.out.println("");
                }



            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("estudios",builder).build();

        }
        catch (Exception ex){

            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();

        }
        //builder.build();
        return Response.status(Response.Status.OK).entity(data).build();
    }




    @DELETE
    @Path( "/delete-solicitud/{id}" )
    public Response EliminarEstudio(@PathParam("id") long  _id )
    {
        JsonObject data;
        SolicituEstudioDto resultado = new SolicituEstudioDto();
        try
        {
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio = dao.find(_id,SolicitudEstudio.class);

            solicitudEstudio.set_estado("Eliminado");

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

    @PUT
    @Path( "/addEncuesta/{id}/{id2}" )
    public Response addEncuesta(@PathParam("id") long  _id,@PathParam("id2") long  _id2,EncuestaDto encuestaDto)
    {
        EncuestaDto resultado = new EncuestaDto();
        SolicituEstudioDto resultado3 = new SolicituEstudioDto();
        JsonObject data;
        int analista_random=0;
        Usuario analista_elegido=null;
        try
        {
            DaoEncuesta dao = new DaoEncuesta();
            DaoPreguntaEncuesta dao2= new DaoPreguntaEncuesta();

            Encuesta encuesta = new Encuesta();
            encuesta.set_nombre( encuestaDto.getNombre() );

            Marca marca = new Marca(_id);
            encuesta.set_marca( marca );

            Encuesta resul = dao.insert( encuesta);
            resultado.setId( resul.get_id() );

            DaoSolicitudEstudio dao3 = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio = dao3.find(_id2,SolicitudEstudio.class);

            DaoUsuario daoUsuario=new DaoUsuario();
            List<Usuario> analista= daoUsuario.getAnalistas();
            analista_random=(int)(Math.random()* analista.size());
            System.out.println("analista random");
            System.out.println(analista_random);
            analista_elegido=analista.get(analista_random);
            solicitudEstudio.set_usuario(analista_elegido);

            solicitudEstudio.set_estado( "Pendiente" );
            solicitudEstudio.set_encuesta( resul );

            SolicitudEstudio resul3 = dao3.update(solicitudEstudio);
            resultado3.setId( resul3.get_id() );

            if (encuestaDto.getPreguntas()!=null) {

                List<PreguntaDto> pregunta = encuestaDto.getPreguntas();

                for (PreguntaDto obj : pregunta) {
                    Pregunta_EncuestaDto resultado2 = new Pregunta_EncuestaDto();
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

    @PUT
    @Path( "/addPregunta" )
    public Response addPregunta(PreguntaDto preguntaDto)
    {
        PreguntaDto resultado = new PreguntaDto();
        JsonObject data;
        try
        {
            DaoPregunta dao = new DaoPregunta();
            DaoOpcionSimpleMultiple dao2 = new DaoOpcionSimpleMultiple();
            DaoOpcion_Simple_Multiple_Pregunta dao3 = new DaoOpcion_Simple_Multiple_Pregunta();
            Pregunta pregunta = new Pregunta();
            pregunta.set_descripcion( preguntaDto.getDescripcion() );
            pregunta.set_tipopregunta( preguntaDto.getTipopregunta() );

            if (preguntaDto.getTipopregunta().equals("Rango")) {
                pregunta.set_valormax(preguntaDto.getValormax());
                pregunta.set_valormin(preguntaDto.getValormin());
            }
            Pregunta resul = dao.insert( pregunta);
            resultado.setId( resul.get_id() );

            JsonObject p = Json.createObjectBuilder().add("id", resul.get_id())
                    .build();

            System.out.println("Id: " + resul.get_id());
            System.out.println("Descripcion: " + preguntaDto.getDescripcion() );
            System.out.println("Tipo de pregunta: "+ preguntaDto.getTipopregunta() );
            if(preguntaDto.getValormax()!= 0){
                System.out.println("Rango minimo: " + preguntaDto.getValormin());
                System.out.println("Rango maximo: " + preguntaDto.getValormax());
            }


            if (preguntaDto.getOpciones()!=null) {

                List<Opcion_Simple_MultipleDto> opcion = preguntaDto.getOpciones();

                for (Opcion_Simple_MultipleDto obj : opcion) {
                    Opcion_Simple_MultipleDto resultado2 = new Opcion_Simple_MultipleDto();

                    OpcionSimpleMultiple opcionSimpleMultiple = new OpcionSimpleMultiple();
                    opcionSimpleMultiple.set_opcion(obj.getOpcion());

                    OpcionSimpleMultiple resul2 = dao2.insert(opcionSimpleMultiple);
                    resultado2.setId(resul2.get_id());

                    Opcion_Simple_Multiple_PreguntaDto resultado3 = new Opcion_Simple_Multiple_PreguntaDto();
                    Opcion_Simple_Multiple_Pregunta opcion_Simple_Multiple_Pregunta = new Opcion_Simple_Multiple_Pregunta();
                    opcion_Simple_Multiple_Pregunta.set_opcion_Simple_Multiple_Pregunta(resul2);
                    opcion_Simple_Multiple_Pregunta.set_pregunta(resul);

                    Opcion_Simple_Multiple_Pregunta resul3 = dao3.insert(opcion_Simple_Multiple_Pregunta);
                    resultado3.setId(resul3.get_id());
                }
            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("Pregunta",p).build();



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



    @GET
    @Path( "/estudios-participacion/{id}" )
    public Response Participacion_estudio(@PathParam("id")long  _id)
    {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<Participacion> resultado = null;

            DaoParticipacion dao = new DaoParticipacion();
            Class<Participacion> type = Participacion.class;

            resultado = dao.findAll(type);
            for (Participacion obj : resultado) {

                if (obj.get_solicitudestudio().get_id() == _id) {

                    JsonObject p = Json.createObjectBuilder().add("id",obj.get_id())
                            .add("Participante",obj.get_encuestado().get_nombre())
                            .add("Estado",obj.get_estado()).build();

                    builder.add(p);

                } else {
                    System.out.println("");
                }
            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("categorias",builder).build();


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
        //builder.build();
        return Response.status(Response.Status.OK).entity(data).build();
    }

    @GET
    @Path( "/estudio/{id}" )
    public Response buscarEstudio(@PathParam("id")long  _id)
    {
        JsonObject data;
        JsonObject builder;
        try {
            SolicitudEstudio obj = null;

            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            Class<SolicitudEstudio> type = SolicitudEstudio.class;

            obj = dao.find(_id,type);

            JsonObject encuesta = Json.createObjectBuilder().add("Marca",obj.get_marca().get_nombre())
                    .add("Categoria",obj.get_marca().get_subcategoria().get_categoria().get_nombre())
                    .add("Subcategoria",obj.get_marca().get_subcategoria().get_nombre()).build();
            JsonObject tipo = Json.createObjectBuilder().add("id",obj.get_id())
                    .add("fecha",obj.get_fecha_inicio().toString())
                    .add("estatus", obj.get_estado())
                    .add("caracteristicas",encuesta)
                    .build();



            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("estudio",tipo).build();


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
        //builder.build();
        return Response.status(Response.Status.OK).entity(data).build();
    }

    @GET
    @Path( "/test" )
    public String consulta()
    {
        return "test";
    }

    @GET
    @Path( "/preguntas-categoria/{id}" )
    public Response Preguntas_categoria(@PathParam("id")long  _id)
    {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<PreguntaEncuesta> resultado = null;
            List<Pregunta> resultado2 = null;

            DaoPreguntaEncuesta dao = new DaoPreguntaEncuesta();
            Class<PreguntaEncuesta> type = PreguntaEncuesta.class;

            DaoPregunta dao2 = new DaoPregunta();
            Class<Pregunta> type2 = Pregunta.class;

            resultado2 = dao2.findAll(type2);

            resultado = dao.findAll(type);

            for (PreguntaEncuesta obj : resultado) {

                if (obj.get_encuesta().get_marca().get_subcategoria().get_categoria().get_id() == _id) {

                    JsonObject p = Json.createObjectBuilder().add("id", obj.get_pregunta().get_id())
                            .add("descripcion", obj.get_pregunta().get_descripcion())
                            .add("tipopregunta", obj.get_pregunta().get_tipopregunta())
                            .build();


                    builder.add(p);

                }
            }

            for (Pregunta obj : resultado2) {

                if (obj.get_preguntaencuesta().isEmpty() == true) {
                    JsonObject p = Json.createObjectBuilder().add("id", obj.get_id())
                            .add("descripcion", obj.get_descripcion())
                            .add("tipopregunta", obj.get_tipopregunta())
                            .build();


                    builder.add(p);
                }
            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("Preguntas",builder).build();


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
        //builder.build();
        return Response.status(Response.Status.OK).entity(data).build();
    }
}