package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoPais;
import ucab.dsw.entidades.Pais;


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

@Path( "/pais" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class PaisServicio {
    @GET
    @Path( "/all" )
    public Response getAllPaises()
    {
        JsonObject data;
        try
        {
            DaoPais dao= new DaoPais();
            List<Pais> resultado= dao.findAll(Pais.class);

            JsonArrayBuilder paisesArrayJson= Json.createArrayBuilder();

            for(Pais obj: resultado){

                JsonObject pais = Json.createObjectBuilder().add("id",obj.get_id())
                                                            .add("nombre",obj.get_nombre()).build();

                paisesArrayJson.add(pais);

            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("paises",paisesArrayJson).build();


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
