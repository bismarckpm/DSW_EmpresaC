package ucab.dsw.logica.comando.usuario;

import ucab.dsw.accesodatos.DaoCliente;
import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.directorio.DirectorioActivo;
import ucab.dsw.dtos.ClienteDto;
import ucab.dsw.entidades.Cliente;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.ClienteMapper;
import ucab.dsw.mappers.UsuarioMapper;

import javax.json.Json;
import javax.json.JsonObject;

public class AddClienteComando extends BaseComando {

    public JsonObject data;
    public ClienteDto clienteDto;

    public AddClienteComando(ClienteDto clienteDto) {
        this.clienteDto = clienteDto;
        this.clienteDto.getUsuarioLdapDto().setTipo_usuario("cliente");
    }

    @Override
    public void execute() {
        //Insertar Usuario
        DaoUsuario daoUsuario = Fabrica.crear(DaoUsuario.class);
        Usuario usuario = UsuarioMapper.mapUsernameToEntityInsert(clienteDto.getUsuarioLdapDto().getCn(),"cliente");
        Usuario resulU = daoUsuario.insert(usuario);

        //Set User_id
        clienteDto.getUsuarioLdapDto().setUid(String.format("%d", resulU.get_id() ));

        //Insertar Cliente
        DaoCliente daoCliente = Fabrica.crear(DaoCliente.class);
        Cliente cliente = ClienteMapper.mapDtoToEntityInsert(this.clienteDto, usuario);
        Cliente resulC= daoCliente.insert(cliente);

        //Insertar UsuarioLDAP
        DirectorioActivo ldap = new DirectorioActivo();
        ldap.addEntryToLdap( clienteDto.getUsuarioLdapDto() );
    }

    @Override
    public JsonObject getResult() {

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Cliente añadido")
                .add("clienteUser",this.clienteDto.getUsuarioDto().getUsuario()).build();

        return data;
    }
}
