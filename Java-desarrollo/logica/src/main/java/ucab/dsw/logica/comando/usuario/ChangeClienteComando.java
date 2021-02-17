package ucab.dsw.logica.comando.usuario;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.DaoCliente;
import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.ClienteDto;
import ucab.dsw.dtos.UsuarioDto;
import ucab.dsw.dtos.UsuarioLdapDto;
import ucab.dsw.entidades.Cliente;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.UsuarioExistenteExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.ClienteMapper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;
import java.util.List;

public class ChangeClienteComando extends BaseComando {

    public ClienteDto clienteDto;
    public JsonObject data;
    public long _id;

    public ChangeClienteComando(long _id, ClienteDto clienteDto) {
        this.clienteDto = clienteDto;
        this._id = _id;
    }

    @Override
    public void execute() throws EmpresaException {
        try{
            DirectorioActivo ldap = new DirectorioActivo();
            UsuarioLdapDto original = new UsuarioLdapDto();
            String user_name_original, email_original;
            Long ID_USER;

            original.setUid(String.format("%d", this._id));
            user_name_original = ldap.getUserFromUid(original);
            email_original = ldap.getMailFromUid(original);
            if (!user_name_original.equals(this.clienteDto.getUsuarioLdapDto().getCn())) {
                if (this.clienteDto.getUsuarioLdapDto().getCn().equals(ldap.userExist(this.clienteDto.getUsuarioLdapDto()))) {
                    throw new UsuarioExistenteExcepcion("Username existente");
                }
            }
            if (!email_original.equals(this.clienteDto.getUsuarioLdapDto().getCorreoelectronico())) {
                if (this.clienteDto.getUsuarioLdapDto().getCorreoelectronico().equals(ldap.emailExist(this.clienteDto.getUsuarioLdapDto()))) {
                    throw new UsuarioExistenteExcepcion("Correo exitente");
                }
            }

            DaoUsuario daoUsuario = Fabrica.crear(DaoUsuario.class);
            Usuario usuario = daoUsuario.find(this._id, Usuario.class);
            usuario.set_usuario(clienteDto.getUsuarioDto().getUsuario());

            Usuario resulU = daoUsuario.insert(usuario);
            ID_USER = resulU.get_id();

            DaoCliente daoCliente = Fabrica.crear(DaoCliente.class);
            List<Cliente> clientes = daoCliente.findAll(Cliente.class);
            for (Cliente cliente : clientes) {
                if (cliente.get_usuario_cliente().get_id() == ID_USER) {
                    cliente = ClienteMapper.mapDtoToEntityUpdate(this.clienteDto, usuario);
                    Cliente resul = daoCliente.update(cliente);
                }
            }
            //UPDATE USUARIOLDAP
            ldap.updateUser(clienteDto.getUsuarioLdapDto(), user_name_original);
        } catch ( UsuarioExistenteExcepcion ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US07-E-UEE",ex.getMessage(), "usarname o correo existente");
        } catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US07-E-DUP",ex.getMessage(), "El cliente ya se encuentra registrado");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException {
        try{
            this.data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Cliente modificado")
                    .add("clienteUser", this.clienteDto.getUsuarioDto().getUsuario()).build();

            return this.data;
        } catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US07-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
