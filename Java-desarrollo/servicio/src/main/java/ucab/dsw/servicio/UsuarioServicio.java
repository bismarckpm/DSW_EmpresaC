package ucab.dsw.servicio;

import javafx.scene.control.TextFormatter;
import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.*;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.*;
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

        } catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","El usuario ya se encuestra registrado")
                    .add("codigo",500).build();

            System.out.println(data);

            return Response.status(Response.Status.OK).entity(data).build();
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

        }catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","El usuario ya se encuestra registrado")
                    .add("codigo",500).build();

            System.out.println(data);

            return Response.status(Response.Status.OK).entity(data).build();
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
    @Path( "/change-password" )
    public Response ChangePassword(ChangePasswordDto changePasswordDto) {
        JsonObject data;

        try {
            DirectorioActivo ldap = new DirectorioActivo();
            UsuarioLdapDto user = new UsuarioLdapDto();
            user.setUid(changePasswordDto.getUser_id());
            user.setContrasena(changePasswordDto.getContrasena_actual());
            user.setCn( ldap.getUserFromUid(user) );

            if(ldap.validateUser(user)){

                ldap.reSetPass( user , changePasswordDto.getContrasena_nueva());

                data= Json.createObjectBuilder()
                        .add("estado","success")
                        .add("codigo",200).build();
            }else{
                data= Json.createObjectBuilder()
                        .add("estado","error")
                        .add("codigo",400).build();
            }

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

    @PUT
    @Path( "/change/admin/{id}" )
    public Response changeAdmin(@PathParam("id")long  _id, UsuarioLdapDto usuarioLdapDto)
    {
        DirectorioActivo ldap = new DirectorioActivo();
        UsuarioDto resultado = new UsuarioDto();
        String user_name_original,email_original;
        JsonObject data;
        Boolean flag = true;
        try {
            UsuarioLdapDto original = new UsuarioLdapDto();
            original.setUid( String.format("%d",_id) );
            user_name_original = ldap.getUserFromUid( original );
            email_original = ldap.getMailFromUid( original );
            if(!user_name_original.equals(usuarioLdapDto.getCn())){
                if( usuarioLdapDto.getCn().equals( ldap.userExist( usuarioLdapDto) ) ){
                    flag = false;
                }
            }
            if(!email_original.equals(usuarioLdapDto.getCorreoelectronico())){
                if( usuarioLdapDto.getCorreoelectronico().equals( ldap.emailExist( usuarioLdapDto) ) ){
                    flag = false;
                }
            }


            if(flag) {
                //UPDATE USUARIO
                DaoUsuario dao = new DaoUsuario();
                Usuario user = dao.find(_id, Usuario.class);
                user.set_usuario(usuarioLdapDto.getCn());

                Usuario resul = dao.update(user);
                resultado.setId(resul.get_id());

                //UPDATE USUARIOLDAP
                ldap.updateUser(usuarioLdapDto, user_name_original);
                data = Json.createObjectBuilder()
                        .add("estado", "success")
                        .add("codigo", 200).build();
            }else{
                data = Json.createObjectBuilder()
                        .add("estado", "error")
                        .add("codigo", 400).build();
            }

        } catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","El tipo ya se encuestra registrado")
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

        return Response.status(Response.Status.OK).entity(data).build();
    }



}
