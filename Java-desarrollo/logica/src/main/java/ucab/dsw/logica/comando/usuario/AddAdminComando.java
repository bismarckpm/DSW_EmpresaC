package ucab.dsw.logica.comando.usuario;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.NuevoUsuarioDto;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.UsuarioMapper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;

public class AddAdminComando extends BaseComando {
    public JsonObject data;
    public Long ID_USER;
    public NuevoUsuarioDto nuevoUsuarioDto;

    public AddAdminComando(NuevoUsuarioDto nuevoUsuarioDto) {
        this.nuevoUsuarioDto = nuevoUsuarioDto;
        this.nuevoUsuarioDto.getUsuarioLdapDto().setTipo_usuario("admin");
    }

    @Override
    public void execute() throws EmpresaException{
        try {
            //INSERT USUARIO
            DaoUsuario daoUsuario = Fabrica.crear(DaoUsuario.class);
            Usuario usuario = UsuarioMapper.mapUsernameToEntityInsert(nuevoUsuarioDto.getUsuarioDto().getUsuario(), "admin");
            Usuario resulU = daoUsuario.insert(usuario);
            this.ID_USER = resulU.get_id();

            //SET User_ID
            nuevoUsuarioDto.getUsuarioLdapDto().setUid(String.format("%d", this.ID_USER));

            //INSERT UsuarioLDAP
            DirectorioActivo ldap = new DirectorioActivo();
            ldap.addEntryToLdap(nuevoUsuarioDto.getUsuarioLdapDto());
        } catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US02-E-DUP",ex.getMessage(), "El admin ya se encuentra registrado");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException {
        try {
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Administrador añadido")
                    .add("adminUser", this.nuevoUsuarioDto.getUsuarioDto().getUsuario()).build();

            return data;
        } catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US02-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }

    }
}
