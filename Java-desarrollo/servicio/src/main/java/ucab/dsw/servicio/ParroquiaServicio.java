package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoParroquia;
import ucab.dsw.entidades.Parroquia;

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

@Path( "/parroquia" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class ParroquiaServicio extends AplicacionBase {
    @GET
    @Path( "/all" )
    public Response getAllParroquias()
    {
        JsonObject data;
        try
        {
            DaoParroquia dao= new DaoParroquia();
            List<Parroquia> resultado= dao.findAll(Parroquia.class);

            JsonArrayBuilder paisesArrayJson= Json.createArrayBuilder();

            for(Parroquia obj: resultado){

                JsonObject parroquia = Json.createObjectBuilder().add("id",obj.get_id())
                                                            .add("nombre",obj.get_nombre())
                                                            .add("ciudad_id",obj.get_ciudad().get_id())
                                                            .add("estado_id",obj.get_ciudad().get_estado().get_id())
                                                            .add("pais_id",obj.get_ciudad().get_estado().get_pais().get_id()).build();

                paisesArrayJson.add(parroquia);

            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("parroquias",paisesArrayJson).build();


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