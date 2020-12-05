package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoEstado;
import ucab.dsw.entidades.Estado;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path( "/estado" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class EstadoServicio extends AplicacionBase {
    @GET
    @Path( "/all" )
    public Response getAllEstados()
    {
        JsonObject data;
        try
        {
            DaoEstado dao= new DaoEstado();
            List<Estado> resultado= dao.findAll(Estado.class);

            JsonArrayBuilder estadosArrayJson= Json.createArrayBuilder();

            for(Estado obj: resultado){

                JsonObject parroquia = Json.createObjectBuilder().add("id",obj.get_id())
                                                                .add("nombre",obj.get_nombre())
                                                                .add("pais_id",obj.get_pais().get_id()).build();

                estadosArrayJson.add(parroquia);

            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("estados",estadosArrayJson).build();


        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            System.out.println(data);
            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();


        }

        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();

    }
}
