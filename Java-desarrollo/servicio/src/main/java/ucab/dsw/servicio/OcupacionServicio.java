package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoNivel_Academico;
import ucab.dsw.accesodatos.DaoOcupacion;
import ucab.dsw.entidades.Nivel_Academico;
import ucab.dsw.entidades.Ocupacion;

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


@Path( "/ocupaciones" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class OcupacionServicio {
    @GET
    @Path("/all")
    public Response getAllOcupaciones() {
        JsonObject data;
        try {
            DaoOcupacion dao = new DaoOcupacion();
            List<Ocupacion> resultado = dao.findAll(Ocupacion.class);
            JsonArrayBuilder ocupacionArrayJson = Json.createArrayBuilder();

            for (Ocupacion obj : resultado) {

                JsonObject ocupacion = Json.createObjectBuilder().add("id", obj.get_id())
                        .add("nombre", obj.get_nombre()).build();

                ocupacionArrayJson.add(ocupacion);
            }
            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200)
                    .add("ocupaciones", ocupacionArrayJson).build();
        } catch (Exception ex) {
            data = Json.createObjectBuilder()
                    .add("estado", "exception!!!")
                    .add("excepcion", ex.getMessage())
                    .add("codigo", 500).build();

            System.out.println(data);
            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }
}
