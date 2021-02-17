package ucab.dsw.logica.comando.registro;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.*;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.UsuarioExistenteExcepcion;
import ucab.dsw.jwt.Jwt;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;

public class RegistroComando extends BaseComando {

    public JsonObject data;
    public long ID_USER,ID_ENC;
    public NuevoEncuestadoDto nuevoEncuestadoDto;
    public Encuestado encuestado;
    public String token;
    public DirectorioActivo ldap;

    public RegistroComando(NuevoEncuestadoDto nuevoEncuestadoDto) {
        this.nuevoEncuestadoDto = nuevoEncuestadoDto;
    }

    @Override
    public void execute() throws EmpresaException{
        try{
            //Obtenemos el usuario SI existe previamente

            DirectorioActivo ldapT = new DirectorioActivo();
            String email = ldapT.emailExist(nuevoEncuestadoDto.getUsuarioLdap());
            String user = ldapT.userExist(nuevoEncuestadoDto.getUsuarioLdap());
            if (email.equals(nuevoEncuestadoDto.getUsuarioLdap().getCorreoelectronico()) || user.equals(nuevoEncuestadoDto.getUsuarioLdap().getCn())) {
                throw new UsuarioExistenteExcepcion("Usuario o correo ya existente");
            }

            //INSERT USUARIO
            DaoUsuario daoU = Fabrica.crear(DaoUsuario.class);
            Usuario usuario = UsuarioMapper.mapUsernameToEntityInsert(nuevoEncuestadoDto.getUsuarioLdap().getCn(), "encuestado");
            Usuario resulU = daoU.insert(usuario);

            //SET User_id
            this.ID_USER = resulU.get_id();
            nuevoEncuestadoDto.getUsuarioLdap().setUid(String.format("%d", this.ID_USER));

            //INSERT LDAP
            this.ldap = new DirectorioActivo();
            this.ldap.addEntryToLdap(nuevoEncuestadoDto.getUsuarioLdap());

            //GET NIVEL ACADEMICO
            DaoNivelAcademico daoNivelAcademico = Fabrica.crear(DaoNivelAcademico.class);
            NivelAcademico nivel_academico = daoNivelAcademico.find(nuevoEncuestadoDto.getEncuestado().getNivel_AcademicoDto().getId(), NivelAcademico.class);

            //GET PARROQUIA
            DaoParroquia daoParroquia = Fabrica.crear(DaoParroquia.class);
            Parroquia parroquia = daoParroquia.find(nuevoEncuestadoDto.getEncuestado().getParroquiaDto().getId(), Parroquia.class);

            //INSERT ENCUESTADO
            Usuario usuarioE = daoU.find(this.ID_USER, Usuario.class);
            DaoEncuestado daoE = Fabrica.crear(DaoEncuestado.class);
            Encuestado encuestadoN = EncuestadoMapper.mapDtoToEntityInsert(nuevoEncuestadoDto, nivel_academico, usuarioE, parroquia);
            Encuestado resulE = daoE.insert(encuestadoN);

            //SET Encuestado_id
            this.ID_ENC = resulE.get_id();
            DaoEncuestado daoEncuestado = Fabrica.crear(DaoEncuestado.class);
            this.encuestado = daoEncuestado.find(this.ID_ENC, Encuestado.class);

            //INSERT TELEFONOS
            for (TelefonoDto t : nuevoEncuestadoDto.getTelefono()) {

                DaoTelefono daoT = Fabrica.crear(DaoTelefono.class);
                Telefono telefono = TelefonoMapper.mapDtoToEntityInsert(t, this.encuestado);
                Telefono resulT = daoT.insert(telefono);

            }
            //INSERT HIJOS
            for (HijoDto h : nuevoEncuestadoDto.getHijo()) {

                DaoHijo daoH = Fabrica.crear(DaoHijo.class);
                Hijo hijo = HijoMapper.mapDtoToEntityInsert(h, this.encuestado);
                Hijo resulH = daoH.insert(hijo);

            }

            //INSERT METODOS_CONEXION_ENCUESTADO
            for (MetodoConexionDto m : nuevoEncuestadoDto.getMetodo_conexion()) {

                DaoMetodoConexion daoMetodoConexion = Fabrica.crear(DaoMetodoConexion.class);
                MetodoConexion metodoConexion = daoMetodoConexion.find(m.getId(), MetodoConexion.class);

                DaoMetodoConexionEncuestado daoM = Fabrica.crear(DaoMetodoConexionEncuestado.class);
                MetodoConexionEncuestado metodoConexionEncuestado = MetodoConexionEncuestadoMapper.mapDtoToEntityInsert(metodoConexion, this.encuestado);

                MetodoConexionEncuestado resulM = daoM.insert(metodoConexionEncuestado);
            }

            //INSERT OCUPACION_ENCUESTADO
            for (OcupacionDto o : nuevoEncuestadoDto.getOcupacion()) {

                DaoOcupacion daoOcupacion = Fabrica.crear(DaoOcupacion.class);
                Ocupacion ocupacion = daoOcupacion.find(o.getId(), Ocupacion.class);

                DaoOcupacionEncuestado daoOcupacionEncuestado = Fabrica.crear(DaoOcupacionEncuestado.class);
                OcupacionEncuestado ocupacionEncuestado = OcupacionEncuestadoMapper.mapDtoToEntityInsert(ocupacion, this.encuestado);
                OcupacionEncuestado resulO = daoOcupacionEncuestado.insert(ocupacionEncuestado);
            }

            Jwt jwt = new Jwt();
            this.token = jwt.generarToken(Long.parseLong(nuevoEncuestadoDto.getUsuarioLdap().getUid()));
        } catch ( UsuarioExistenteExcepcion ex ){
            ex.printStackTrace();
            throw new EmpresaException("C-RG01-E-UEE",ex.getMessage(), "Usuario o correo ya existente");
        } catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-RG01-E-DUP",ex.getMessage(), "El encuestado ya se encuentra registrado");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {
        try{
            this.data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Encuestado creado")
                    .add("token", this.token)
                    .add("rol", this.ldap.getEntryRole(nuevoEncuestadoDto.getUsuarioLdap()))
                    .add("user_id", this.ldap.getEntryUid(nuevoEncuestadoDto.getUsuarioLdap())).build();

            return this.data;
        } catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-RG01-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
