package ucab.dsw.logica.comando.usuario;

import ucab.dsw.accesodatos.DaoCliente;
import ucab.dsw.entidades.Cliente;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.List;

public class FindClienteComando extends BaseComando {
    public JsonObject data;
    public JsonObject cliente_in;
    public long _id;

    public FindClienteComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() {
        DaoCliente daoCliente = Fabrica.crear(DaoCliente.class);
        List<Cliente> clientes = daoCliente.findAll(Cliente.class);
        this.cliente_in = null;

        for (Cliente cliente : clientes){
            if( this._id == cliente.get_usuario_cliente().get_id() ){
                this.cliente_in = Json.createObjectBuilder()
                        .add("rif", cliente.get_rif() )
                        .add("razon_social", cliente.get_razon_social())
                        .add("nombre_empresa",cliente.get_nombre_empresa()).build();
            }
        }
    }

    @Override
    public JsonObject getResult() {
        this.data= Json.createObjectBuilder()
                .add("estado","success")
                .add("cliente",this.cliente_in).build();

        return this.data;
    }
}
