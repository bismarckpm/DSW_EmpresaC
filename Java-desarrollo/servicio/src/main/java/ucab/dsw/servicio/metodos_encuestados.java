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


    @GET
    @Path( "/estudios-asignados/{id}" )
    public Response consultaEstudios_asignados(@PathParam("id")long  _id)
    {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<Participacion> resultado = null;

            DaoParticipacion dao = new DaoParticipacion();
            Class<Participacion> type = Participacion.class;

            resultado = dao.findAll(type);
            for (Participacion obj : resultado) {


                if (obj.get_encuestado().get_id() == _id && obj.get_solicitudestudio().get_estado().equals("en ejecucion")) {
                    JsonObject encuesta = Json.createObjectBuilder().add("Marca", obj.get_solicitudestudio().get_marca().get_nombre())
                            .add("idcategoria", obj.get_solicitudestudio().get_marca().get_subcategoria().get_categoria().get_id())
                            .add("Categoria", obj.get_solicitudestudio().get_marca().get_subcategoria().get_categoria().get_nombre())
                            .add("idsubcategoria", obj.get_solicitudestudio().get_marca().get_subcategoria().get_id())
                            .add("Subcategoria", obj.get_solicitudestudio().get_marca().get_subcategoria().get_nombre()).build();

                    JsonObject tipo = Json.createObjectBuilder().add("id", obj.get_id())
                            .add("fecha", obj.get_solicitudestudio().get_fecha_inicio().toString())
                            .add("caracteristicas", encuesta).build();

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


    @GET
    @Path( "/encuesta-estudio/{id}" )
    public Response encuesta_estudio(@PathParam("id")long  _id)
    {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<PreguntaEncuesta> resultado = null;

            DaoPreguntaEncuesta dao = new DaoPreguntaEncuesta();
            DaoSolicitudEstudio dao2 = new DaoSolicitudEstudio();

            SolicitudEstudio solicitudEstudio = new SolicitudEstudio();

            solicitudEstudio = dao2.find(_id,SolicitudEstudio.class);

            Class<PreguntaEncuesta> type = PreguntaEncuesta.class;

            resultado = dao.findAll(type);
            for (PreguntaEncuesta obj : resultado) {


                if (obj.get_encuesta().get_id() == solicitudEstudio.get_encuesta().get_id() ) {
                    JsonObject encuesta = Json.createObjectBuilder().add("Encuesta ", obj.get_encuesta().get_nombre())
                            .add("Pregunta ", obj.get_pregunta().get_descripcion())
                            .add("Tipo de Pregunta ", obj.get_pregunta().get_tipopregunta()).build();
                    builder.add(encuesta);

                    if (obj.get_pregunta().get_valormax() !=0){
                        JsonObject p = Json.createObjectBuilder().add("valor minimo ", obj.get_pregunta().get_valormin())
                                .add("valor maximo ", obj.get_pregunta().get_valormax()).build();
                        builder.add(p);
                        System.out.println("id" + obj.get_id());
                    }

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
}
