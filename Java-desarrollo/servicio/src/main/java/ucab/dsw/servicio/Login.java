package ucab.dsw.servicio;

import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.jwt.Jwt;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path( "/login" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class Login extends AplicacionBase{
    @POST
    @Path( "/ldap" )
    public Response loginLdap(UsuarioLdapDto usuarioLdapDto)
    {
        String token="";


        try
        {
            DirectorioActivo ldap = new DirectorioActivo();
            long resultado=ldap.userAuthentication( usuarioLdapDto );

            if(resultado==1){

                Jwt jwt=new Jwt();
                token= jwt.generarToken(usuarioLdapDto);
                JsonObject data= Json.createObjectBuilder()
                                     .add("token-jwt",token).build();

                System.out.println(token);
                System.out.println(Response.status(Response.Status.OK).entity(data).build());
                return Response.status(Response.Status.OK).entity(data).build();


            }else{
                System.out.println("Credenciales Incorrectas");
                System.out.println(Response.status(Response.Status.UNAUTHORIZED).build());
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

        }
        catch ( Exception ex )
        {
            System.out.println("Excepcion");

            return Response.status(Response.Status.BAD_REQUEST).build();


        }

    }
}
