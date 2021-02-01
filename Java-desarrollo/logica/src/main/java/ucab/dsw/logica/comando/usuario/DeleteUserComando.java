package ucab.dsw.logica.comando.usuario;

import ucab.dsw.accesodatos.DaoCliente;
import ucab.dsw.accesodatos.DaoEncuestado;
import ucab.dsw.accesodatos.DaoUsuario;
import ucab.dsw.dtos.UsuarioDto;
import ucab.dsw.entidades.Cliente;
import ucab.dsw.entidades.Encuestado;
import ucab.dsw.entidades.Usuario;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

public class DeleteUserComando extends BaseComando {
    public JsonObject data;
    public long _id;
    public UsuarioDto usuarioDto;

    public DeleteUserComando(long _id, UsuarioDto usuarioDto) {
        this._id = _id;
        this.usuarioDto = usuarioDto;
    }

    @Override
    public void execute() {
        //"DELETE" USUARIO
        DaoUsuario dao = Fabrica.crear(DaoUsuario.class);
        Usuario user = dao.find(_id, Usuario.class);
        user.set_estado("inactivo");

        Usuario resul = dao.update(user);

        //"DELETE" CLIENTE
        if (user.get_rol().equals("cliente")) {
            DaoCliente daoC = Fabrica.crear(DaoCliente.class);
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
            DaoEncuestado daoE = Fabrica.crear(DaoEncuestado.class);
            List<Encuestado> encuestados = daoE.findAll(Encuestado.class);

            for (Encuestado encuestado : encuestados) {
                if (_id == encuestado.get_usuario_encuestado().get_id()) {
                    encuestado.set_estado( user.get_estado() );
                    Encuestado resulE = daoE.update(encuestado);
                }
            }
        }
    }

    @Override
    public JsonObject getResult() {
        this.data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Usuario inactivo")
                .add("estado_user","inactivo").build();

        return this.data;
    }
}
