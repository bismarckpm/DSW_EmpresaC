package ucab.dsw.servicio;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path( "" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class RequenaTest extends AplicacionBase
{
    @GET
    @Path( "/enviar1" )
    public String enviarString() {
        return "Este es un mensaje de prueba de ElRequena";
    }

    @GET
    @Path( "/enviar2" )
    public JsonArray enviarArreglo() {
        return Json.createArrayBuilder().
                add(objeto("Saludos","cordiales")).
                add(objeto("desde","el")).
                add(objeto("Backend","!!!")).build();
    }

    public JsonObject objeto(String nombre, String clave){
        return Json.createObjectBuilder().
                add("nombre", nombre).
                add("clave", clave).build();
    }
}
