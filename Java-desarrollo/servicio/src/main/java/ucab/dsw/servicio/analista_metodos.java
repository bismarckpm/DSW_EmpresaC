package ucab.dsw.servicio;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;

import javax.json.JsonObject;
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
import javax.ws.rs.core.Response;
import javax.json.JsonArrayBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path( "/analista" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class analista_metodos {


    @GET
    @Path( "/estudios-analistas/{id}" )
    public Response Estudios_asignados(@PathParam("id")long  _id)
    {
        JsonObject data;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            List<SolicitudEstudio> resultado = null;

            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            Class<SolicitudEstudio> type = SolicitudEstudio.class;

            resultado = dao.findAll(type);
            for (SolicitudEstudio obj : resultado) {
                if (obj.get_usuario() != null) {
                    if (obj.get_usuario().get_id() == _id) {

                        JsonObject p = Json.createObjectBuilder().add("id: ", obj.get_id())
                                .add("Estado: ", obj.get_estado())
                                .add("Marca : ", obj.get_marca().get_nombre())
                                .add("Categoria : ", obj.get_marca().get_subcategoria().get_categoria().get_nombre())
                                .add("SubCategoria : ", obj.get_marca().get_subcategoria().get_nombre())
                                .build();


                        builder.add(p);

                    }
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

    @PUT
    @Path( "/Empezar-estudio/{id}" )
    public Response Empezar_estudio(@PathParam("id") long  _id )
    {
        JsonObject data;
        SolicituEstudioDto resultado = new SolicituEstudioDto();
        try
        {
            DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
            SolicitudEstudio solicitudEstudio = dao.find(_id,SolicitudEstudio.class);

            solicitudEstudio.set_estado( "En progreso" );

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
}