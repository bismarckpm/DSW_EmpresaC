package ucab.dsw.servicio;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.*;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.entidades.*;
import ucab.dsw.jwt.Jwt;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Clase para la gestion del registro de un nuevo encuestado
 * @author Jesus Requena
 */
@Path( "" )
@Produces( MediaType.APPLICATION_JSON )
@Consumes( MediaType.APPLICATION_JSON )
public class RegistroServicio extends AplicacionBase{

    /**
     * Metodo para añadrir un nuevo encuestado e iniciar sesión
     * despues de verificar que no existen coincidencias con el registro en la base de datos
     * @author Jesus Requena
     * @param nuevoEncuestadoDto precargado con el conjunto de datos a ingresar en el registro
     * @return Response que incluye un estado de respuesta http (OK, UNAUTHHORIZED o BAD_REQUEST) para
     *         indicar si exectivamente se pudo completar la solicitud, se encontro un usuario o correo electronico
     *         ya registrado u ocurrió un fallo en la comunicación.
     *         La respuesta posee adjunta un json con los datos de inicio de sesion (id, token y rol) en caso de
     *         respuesta OK
     */
    @POST
    @Path( "/registro" )
    public Response registro( NuevoEncuestadoDto nuevoEncuestadoDto ){
        String token="";
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

                //ENVIAR TOKEN
                Jwt jwt=new Jwt();
                token= jwt.generarToken( ID_ENC );
                data= Json.createObjectBuilder()
                        .add("estado","success")
                        .add("codigo",200)
                        .add("token",token)
                        .add("rol", ldap.getEntryRole( nuevoEncuestadoDto.getUsuarioLdap() ))
                        .add("user_id",ldap.getEntryUid(nuevoEncuestadoDto.getUsuarioLdap())).build();

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
}
