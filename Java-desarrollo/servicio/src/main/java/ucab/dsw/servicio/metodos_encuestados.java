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
            DaoMarca daoMarca = new DaoMarca();
            Class<Participacion> type = Participacion.class;

            resultado = dao.findAll(type);
            for (Participacion obj : resultado) {
                Participacion participacion = dao.find(obj.get_id(), Participacion.class);
                Marca marca = daoMarca.find(participacion.get_solicitudestudio().get_marca().get_id(), Marca.class);
                if (participacion.get_encuestado().get_id() == _id && participacion.get_solicitudestudio().get_estado().equals("en progreso")) {
                    JsonObject encuesta = Json.createObjectBuilder().add("Marca", marca.get_nombre())
                            .add("idcategoria", marca.get_subcategoria().get_categoria().get_id())
                            .add("Categoria", marca.get_subcategoria().get_categoria().get_nombre())
                            .add("idsubcategoria", marca.get_subcategoria().get_id())
                            .add("Subcategoria", marca.get_subcategoria().get_nombre()).build();

                    JsonObject tipo = Json.createObjectBuilder().add("id", participacion.get_solicitudestudio().get_id())
                            .add("fecha", participacion.get_solicitudestudio().get_fecha_inicio().toString())
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

                                opciones.add(Json.createObjectBuilder().add("opcion", opcion.get_opcionsimplemultiple().get_opcion()));

                            }


                        }
                        JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                .add("Pregunta ", preguntaEncuesta.get_pregunta().get_descripcion())
                                .add("Tipo de Pregunta ", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                .add("participacion",opciones)
                                .build();

                        builder.add(preguntas);
                    }
                    else {

                        JsonObject preguntas = Json.createObjectBuilder().add("id", preguntaEncuesta.get_id())
                                .add("Pregunta ", preguntaEncuesta.get_pregunta().get_descripcion())
                                .add("Tipo de Pregunta ", preguntaEncuesta.get_pregunta().get_tipopregunta())
                                .build();

                        builder.add(preguntas);

                        if (preguntaEncuesta.get_pregunta().get_valormax() !=0){
                            JsonObject p = Json.createObjectBuilder().add("valor minimo ", preguntaEncuesta.get_pregunta().get_valormin())
                                    .add("valor maximo ", preguntaEncuesta.get_pregunta().get_valormax()).build();
                            builder.add(p);
                        }
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
}
