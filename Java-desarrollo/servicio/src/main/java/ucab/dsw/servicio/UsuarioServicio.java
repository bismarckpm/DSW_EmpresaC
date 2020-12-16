package ucab.dsw.servicio;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.DaoCliente;
import ucab.dsw.accesodatos.DaoEncuesta;
import ucab.dsw.accesodatos.DaoEncuestado;
import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.ClienteDto;
import ucab.dsw.dtos.EncuestadoDto;
import ucab.dsw.dtos.NuevoUsuarioDto;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path( "/usuario" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class UsuarioServicio extends AplicacionBase {

    @POST
    @Path( "/add/cliente" )
    public Response AddCliente(ClienteDto clienteDto)
    {
        JsonObject data;
        Long ID_USER;
        try
        {
            DaoUsuario daoUsuario = new DaoUsuario();
            Usuario usuario= new Usuario();

            usuario.set_usuario(clienteDto.getUsuarioDto().getUsuario());
            usuario.set_estado("activo");
            usuario.set_rol("cliente");

            Usuario resulU = daoUsuario.insert(usuario);
            ID_USER = resulU.get_id();

            DaoCliente daoCliente=new DaoCliente();
            Cliente cliente=new Cliente();

            cliente.set_rif(clienteDto.getRif());
            cliente.set_razon_social(clienteDto.getRazon_social());
            cliente.set_nombre_empresa(clienteDto.getNombre_empresa());
            cliente.set_usuario_cliente(usuario);

            Cliente resul= daoCliente.insert(cliente);

            DirectorioActivo ldap = new DirectorioActivo();
            clienteDto.getUsuarioLdapDto().setUid(String.format("%d",ID_USER));
            clienteDto.getUsuarioLdapDto().setTipo_usuario("cliente");
            ldap.addEntryToLdap(clienteDto.getUsuarioLdapDto() );

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();

        }
        catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","El usuario ya se encuestra registrado")
                    .add("codigo",500).build();

            System.out.println(data);

            return Response.status(Response.Status.OK).entity(data).build();
        }
        catch ( Exception ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje",ex.getMessage())
                    .add("codigo",500).build();

            System.out.println(data);
            return Response.status(Response.Status.OK).entity(data).build();

        }

        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();

    }

    @POST
    @Path( "/add/admin" )
    public Response AddAdmin(NuevoUsuarioDto nuevoUsuarioDto) {
        JsonObject data;
        Long ID_USER;
        try {
            DaoUsuario daoUsuario = new DaoUsuario();
            Usuario usuario= new Usuario();

            usuario.set_usuario(nuevoUsuarioDto.getUsuarioDto().getUsuario());
            usuario.set_estado("activo");
            usuario.set_rol("administrador");

            Usuario resulU = daoUsuario.insert(usuario);
            ID_USER = resulU.get_id();

            DirectorioActivo ldap = new DirectorioActivo();
            nuevoUsuarioDto.getUsuarioLdapDto().setUid(String.format("%d",ID_USER));
            nuevoUsuarioDto.getUsuarioLdapDto().setTipo_usuario("cliente");
            ldap.addEntryToLdap(nuevoUsuarioDto.getUsuarioLdapDto() );

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();

        } catch ( Exception ex ) {
            ex.printStackTrace();
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            System.out.println(data);
            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }

    @POST
    @Path( "/add/analista" )
    public Response AddAnalista(NuevoUsuarioDto nuevoUsuarioDto) {
        JsonObject data;
        Long ID_USER;
        try {
            DaoUsuario daoUsuario = new DaoUsuario();
            Usuario usuario= new Usuario();

            usuario.set_usuario(nuevoUsuarioDto.getUsuarioDto().getUsuario());
            usuario.set_estado("activo");
            usuario.set_rol("analista");

            Usuario resulU = daoUsuario.insert(usuario);
            ID_USER = resulU.get_id();

            DirectorioActivo ldap = new DirectorioActivo();
            nuevoUsuarioDto.getUsuarioLdapDto().setUid(String.format("%d",ID_USER));
            nuevoUsuarioDto.getUsuarioLdapDto().setTipo_usuario("cliente");
            ldap.addEntryToLdap(nuevoUsuarioDto.getUsuarioLdapDto() );

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();

        } catch ( Exception ex ) {
            ex.printStackTrace();
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            System.out.println(data);
            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }
}
