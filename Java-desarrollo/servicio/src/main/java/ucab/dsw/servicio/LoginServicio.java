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
public class LoginServicio extends AplicacionBase{
    @POST
    @Path( "/ldap" )
    public Response loginLdap(UsuarioLdapDto usuarioLdapDto)
    {
        String token="";
        JsonObject data;

        try
        {
            DirectorioActivo ldap = new DirectorioActivo();
            long resultado=ldap.userAuthentication( usuarioLdapDto );

            if(resultado==1){

                Jwt jwt=new Jwt();
                token= jwt.generarToken(usuarioLdapDto);
                data= Json.createObjectBuilder()
                                     .add("estado","success")
                                     .add("codigo",200)
                                     .add("token-jwt",token)
                                     .add("rol", ldap.getEntryRole(usuarioLdapDto)).build();

                System.out.println(data);
                return Response.status(Response.Status.OK).entity(data).build();


            }else{
                data= Json.createObjectBuilder()
                        .add("estado","error")
                        .add("codigo",401).build();

                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }

        }
        catch ( Exception ex )
        {
            System.out.println("Excepcion");

            return Response.status(Response.Status.BAD_REQUEST).build();


        }

    }
}
