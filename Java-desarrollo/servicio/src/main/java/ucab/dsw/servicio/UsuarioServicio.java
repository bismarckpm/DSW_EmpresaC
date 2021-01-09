package ucab.dsw.servicio;

import org.eclipse.persistence.exceptions.DatabaseException;

import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para la gestion del usuarios desde el panel del aministrador
 * @author Jesus Requena
 */
@Path( "/usuario" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class UsuarioServicio extends AplicacionBase {

    /**
     * Metodo para añadrir un nuevo cliente
     * despues de verificar que no existen coincidencias con el registro en la base de datos
     * @author Jesus Requena
     * @param clienteDto precargado con el conjunto de datos a ingresar en el registro
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return Response que incluye un estado de respuesta http OK  para indicar si exectivamente
     *         se pudo completar la solicitud.
     *         La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
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

            usuario.set_usuario(clienteDto.getUsuarioLdapDto().getCn());
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
            cliente.set_estado("activo");

            Cliente resul= daoCliente.insert(cliente);

            DirectorioActivo ldap = new DirectorioActivo();
            clienteDto.getUsuarioLdapDto().setUid(String.format("%d",ID_USER));
            clienteDto.getUsuarioLdapDto().setTipo_usuario("cliente");

            ldap.addEntryToLdap( clienteDto.getUsuarioLdapDto() );

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

    /**
     * Metodo para añadrir un nuevo encuestado
     * despues de verificar que no existen coincidencias con el registro en la base de datos
     * @author Jesus Requena
     * @param nuevoEncuestadoDto precargado con el conjunto de datos a ingresar en el registro
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return Response que incluye un estado de respuesta http (OK, UNAUTHHORIZED o BAD_REQUEST) para
     *         indicar si exectivamente se pudo completar la solicitud, se encontro un usuario o correo electronico
     *         ya registrado u ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
    @POST
    @Path( "/add/encuestado" )
    public Response AddEncuestado( NuevoEncuestadoDto nuevoEncuestadoDto ){
        JsonObject data;
        long ID_USER,ID_ENC;
        DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            DirectorioActivo ldap = new DirectorioActivo();
            //obtenemos el usuario SI existe previamente
            String email = ldap.emailExist( nuevoEncuestadoDto.getUsuarioLdap() );
            String user = ldap.userExist( nuevoEncuestadoDto.getUsuarioLdap() );
            if( !email.equals( nuevoEncuestadoDto.getUsuarioLdap().getCorreoelectronico()) && !user.equals( nuevoEncuestadoDto.getUsuarioLdap().getCn() )){
                //INSERT USUARIO
                UsuarioDto resultadoU = new UsuarioDto();
                DaoUsuario daoIU = new DaoUsuario();
                Usuario usuario = new Usuario();

                usuario.set_usuario( nuevoEncuestadoDto.getUsuarioLdap().getCn() );
                usuario.set_estado( "activo" );
                usuario.set_rol( "encuestado" );

                Usuario resulU = daoIU.insert( usuario );
                resultadoU.setId( resulU.get_id() );
                ID_USER = resulU.get_id();

                //INSERT LDAP
                nuevoEncuestadoDto.getUsuarioLdap().setUid( String.format("%d",ID_USER));
                ldap.addEntryToLdap( nuevoEncuestadoDto.getUsuarioLdap() );

                //INSERT ENCUESTADO
                EncuestadoDto resultadoE = new EncuestadoDto();
                DaoEncuestado daoIE = new DaoEncuestado();
                Encuestado encuestado = new Encuestado();

                DaoNivelAcademico daoNivelAcademicoE = new DaoNivelAcademico();
                NivelAcademico nivel_academico = daoNivelAcademicoE.find( nuevoEncuestadoDto.getEncuestado().getNivel_AcademicoDto().getId(), NivelAcademico.class  );

                DaoUsuario daoUsuarioE = new DaoUsuario();
                Usuario usuarioE = daoUsuarioE.find( ID_USER, Usuario.class);

                DaoParroquia daoParroquia = new DaoParroquia();
                Parroquia parroquiaE = daoParroquia.find( nuevoEncuestadoDto.getEncuestado().getParroquiaDto().getId() , Parroquia.class);

                encuestado.set_doc_id( nuevoEncuestadoDto.getEncuestado().getDoc_id() );
                encuestado.set_nombre( nuevoEncuestadoDto.getEncuestado().getNombre() );
                encuestado.set_apellido( nuevoEncuestadoDto.getEncuestado().getApellido() );
                encuestado.set_correo( nuevoEncuestadoDto.getEncuestado().getCorreo() );
                encuestado.set_fecha_nacimiento( formato.parse(nuevoEncuestadoDto.getEncuestado().getFecha_nacimiento()) );
                encuestado.set_cant_personas_vivienda( nuevoEncuestadoDto.getEncuestado().getCant_personas_vivienda() );
                encuestado.set_genero( nuevoEncuestadoDto.getEncuestado().getGenero() );
                encuestado.set_estado( "activo" );
                encuestado.set_nivel_academico_encuestado( nivel_academico );
                encuestado.set_usuario_encuestado( usuarioE );
                encuestado.set_Parroquia_encuestado( parroquiaE );

                Encuestado resulE = daoIE.insert( encuestado );
                resultadoE.setId( resulE.get_id() );
                ID_ENC = resulE.get_id();

                //INSERT TELEFONOS
                for(TelefonoDto t : nuevoEncuestadoDto.getTelefono() ){
                    TelefonoDto resultadoT = new TelefonoDto();
                    DaoTelefono daoT = new DaoTelefono();
                    Telefono telefono = new Telefono();

                    DaoEncuestado daoEncuestado = new DaoEncuestado();
                    Encuestado encuestadoT = daoEncuestado.find( ID_ENC , Encuestado.class);

                    telefono.set_numero( t.getNumero() );
                    telefono.set_codigo_area( t.getCodigo_area() );
                    telefono.set_estado( "activo" );
                    telefono.set_encuestado_telefono( encuestadoT );

                    Telefono resulT = daoT.insert( telefono );
                    resultadoT.setId( resulT.get_id() );
                }
                //INSERT HIJOS
                for(HijoDto h : nuevoEncuestadoDto.getHijo()){
                    HijoDto resultadoH = new HijoDto();
                    DaoHijo daoH = new DaoHijo();
                    Hijo hijo = new Hijo();

                    DaoEncuestado daoEncuestadoH = new DaoEncuestado();
                    Encuestado encuestadoH = daoEncuestadoH.find( ID_ENC , Encuestado.class);

                    hijo.set_nombre( h.getNombre() );
                    hijo.set_apellido( h.getApellido() );
                    hijo.set_fecha_nacimiento( formato.parse(h.getFecha_nacimiento()) );
                    hijo.set_genero( h.getGenero() );
                    hijo.set_estado( "activo" );
                    hijo.set_encuestado_hijo( encuestadoH );

                    Hijo resulH = daoH.insert( hijo );
                    resultadoH.setId( resulH.get_id() );
                }

                //INSERT METODOS_CONEXION_ENCUESTADO
                for(MetodoConexionDto m : nuevoEncuestadoDto.getMetodo_conexion()){

                    MetodoConexionEncuestadoDto resultadoM = new MetodoConexionEncuestadoDto();
                    DaoMetodoConexionEncuestado daoM = new DaoMetodoConexionEncuestado();
                    MetodoConexionEncuestado metodo_conexion_encuestado = new MetodoConexionEncuestado();

                    DaoEncuestado daoEncuestadoM = new DaoEncuestado();
                    Encuestado encuestadoM = daoEncuestadoM.find( ID_ENC , Encuestado.class);

                    DaoMetodoConexion daoMetodo_conexion = new DaoMetodoConexion();
                    MetodoConexion metodo_conexionM = daoMetodo_conexion.find( m.getId() , MetodoConexion.class );

                    metodo_conexion_encuestado.set_encuestado_metodo_conexion( encuestadoM );
                    metodo_conexion_encuestado.set_metodo_conexion( metodo_conexionM );

                    MetodoConexionEncuestado resulM = daoM.insert( metodo_conexion_encuestado );
                    resultadoM.setId( resulM.get_id() );
                }

                //INSERT OCUPACION_ENCUESTADO
                for (OcupacionDto o : nuevoEncuestadoDto.getOcupacion()){
                    OcupacionEncuestadoDto resultadoO = new OcupacionEncuestadoDto();
                    DaoOcupacionEncuestado daoO = new DaoOcupacionEncuestado();
                    OcupacionEncuestado ocupacion_encuestado = new OcupacionEncuestado();

                    DaoOcupacion daoOcupacion = new DaoOcupacion();
                    Ocupacion ocupacionO = daoOcupacion.find( o.getId() ,Ocupacion.class);

                    DaoEncuestado daoEncuestadoO = new DaoEncuestado();
                    Encuestado encuestadoO = daoEncuestadoO.find( ID_ENC , Encuestado.class);

                    ocupacion_encuestado.set_ocupacion( ocupacionO );
                    ocupacion_encuestado.set_encuestado_ocupacion( encuestadoO );

                    OcupacionEncuestado resulO = daoO.insert( ocupacion_encuestado );
                    resultadoO.setId( resulO.get_id() );
                }

                data= Json.createObjectBuilder()
                        .add("estado","success")
                        .add("codigo",200)
                        .build();

                return Response.status(Response.Status.OK).entity(data).build();
            }else{
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        }catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","El usuario ya se encuestra registrado")
                    .add("codigo",500).build();

            System.out.println(data);

            return Response.status(Response.Status.OK).entity(data).build();
        }catch ( Exception ex ) {
            System.out.println("Excepcion");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    /**
     * Metodo para añadrir un nuevo administrador
     * despues de verificar que no existen coincidencias con el registro en la base de datos
     * @author Jesus Requena
     * @param nuevoUsuarioDto precargado con el conjunto de datos a ingresar en el registro
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return Response que incluye un estado de respuesta http (OK o BAD_REQUEST) para indicar si exectivamente
     *         se pudo completar la solicitud o si ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
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
            usuario.set_rol("admin");

            Usuario resulU = daoUsuario.insert(usuario);
            ID_USER = resulU.get_id();

            DirectorioActivo ldap = new DirectorioActivo();
            nuevoUsuarioDto.getUsuarioLdapDto().setUid(String.format("%d",ID_USER));
            nuevoUsuarioDto.getUsuarioLdapDto().setTipo_usuario("admin");
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

    /**
     * Metodo para añadrir un nuevo analista
     * despues de verificar que no existen coincidencias con el registro en la base de datos
     * @author Jesus Requena
     * @param nuevoUsuarioDto precargado con el conjunto de datos a ingresar en el registro
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return Response que incluye un estado de respuesta http (OK o BAD_REQUEST) para indicar si exectivamente
     *         se pudo completar la solicitud o si ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
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
            nuevoUsuarioDto.getUsuarioLdapDto().setTipo_usuario("analista");
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

    /**
     * Metodo para modificar la contraseña de un usuario existente despues de verificar la contraseña actual
     * @author Jesus Requena
     * @param cambiarClaveDto el cual incluye el usuario a modificar, la conrtraseña actual y la nueva
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return Response que incluye un estado de respuesta http (OK o BAD_REQUEST) para indicar si exectivamente
     *         se pudo completar la solicitud o si ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado y codigo)
     */
    @POST
    @Path( "/change-password" )
    public Response ChangePassword(CambiarClaveDto cambiarClaveDto) {
        JsonObject data;

        try {
            DirectorioActivo ldap = new DirectorioActivo();
            UsuarioLdapDto user = new UsuarioLdapDto();
            user.setUid(cambiarClaveDto.getUser_id());
            user.setContrasena(cambiarClaveDto.getContrasena_actual());
            user.setCn( ldap.getUserFromUid(user) );

            if(ldap.validateUser(user)){

                ldap.reSetPass( user , cambiarClaveDto.getContrasena_nueva());

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


    @DELETE
    @Path( "/activar/{id}" )
    public Response activarUsuario(@PathParam("id") long  _id)
    {
        JsonObject data;
        UsuarioDto resultado = new UsuarioDto();
        try
        {
            DaoUsuario dao = new DaoUsuario();
            Usuario usuario = dao.find(_id,Usuario.class);
            usuario.set_estado("activo");

            Usuario resul = dao.update(usuario);
            resultado.setId( resul.get_id() );

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200).build();
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


    /**
     * Metodo para editar los datos de un usuario administrador
     * @author Jesus Requena
     * @param usuarioLdapDto precargado con el conjunto de datos a modificar en el registro
     * @param _id con el id del usuario a modificar
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return Response que incluye un estado de respuesta http OK  para indicar si exectivamente
     *      se pudo completar la solicitud.
     *      La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
    @PUT
    @Path( "/edit/{id}" )
    public Response changeAdmin(@PathParam("id")long  _id, UsuarioLdapDto usuarioLdapDto) {
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

    /**
     * Metodo para editar los datos de un usuario tipo cliente
     * @author Jesus Requena
     * @param clienteDto precargado con el conjunto de datos a modificar en el registro
     * @param _id con el id del usuario a modificar
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return Response que incluye un estado de respuesta http OK  para indicar si exectivamente
     *      se pudo completar la solicitud.
     *      La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
    @PUT
    @Path( "/cliente/edit/{id}" )
    public Response changeCliente(@PathParam("id")long  _id, ClienteDto clienteDto)
    {
        DirectorioActivo ldap = new DirectorioActivo();
        UsuarioDto resultado = new UsuarioDto();
        String user_name_original,email_original;
        JsonObject data;
        Boolean flag = true;
        Long ID_USER;
        try
        {
            UsuarioLdapDto original = new UsuarioLdapDto();
            original.setUid( String.format("%d",_id) );
            user_name_original = ldap.getUserFromUid( original );
            email_original = ldap.getMailFromUid( original );
            if(!user_name_original.equals(clienteDto.getUsuarioLdapDto().getCn())){
                if( clienteDto.getUsuarioLdapDto().getCn().equals( ldap.userExist( clienteDto.getUsuarioLdapDto() ) ) ){
                    flag = false;
                }
            }
            if(!email_original.equals(clienteDto.getUsuarioLdapDto().getCorreoelectronico())){
                if( clienteDto.getUsuarioLdapDto().getCorreoelectronico().equals( ldap.emailExist( clienteDto.getUsuarioLdapDto() ) ) ){
                    flag = false;
                }
            }

            if(flag){
                DaoUsuario daoUsuario = new DaoUsuario();
                Usuario usuario = daoUsuario.find( _id ,Usuario.class);

                usuario.set_usuario(clienteDto.getUsuarioDto().getUsuario());

                Usuario resulU = daoUsuario.insert(usuario);
                ID_USER = resulU.get_id();

                DaoCliente daoCliente = new DaoCliente();
                List<Cliente> clientes = daoCliente.findAll(Cliente.class);
                for (Cliente cliente : clientes ) {
                    if(cliente.get_usuario_cliente().get_id() == ID_USER){
                        cliente.set_rif(clienteDto.getRif());
                        cliente.set_razon_social(clienteDto.getRazon_social());
                        cliente.set_nombre_empresa(clienteDto.getNombre_empresa());
                        cliente.set_usuario_cliente(usuario);
                        Cliente resul = daoCliente.update(cliente);
                    }
                }
                //UPDATE USUARIOLDAP
                ldap.updateUser(clienteDto.getUsuarioLdapDto(), user_name_original);

                data = Json.createObjectBuilder()
                        .add("estado", "success")
                        .add("codigo", 200).build();
            }else{
                data = Json.createObjectBuilder()
                        .add("estado", "error")
                        .add("codigo", 400).build();
            }
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

    /**
     * Metodo para obtener a todos los usuarios registrados en el Directorio Activo
     * @author Jesus Requena
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return Response que incluye un estado de respuesta http (OK o BAD_REQUEST) para indicar si exectivamente
     *         se pudo completar la solicitud o si ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado codigo, mensaje
     *         y el arreglo con todos los usuarios y sus respectivos datos)
     */
    @GET
    @Path( "/all" )
    public Response findAllUsers(){
        JsonObject data;
        try {
            DirectorioActivo ldap = new DirectorioActivo();
            ArrayList<UsuarioLdapDto> usuarios = ldap.getAllUsers();
            JsonArrayBuilder usuariosArrayJson = Json.createArrayBuilder();

            DaoUsuario dao = new DaoUsuario();
            List<Usuario> resultado = dao.findAll( Usuario.class );

            for(UsuarioLdapDto u: usuarios) {

                JsonObject user_in = null;
                for (Usuario usuar : resultado) {
                    if ( u.getUid().equals(String.format("%d",usuar.get_id()) ) ) {
                        user_in = Json.createObjectBuilder()
                                .add( "id", usuar.get_id())
                                .add("usuario",usuar.get_usuario())
                                .add("rol",usuar.get_rol())
                                .add("estado",usuar.get_estado())
                                .build();
                    }
                }

                JsonObject usuario = Json.createObjectBuilder()
                        .add("cn",u.getCn())
                        .add("sn",u.getSn())
                        .add("tipo_usuario",u.getTipo_usuario())
                        .add("nombre", u.getNombre())
                        .add("correoelectronico",u.getCorreoelectronico())
                        .add("uid",u.getUid())
                        .add("usuario", user_in)
                        .add("estado",user_in.getString("estado"))
                        .build();

                usuariosArrayJson.add(usuario);
            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("usuarios",usuariosArrayJson).build();

        } catch ( Exception ex ) {
            String problema = ex.getMessage();
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }


    /**
     * Metodo para obtener a un cliente a partir de su id y retornar sus datos
     * @author Jesus Requena
     * @param _id con el id del cliente solicitado
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return Response que incluye un estado de respuesta http (OK o BAD_REQUEST) para indicar si exectivamente
     *         se pudo completar la solicitud o si ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json que incluye (estado, codigo
     *         y los datos del cliente)
     */
    @GET
    @Path( "/get-cliente/{id}" )
    public Response findCliente(@PathParam("id")long  _id){
        JsonObject data;
        try {

            DaoCliente daoCliente = new DaoCliente();
            List<Cliente> clientes = daoCliente.findAll(Cliente.class);
            JsonObject cliente_in = null;
            for (Cliente cliente : clientes){
                if(_id == cliente.get_usuario_cliente().get_id() ){
                    cliente_in = Json.createObjectBuilder()
                            .add("rif", cliente.get_rif() )
                            .add("razon_social", cliente.get_razon_social())
                            .add("nombre_empresa",cliente.get_nombre_empresa())
                            .build();
                }
            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("cliente",cliente_in).build();

        } catch ( Exception ex ) {
            String problema = ex.getMessage();
            data= Json.createObjectBuilder()
                    .add("estado","exception!!!")
                    .add("excepcion",ex.getMessage())
                    .add("codigo",500).build();

            return Response.status(Response.Status.BAD_REQUEST).entity(data).build();
        }
        System.out.println(data);
        return Response.status(Response.Status.OK).entity(data).build();
    }

    /**
     * Metodo para inhabilitar a un usuario
     * @author Jesus Requena
     * @param _id con el id del usuario a inhabilitar
     * @param usuarioDto con los datos del usuario a inhabilitar
     * @throws Exception si ocurre cualquier excepcion general no controlada previamente
     * @return Response que incluye un estado de respuesta http OK  para indicar si exectivamente
     *         se pudo completar la solicitud.
     *         La respuesta posee adjunta un json que incluye (estado codigo y mensaje)
     */
    @PUT
    @Path( "/delete/{id}" )
    public Response deleteUser(@PathParam("id")long  _id, UsuarioDto usuarioDto)
    {
        JsonObject data;

        try {
            //"DELETE" USUARIO
            DaoUsuario dao = new DaoUsuario();
            Usuario user = dao.find(_id, Usuario.class);

            user.set_estado(usuarioDto.getEstado());

            Usuario resul = dao.update(user);

            //"DELETE" CLIENTE
            if (user.get_rol().equals("cliente")) {
                DaoCliente daoC = new DaoCliente();
                List<Cliente> clientes = daoC.findAll(Cliente.class);

                for (Cliente cliente : clientes) {
                    if ( _id == cliente.get_usuario_cliente().get_id()) {
                        cliente.set_estado (user.get_estado() );
                        Cliente resulC = daoC.update(cliente);
                    }
                }
            }

            //"DELETE" ENCUESTADO
            if( user.get_rol().equals("encuestado") ){
                DaoEncuestado daoE = new DaoEncuestado();
                List<Encuestado> encuestados = daoE.findAll(Encuestado.class);

                for (Encuestado encuestado : encuestados) {
                    if (_id == encuestado.get_usuario_encuestado().get_id()) {
                        encuestado.set_estado( user.get_estado() );
                        Encuestado resulE = daoE.update(encuestado);
                    }
                }
            }
            data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("codigo", 200).build();


        } catch (PersistenceException | DatabaseException ex){
            data= Json.createObjectBuilder()
                    .add("estado","error")
                    .add("mensaje","Entrada repetida")
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
