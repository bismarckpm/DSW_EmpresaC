package ucab.dsw.servicio;
import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.dtos.UsuarioDto;
import ucab.dsw.entidades.Usuario;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.*;
import javax.ws.rs.core.*;


@Path( "/prueba" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class PruebaServicio extends AplicacionBase
{
    @PUT
    @Path( "/adduser" )
    public UsuarioDto addUser( UsuarioDto usuarioDto )
    {
        UsuarioDto resultado = new UsuarioDto();
        try
        {
            DaoUsuario dao = new DaoUsuario();
            Usuario usuario = new Usuario();
            usuario.set_usuario( usuarioDto.getUsuario() );
            usuario.set_estado( usuarioDto.getEstado() );
            usuario.set_rol( usuarioDto.getRol() );
            Usuario resul = dao.insert( usuario );
            resultado.setId( resul.get_id() );
        }
        catch ( Exception ex )
        {
            String problema = ex.getMessage();
        }
        return  resultado;
    }

    @GET
    @Path( "/seguridad" )
    public Response authenticate(@HeaderParam("authorization") String token) {
        System.out.println("**************************************************************************");
        System.out.println(token);
        System.out.println("**************************************************************************");
        return Response.ok("token="+token).build();
    }

    @GET
    @Path( "/consulta" )
    public String consulta()
    {
        return "Epa";
    }
}
