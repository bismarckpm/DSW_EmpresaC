package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoCiudad;
import ucab.dsw.entidades.Ciudad;

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


@Path( "/ciudad" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class CiudadServicio extends AplicacionBase{
    @GET
    @Path( "/all" )
    public Response getAllCiudades()
    {
        JsonObject data;
        try
        {
            DaoCiudad dao= new DaoCiudad();
            List<Ciudad> resultado= dao.findAll(Ciudad.class);

            JsonArrayBuilder ciudadesArrayJson= Json.createArrayBuilder();

            for(Ciudad obj: resultado){

                JsonObject ciudad = Json.createObjectBuilder().add("id",obj.get_id())
                                                                .add("nombre",obj.get_nombre())
                                                                .add("estado_id",obj.get_estado().get_id())
                                                                .add("pais_id",obj.get_estado().get_pais().get_id()).build();

                ciudadesArrayJson.add(ciudad);

            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("ciudades",ciudadesArrayJson).build();


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