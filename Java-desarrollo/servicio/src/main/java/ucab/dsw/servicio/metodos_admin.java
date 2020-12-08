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
    @Path( "/estudios-asignados" )
    public Response consultaEstudios_asignados()
    {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<SolicitudEstudio> resultado = null;

            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            Class<SolicitudEstudio> type = SolicitudEstudio.class;

            resultado = dao.findAll(type);
            for (SolicitudEstudio obj : resultado) {
                if (obj.get_encuesta() == null) {
                    System.out.println("");

                } else {
                    JsonObject encuesta = Json.createObjectBuilder().add("Marca:",obj.get_marca().get_nombre())
                            .add("Categoria",obj.get_marca().get_subcategoria().get_categoria().get_nombre())
                            .add("Subcategoria",obj.get_marca().get_subcategoria().get_nombre()).build();
                    JsonObject tipo = Json.createObjectBuilder().add("cod_pais",obj.get_id())
                            .add("fecha",obj.get_fecha_inicio().toString())
                            .add("encuesta",encuesta).build();

                    builder.add(tipo);
                }


            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("categorias",builder).build();

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
    @Path( "/estudios-no-asignados" )
    public Response consultaEstudios_no_asignados()
    {
        JsonObject data;

        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            List<SolicitudEstudio> resultado = null;

            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            Class<SolicitudEstudio> type = SolicitudEstudio.class;

            resultado = dao.findAll(type);
            for (SolicitudEstudio obj : resultado) {
                builder.add(Json.createObjectBuilder().add("cod_pais", obj.get_id())
                        .add("fecha", obj.get_fecha_inicio().toString())
                        .add("estatus", obj.get_estado()));
                if (obj.get_encuesta() == null) {

                    JsonObject encuesta = Json.createObjectBuilder().add("Marca:",obj.get_marca().get_nombre())
                            .add("Categoria",obj.get_marca().get_subcategoria().get_categoria().get_nombre())
                            .add("Subcategoria",obj.get_marca().get_subcategoria().get_nombre()).build();
                    JsonObject tipo = Json.createObjectBuilder().add("cod_pais",obj.get_id())
                            .add("fecha",obj.get_fecha_inicio().toString())
                            .add("estatus", obj.get_estado())
                            .add("encuesta",encuesta)
                            .build();

                    builder.add(tipo);

                } else {
                    System.out.println("");
                }



            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("categorias",builder).build();

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

    @GET
    @Path( "/nose/{id}" )
    public Response preguntas_categoria_subcategoria(@PathParam("id") long  _id, @PathParam("id") long  _id2)
    {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        List<Respuesta> resultado= null;

        DaoRespuesta dao= new DaoRespuesta();
        Class<Respuesta> type = Respuesta.class;

        resultado= dao.findAll(type);
        System.out.println("Preguntas por Categoria: ");
        for(Respuesta obj: resultado) {

            if(obj.get_preguntaencuesta().get_encuesta().get_marca().get_subcategoria().get_categoria().get_id() == _id2) {
                System.out.println("Id: " + obj.get_preguntaencuesta().get_pregunta().get_id());
                System.out.println("Descripcion: " + obj.get_preguntaencuesta().get_pregunta().get_descripcion());
                System.out.println("Tipo de pregunta: " + obj.get_preguntaencuesta().get_pregunta().get_tipopregunta());
                if(obj.get_preguntaencuesta().get_pregunta().get_valormax()!= 0){
                    System.out.println("Rango minimo: " + obj.get_preguntaencuesta().get_pregunta().get_valormin());
                    System.out.println("Rango maximo: " + obj.get_preguntaencuesta().get_pregunta().get_valormax());
                }

            }else{
                System.out.println("");
            }
        }

        System.out.println("Preguntas por SubCategoria: ");
        for(Respuesta obj: resultado) {

            if(obj.get_preguntaencuesta().get_encuesta().get_marca().get_subcategoria().get_id() == _id) {
                System.out.println("Id: " + obj.get_preguntaencuesta().get_pregunta().get_id());
                System.out.println("Descripcion: " + obj.get_preguntaencuesta().get_pregunta().get_descripcion());
                System.out.println("Tipo de pregunta: " + obj.get_preguntaencuesta().get_pregunta().get_tipopregunta());
                if(obj.get_preguntaencuesta().get_pregunta().get_valormax()!= 0){
                    System.out.println("Rango minimo: " + obj.get_preguntaencuesta().get_pregunta().get_valormin());
                    System.out.println("Rango maximo: " + obj.get_preguntaencuesta().get_pregunta().get_valormax());
                }
            }else{
                System.out.println("");
            }
        }
        //builder.build();
        return Response.status(Response.Status.OK).entity(builder).build();
    }

    @PUT
    @Path( "/asignarEncuesta/{id}" )
    public Response asignarEncuesta(@PathParam("id") long  _id, @PathParam("id") long  _id2 )
    {
        JsonObject data;
        SolicituEstudioDto resultado = new SolicituEstudioDto();
        try
        {
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio = dao.find(_id,SolicitudEstudio.class);


            Encuesta encuesta = new Encuesta(_id2);
            solicitudEstudio.set_encuesta( encuesta );

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
    @Path( "/addEncuesta/{id}" )
    public Response addEncuesta(@PathParam("id") long  _id,EncuestaDto encuestaDto,List<Pregunta> pregunta)
    {
        EncuestaDto resultado = new EncuestaDto();
        JsonObject data;
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


            for(Pregunta obj: pregunta) {
                Pregunta_EncuestaDto resultado2 = new Pregunta_EncuestaDto();
                PreguntaEncuesta preguntaEncuesta = new PreguntaEncuesta();
                preguntaEncuesta.set_encuesta(resul);
                preguntaEncuesta.set_pregunta(obj);

                PreguntaEncuesta resul2 = dao2.insert( preguntaEncuesta);
                resultado2.setId( resul2.get_id() );

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
            Pregunta pregunta = new Pregunta();
            pregunta.set_descripcion( preguntaDto.getDescripcion() );
            pregunta.set_tipopregunta( preguntaDto.getTipopregunta() );
            pregunta.set_valormax( preguntaDto.getValormax() );
            pregunta.set_valormin( preguntaDto.getValormin() );

            Pregunta resul = dao.insert( pregunta);
            resultado.setId( resul.get_id() );

            System.out.println("Id: " + resul.get_id());
            System.out.println("Descripcion: " + preguntaDto.getDescripcion() );
            System.out.println("Tipo de pregunta: "+ preguntaDto.getTipopregunta() );
            if(preguntaDto.getValormax()!= 0){
                System.out.println("Rango minimo: " + preguntaDto.getValormin());
                System.out.println("Rango maximo: " + preguntaDto.getValormax());
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
                            .add("Participante",obj.get_encuestado().get_nombre()).build();

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
    @Path( "/test" )
    public String consulta()
    {
        return "test";
    }
}