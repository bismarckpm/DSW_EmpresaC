package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoMetodo_Conexion;
import ucab.dsw.accesodatos.DaoNivel_Academico;
import ucab.dsw.accesodatos.DaoOcupacion;
import ucab.dsw.entidades.Metodo_conexion;
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


@Path( "/metodos_conexion" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class MetodoConexionServicio {
    @GET
    @Path("/all")
    public Response getAllMetodoConexion() {
        JsonObject data;
        try {
            DaoMetodo_Conexion dao = new DaoMetodo_Conexion();
            List<Metodo_conexion> resultado = dao.findAll(Metodo_conexion.class);
            JsonArrayBuilder metodosConexionArrayJson = Json.createArrayBuilder();

            for (Metodo_conexion obj : resultado) {

                JsonObject metodosConexion = Json.createObjectBuilder().add("id", obj.get_id())
                        .add("nombre", obj.get_nombre()).build();

                metodosConexionArrayJson.add(metodosConexion);
            }
            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200)
                    .add("metodos_conexion", metodosConexionArrayJson).build();
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
