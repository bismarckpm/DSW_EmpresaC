package ucab.dsw.logica.comando.usuario;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.UsuarioExistenteExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;

public class ChangeAdminComando extends BaseComando {

    public JsonObject data;
    public UsuarioLdapDto usuarioLdapDto;
    public long _id;

    public ChangeAdminComando(long _id, UsuarioLdapDto usuarioLdapDto) {
        this.usuarioLdapDto = usuarioLdapDto;
        this._id = _id;
    }

    @Override
    public void execute() throws EmpresaException {
        try{
            DirectorioActivo ldap = new DirectorioActivo();
            UsuarioLdapDto original = new UsuarioLdapDto();
            String user_name_original, email_original;

            original.setUid(String.format("%d", this._id));
            user_name_original = ldap.getUserFromUid(original);
            email_original = ldap.getMailFromUid(original);

            //Varifica datos existentes
            if (!user_name_original.equals(this.usuarioLdapDto.getCn())) {
                if (usuarioLdapDto.getCn().equals(ldap.userExist(usuarioLdapDto))) {
                    throw new UsuarioExistenteExcepcion("Username existente");
                }
            }
            if (!email_original.equals(usuarioLdapDto.getCorreoelectronico())) {
                if (usuarioLdapDto.getCorreoelectronico().equals(ldap.emailExist(usuarioLdapDto))) {
                    throw new UsuarioExistenteExcepcion("Correo exitente");
                }
            }
            //UPDATE USUARIO
            DaoUsuario dao = Fabrica.crear(DaoUsuario.class);
            Usuario user = dao.find(_id, Usuario.class);
            user.set_usuario(usuarioLdapDto.getCn());

            //UPDATE USUARIOLDAP
            ldap.updateUser(usuarioLdapDto, user_name_original);
        } catch (UsuarioExistenteExcepcion ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US06-E-UEE",ex.getMessage(), "Username o correo existente");
        } catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US06-E-DUP",ex.getMessage(), "El admin ya se encuentra registrado");
        }

    }

    @Override
    public JsonObject getResult() throws EmpresaException {
        try{
            this.data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Usuario modificado")
                    .add("adminUser", this.usuarioLdapDto.getCn()).build();

            return this.data;
        } catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US06-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
