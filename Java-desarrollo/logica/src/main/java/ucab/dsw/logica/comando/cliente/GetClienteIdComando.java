package ucab.dsw.logica.comando.cliente;

import ucab.dsw.accesodatos.DaoCliente;
import ucab.dsw.dtos.ClienteDto;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.Cliente;
import ucab.dsw.excepciones.EmpresaException;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.ClienteMapper;

import javax.json.Json;
import javax.json.JsonObject;

public class GetClienteIdComando extends BaseComando {
    public ClienteDto clienteDto;
    public long _id;

    public GetClienteIdComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() throws EmpresaException{
        try{
            DaoCliente dao = new DaoCliente();
            Cliente cliente= dao.getClienteId(_id);
            this.clienteDto= ClienteMapper.mapEntityToDto(cliente);

        } catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-CLI02-ZERO-ID", ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","id del cliente es: "+ _id)
                    .add("cliente_id",clienteDto.getId()).build();

            return data;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-CLI02-G-NULL", "Ha ocurrido un error en los JsonObject - Cause: Null key/pair", "Error. Intente mas tarde.");
        }
    }
}