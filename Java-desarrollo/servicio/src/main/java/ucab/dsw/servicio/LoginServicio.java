package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.ContrasenaInvalidaExcepcion;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.logica.comando.login.LoginLdapComando;
import ucab.dsw.logica.comando.login.LogoutComando;
import ucab.dsw.logica.fabrica.Fabrica;

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
        try {
            LoginLdapComando comando = Fabrica.crearComandoConDto(LoginLdapComando.class, usuarioLdapDto);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        } catch ( ContrasenaInvalidaExcepcion ex ){
            ex.printStackTrace();
            System.out.println(ex.getMensaje());
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","unauthorized")
                    .add("mensaje_soporte",ex.getMensaje())
                    .add("mensaje","Contraseña invalida").build();

            return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
        } catch ( Exception ex ) {
            ex.printStackTrace();
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","internal_server_error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
    }

    @DELETE
    @Path( "/logout/{id}" )
    public Response logout(@PathParam("id")long  _id){
        try {
            LogoutComando comando = Fabrica.crearComandoConId(LogoutComando.class, _id);
            comando.execute();

            return Response.status(Response.Status.OK).entity(comando.getResult()).build();
        }catch ( Exception ex ) {
            ex.printStackTrace();
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","internal_server_error")
                    .add("mensaje_soporte",ex.getMessage())
                    .add("mensaje","Ha ocurrido un error con el servidor").build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
    }
}
