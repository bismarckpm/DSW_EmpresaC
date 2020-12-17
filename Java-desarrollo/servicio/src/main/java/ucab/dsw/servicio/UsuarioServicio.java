package ucab.dsw.servicio;

import javafx.scene.control.TextFormatter;
import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.*;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.jwt.Jwt;

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

                DaoNivel_Academico daoNivelAcademicoE = new DaoNivel_Academico();
                Nivel_Academico nivel_academico = daoNivelAcademicoE.find( nuevoEncuestadoDto.getEncuestado().getNivel_AcademicoDto().getId(), Nivel_Academico.class  );

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
                for(Metodo_ConexionDto m : nuevoEncuestadoDto.getMetodo_conexion()){

                    Metodo_Conexion_EncuestadoDto resultadoM = new Metodo_Conexion_EncuestadoDto();
                    DaoMetodo_Conexion_Encuestado daoM = new DaoMetodo_Conexion_Encuestado();
                    Metodo_Conexion_Encuestado metodo_conexion_encuestado = new Metodo_Conexion_Encuestado();

                    DaoEncuestado daoEncuestadoM = new DaoEncuestado();
                    Encuestado encuestadoM = daoEncuestadoM.find( ID_ENC , Encuestado.class);

                    DaoMetodo_Conexion daoMetodo_conexion = new DaoMetodo_Conexion();
                    Metodo_conexion metodo_conexionM = daoMetodo_conexion.find( m.getId() ,Metodo_conexion.class );

                    metodo_conexion_encuestado.set_encuestado_metodo_conexion( encuestadoM );
                    metodo_conexion_encuestado.set_metodo_conexion( metodo_conexionM );

                    Metodo_Conexion_Encuestado resulM = daoM.insert( metodo_conexion_encuestado );
                    resultadoM.setId( resulM.get_id() );
                }

                //INSERT OCUPACION_ENCUESTADO
                for (OcupacionDto o : nuevoEncuestadoDto.getOcupacion()){
                    Ocupacion_EncuestadoDto resultadoO = new Ocupacion_EncuestadoDto();
                    DaoOcupacion_Encuestado daoO = new DaoOcupacion_Encuestado();
                    Ocupacion_Encuestado ocupacion_encuestado = new Ocupacion_Encuestado();

                    DaoOcupacion daoOcupacion = new DaoOcupacion();
                    Ocupacion ocupacionO = daoOcupacion.find( o.getId() ,Ocupacion.class);

                    DaoEncuestado daoEncuestadoO = new DaoEncuestado();
                    Encuestado encuestadoO = daoEncuestadoO.find( ID_ENC , Encuestado.class);

                    ocupacion_encuestado.set_ocupacion( ocupacionO );
                    ocupacion_encuestado.set_encuestado_ocupacion( encuestadoO );

                    Ocupacion_Encuestado resulO = daoO.insert( ocupacion_encuestado );
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
    @Path( "/edit/{id}" )
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
                //PENDIENTE DE ACOMODAR
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
                        .add( "correoelectronico",u.getCorreoelectronico())
                        .add("uid",u.getUid() )
                        .add( "usuario", user_in )
                        .build();

                usuariosArrayJson.add(usuario);
            }

            data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("codigo",200)
                    .add("tipos",usuariosArrayJson).build();

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
