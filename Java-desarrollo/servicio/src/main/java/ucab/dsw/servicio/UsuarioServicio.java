package ucab.dsw.servicio;

import ucab.dsw.accesodatos.DaoCliente;
import ucab.dsw.accesodatos.DaoEncuesta;
import ucab.dsw.accesodatos.DaoEncuestado;
import ucab.dsw.dtos.ClienteDto;
import ucab.dsw.dtos.EncuestadoDto;
import ucab.dsw.entidades.*;

import javax.json.Json;
import javax.json.JsonObject;
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

            /*Falta el registro en el ldap*/

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

    @POST
    @Path( "/add/encuestado" )
    public Response AddEncuestado(EncuestadoDto encuestadoDto)
    {
        JsonObject data;
        try
        {
            DaoEncuestado daoEncuestado=new DaoEncuestado();

            Usuario usuario= new Usuario();
            usuario.set_usuario(encuestadoDto.getUsuarioDto().getUsuario());
            usuario.set_estado("activo");
            usuario.set_rol("encuestado");

            Encuestado encuestado=new Encuestado();
            encuestado.set_correo(encuestadoDto.getCorreo());
            encuestado.set_doc_id(encuestadoDto.getDoc_id());
            encuestado.set_nombre(encuestadoDto.getNombre());
            encuestado.set_doc_id(encuestadoDto.getDoc_id());
            encuestado.set_nombre(encuestadoDto.getNombre());
            encuestado.set_apellido(encuestadoDto.getApellido());
            DateFormat formato= new SimpleDateFormat("yyyy-MM-dd");
            encuestado.set_fecha_nacimiento(formato.parse(encuestadoDto.getFecha_nacimiento()));
            encuestado.set_cant_personas_vivienda(encuestadoDto.getCant_personas_vivienda());
            encuestado.set_genero(encuestadoDto.getGenero());

            Parroquia parroquia=new Parroquia(encuestadoDto.getParroquiaDto().getId());
            Nivel_Academico nivel_academico=new Nivel_Academico(encuestadoDto.getNivel_AcademicoDto().getId());

            /*Faltan los hijos*/

            /*Falta el registro en el ldap*/

            encuestado.set_usuario_encuestado(usuario);
            encuestado.set_Parroquia_encuestado(parroquia);
            encuestado.set_nivel_academico_encuestado(nivel_academico);


            Encuestado resul= daoEncuestado.insert(encuestado);

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

    /*Falta agregar analista y administrador*/
}
