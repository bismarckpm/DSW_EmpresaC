package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoNivel_Academico;
import ucab.dsw.entidades.Nivel_Academico;

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


@Path( "/niveles_academicos" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class NivelAcademicoServicio extends AplicacionBase {
    @GET
    @Path("/all")
    public Response getAllNivelesAcademicos() {
        JsonObject data;
        try {
            DaoNivel_Academico dao = new DaoNivel_Academico();
            List<Nivel_Academico> resultado = dao.findAll(Nivel_Academico.class);

            JsonArrayBuilder categoriaArrayJson = Json.createArrayBuilder();

            for (Nivel_Academico obj : resultado) {

                JsonObject categoria = Json.createObjectBuilder().add("id", obj.get_id())
                        .add("nombre", obj.get_nombre()).build();

                categoriaArrayJson.add(categoria);
            }

            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200)
                    .add("niveles_academicos", categoriaArrayJson).build();


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