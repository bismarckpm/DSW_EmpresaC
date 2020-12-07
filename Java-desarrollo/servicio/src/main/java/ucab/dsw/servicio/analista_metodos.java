package ucab.dsw.servicio;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;

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
import javax.json.JsonArrayBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path( "/prueba" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class analista_metodos {


    @GET
    @Path("/estudios")
    public int consultaEstudios_asignados(long  _id) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        List<SolicitudEstudio> resultado = null;

        DaoSolicitudEstudio dao = new DaoSolicitudEstudio();
        Class<SolicitudEstudio> type = SolicitudEstudio.class;

        resultado = dao.findAll(type);
        for (SolicitudEstudio obj : resultado) {
            builder.add(Json.createObjectBuilder().add("cod_pais", obj.get_id())
                    .add("fecha", obj.get_fecha_inicio().toString())
                    .add("estatus", obj.get_estado()));
            if (obj.get_usuario().get_id() == _id) {
                System.out.println("Id: " + obj.get_id());
                System.out.println("Estado: " + obj.get_estado());
                System.out.println("Marca: " + obj.get_marca().get_nombre());
                System.out.println("Categoria: " + obj.get_marca().get_subcategoria().get_categoria().get_nombre());
                System.out.println("Subcategoria: " + obj.get_marca().get_subcategoria().get_nombre());

            } else {
                System.out.println("");
            }
        }
        builder.build();
        return 1;
    }
}