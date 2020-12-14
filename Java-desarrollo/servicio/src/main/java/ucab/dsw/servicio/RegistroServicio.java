package ucab.dsw.servicio;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class RegistroServicio extends AplicacionBase{

    @POST
    @Path( "/registro" )
    public Response registro(  ){
        try {

            return Response.status(Response.Status.OK).build();
        }catch ( Exception ex ) {
            System.out.println("Excepcion");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
