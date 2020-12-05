package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoCliente;
import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.dtos.ClienteDto;
import ucab.dsw.entidades.Cliente;

import ucab.dsw.entidades.Usuario;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
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
        try
        {
            DaoCliente daoCliente=new DaoCliente();

            Usuario usuario= new Usuario();
            usuario.set_usuario(clienteDto.getUsuarioDto().getUsuario());
            usuario.set_estado("activo");
            usuario.set_rol("cliente");

            Cliente cliente=new Cliente();
            cliente.set_rif(clienteDto.getRif());
            cliente.set_razon_social(clienteDto.getRazon_social());
            cliente.set_nombre_empresa(clienteDto.getNombre_empresa());
            cliente.set_usuario_cliente(usuario);

            Cliente resul= daoCliente.insert(cliente);

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();


        }
        catch ( Exception ex )
        {
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
