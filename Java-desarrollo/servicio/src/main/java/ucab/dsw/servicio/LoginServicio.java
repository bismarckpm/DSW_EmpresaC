package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.jwt.Jwt;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Una clase para el inicio de sesion en MERCADEOUCAB
 * @version 1.0, 02/01/2021
 * @author Gabriel Romero
 */
@Path( "/login" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class LoginServicio extends AplicacionBase{

    /**
    * Esta funcion consiste en validar las credenciales de un usuario en nuestro servidor LDAP, y
    * de esta manera, proporcionar el acceso a las diferentes opciones segun el rol. 
    * @author Gabriel Romero
    * @param usuarioLdapDto corresponde al objeto de la capa web que contiene los datos a validar (usuario/correo y contraseña)
    * @return retorna una Response con un estado de respuesta http indicando si la operacion 
    *         se realizo o no correctamente. Ademas, dicho Response contiene una entidad/objeto 
    *         en formato JSON con los siguiente atributos: codigo, estado, token, rol, user_id
    *         y mensaje en caso de ocurrir alguna de las excepciones
    */
    @POST
    @Path( "/ldap" )
    public Response loginLdap(UsuarioLdapDto usuarioLdapDto)
    {
        String token="";
        JsonObject data;

        try
        {
            DirectorioActivo ldap = new DirectorioActivo();
            DaoUsuario daoUsuario = new DaoUsuario();
            

            
            if ( usuarioLdapDto.getCorreoelectronico() != null ){
                usuarioLdapDto.setCn(ldap.getUserFromMail(usuarioLdapDto));
            }
            long resultado=ldap.userAuthentication( usuarioLdapDto );



            if(resultado==1){
                Usuario usuario = daoUsuario.find(Long.parseLong(ldap.getEntryUid(usuarioLdapDto)), Usuario.class);
                if (usuario.get_estado().equals("inactivo")){
                    data= Json.createObjectBuilder()
                            .add("estado","Usuario Inactivo")
                            .add("codigo",401).build();
                    return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
                }


                Jwt jwt=new Jwt();
                token= jwt.generarToken(usuarioLdapDto);
                data= Json.createObjectBuilder()
                                     .add("estado","success")
                                     .add("codigo",200)
                                     .add("token",token)
                                     .add("rol", ldap.getEntryRole(usuarioLdapDto))
                                     .add("user_id",ldap.getEntryUid(usuarioLdapDto)).build();

                System.out.println(data);
                return Response.status(Response.Status.OK).entity(data).build();
            }else{
                data= Json.createObjectBuilder()
                        .add("estado","Las credenciales no son correctas. Intente de nuevo.")
                        .add("codigo",401).build();
                return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
            }
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
            System.out.println("Excepcion");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
