package ucab.dsw.logica.comando.usuario;

import org.eclipse.persistence.exceptions.DatabaseException;
import ucab.dsw.accesodatos.DaoCliente;
import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.ClienteDto;
import ucab.dsw.entidades.Cliente;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.ClienteMapper;
import ucab.dsw.mappers.UsuarioMapper;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.PersistenceException;

public class AddClienteComando extends BaseComando {

    public ClienteDto clienteDto;

    public AddClienteComando(ClienteDto clienteDto) {
        this.clienteDto = clienteDto;
        this.clienteDto.getUsuarioLdapDto().setTipo_usuario("cliente");
    }

    @Override
    public void execute() throws EmpresaException{
        try{
            //Insertar Usuario
            DaoUsuario daoUsuario = Fabrica.crear(DaoUsuario.class);
            Usuario usuario = UsuarioMapper.mapUsernameToEntityInsert(clienteDto.getUsuarioLdapDto().getCn(), "cliente");
            Usuario resulU = daoUsuario.insert(usuario);

            //Set User_id
            clienteDto.getUsuarioLdapDto().setUid(String.format("%d", resulU.get_id()));

            //Insertar Cliente
            DaoCliente daoCliente = Fabrica.crear(DaoCliente.class);
            Cliente cliente = ClienteMapper.mapDtoToEntityInsert(this.clienteDto, usuario);
            Cliente resulC = daoCliente.insert(cliente);

            //Insertar UsuarioLDAP
            DirectorioActivo ldap = new DirectorioActivo();
            ldap.addEntryToLdap(clienteDto.getUsuarioLdapDto());
        } catch (PersistenceException | DatabaseException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US04-E-DUP",ex.getMessage(), "El cliente ya se encuentra registrado");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            JsonObject data = Json.createObjectBuilder()
                    .add("estado", "success")
                    .add("mensaje", "Cliente añadido")
                    .add("clienteUser", this.clienteDto.getUsuarioLdapDto().getCn()).build();

            return data;
        } catch (NullPointerException ex){
            ex.printStackTrace();
            throw new EmpresaException("C-US04-G-NULL","Ha ocurrido un error en los JsonObject - Cause: Null key/pair","Error. Intente mas tarde.");
        }
    }
}
