package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoNivelAcademico;
import ucab.dsw.entidades.NivelAcademico;

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

/**
 * Una clase para la administracion los niveles academicos
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 */
@Path( "/niveles_academicos" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class NivelAcademicoServicio extends AplicacionBase {

    /**
    * Esta funcion consiste en traer todos los niveles academicos disponibles
    * @author Gabriel Romero
    * @throws Exception si ocurre cualquier excepcion general no controlada previamente
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, niveles_academicos (array de objetos) 
    *         y mensaje en caso de ocurrir alguna de las excepciones.
    */
    @GET
    @Path("/all")
    public Response getAllNivelesAcademicos() {
        JsonObject data;
        try {
            DaoNivelAcademico dao = new DaoNivelAcademico();
            List<NivelAcademico> resultado = dao.findAll(NivelAcademico.class);

            JsonArrayBuilder categoriaArrayJson = Json.createArrayBuilder();

            for (NivelAcademico obj : resultado) {

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