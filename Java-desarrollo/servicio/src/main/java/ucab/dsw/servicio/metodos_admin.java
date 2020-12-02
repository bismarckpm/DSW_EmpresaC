package ucab.dsw.servicio;
import ucab.dsw.accesodatos.DaoPregunta;
import ucab.dsw.accesodatos.DaoPreguntaEncuesta;
import ucab.dsw.accesodatos.DaoSolicitudEstudio;
import ucab.dsw.dtos.SolicituEstudioDto;
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
import javax.json.JsonArrayBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path( "/prueba" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class metodos_admin {


    @GET
    @Path( "/estudios" )
    public int consultaEstudios_asignados()
    {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        List<SolicitudEstudio> resultado= null;

        DaoSolicitudEstudio dao= new DaoSolicitudEstudio();
        Class<SolicitudEstudio> type = SolicitudEstudio.class;

        resultado= dao.findAll(type);
        for(SolicitudEstudio obj: resultado) {
            builder.add(Json.createObjectBuilder().add("cod_pais", obj.get_id())
                    .add("fecha", obj.get_fecha_inicio().toString())
                    .add("estatus", obj.get_estado()));
            if(obj.get_encuesta() == null) {
                System.out.println("");
            }else{
                System.out.println("Id: " + obj.get_id());
                System.out.println("Marca: " + obj.get_marca().get_nombre());
                System.out.println("Categoria: " + obj.get_marca().get_subcategoria().get_categoria().get_nombre());
                System.out.println("Subcategoria: " + obj.get_marca().get_subcategoria().get_nombre());
            }
        }
        builder.build();
        return 1;
    }

    @GET
    @Path( "/estudios" )
    public int consultaEstudios_no_asignados()
    {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        List<SolicitudEstudio> resultado= null;

        DaoSolicitudEstudio dao= new DaoSolicitudEstudio();
        Class<SolicitudEstudio> type = SolicitudEstudio.class;

        resultado= dao.findAll(type);
        for(SolicitudEstudio obj: resultado) {
            builder.add(Json.createObjectBuilder().add("cod_pais", obj.get_id())
                    .add("fecha", obj.get_fecha_inicio().toString())
                    .add("estatus", obj.get_estado()));
            if(obj.get_encuesta() == null) {
                System.out.println("Id: " + obj.get_id());
                System.out.println("Marca: " + obj.get_marca().get_nombre());
                System.out.println("Categoria: " + obj.get_marca().get_subcategoria().get_categoria().get_nombre());
                System.out.println("Subcategoria: " + obj.get_marca().get_subcategoria().get_nombre());

            }else{
                System.out.println("");
            }
        }
        builder.build();
        return 1;
    }

    @DELETE
    @Path( "/delete/{id}" )
    public int preguntas_categoria_subcategoria(@PathParam("id") long  _id, @PathParam("id") long  _id2)
    {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        List<PreguntaEncuesta> resultado= null;

        DaoPreguntaEncuesta dao= new DaoPreguntaEncuesta();
        Class<PreguntaEncuesta> type = PreguntaEncuesta.class;

        resultado= dao.findAll(type);
        System.out.println("Preguntas por Categoria: ");
        for(PreguntaEncuesta obj: resultado) {

            if(obj.get_encuesta().get_marca().get_subcategoria().get_categoria().get_id() == _id2) {
                System.out.println("Id: " + obj.get_pregunta().get_id());
                System.out.println("Descripcion: " + obj.get_pregunta().get_descripcion());
                System.out.println("Tipo de pregunta: " + obj.get_pregunta().get_tipopregunta());

            }else{
                System.out.println("");
            }
        }

        System.out.println("Preguntas por SubCategoria: ");
        for(PreguntaEncuesta obj: resultado) {

            if(obj.get_encuesta().get_marca().get_subcategoria().get_id() == _id) {
                System.out.println("Id: " + obj.get_pregunta().get_id());
                System.out.println("Descripcion: " + obj.get_pregunta().get_descripcion());
                System.out.println("Tipo de pregunta: " + obj.get_pregunta().get_tipopregunta());

            }else{
                System.out.println("");
            }
        }
        builder.build();
        return 1;
    }

    @DELETE
    @Path( "/delete/{id}" )
    public SolicituEstudioDto asignarEncuesta(@PathParam("id") long  _id, @PathParam("id") long  _id2 )
    {
        SolicituEstudioDto resultado = new SolicituEstudioDto();
        try
        {
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio = dao.find(_id,SolicitudEstudio.class);


            Encuesta encuesta = new Encuesta(_id2);
            solicitudEstudio.set_encuesta( encuesta );

            SolicitudEstudio resul = dao.update(solicitudEstudio);
            resultado.setId( resul.get_id() );
        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  resultado;
    }

    @DELETE
    @Path( "/delete/{id}" )
    public SolicituEstudioDto EliminarEstudio(@PathParam("id") long  _id )
    {
        SolicituEstudioDto resultado = new SolicituEstudioDto();
        try
        {
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio = dao.find(_id,SolicitudEstudio.class);

            solicitudEstudio.set_estado("Eliminado");

            SolicitudEstudio resul = dao.update(solicitudEstudio);
            resultado.setId( resul.get_id() );
        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  resultado;
    }

    @GET
    @Path( "/consulta" )
    public String consulta()
    {
        return "Epa";
    }
}