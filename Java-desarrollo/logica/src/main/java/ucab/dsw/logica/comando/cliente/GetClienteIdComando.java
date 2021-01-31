package ucab.dsw.logica.comando.cliente;

import ucab.dsw.accesodatos.DaoCliente;
import ucab.dsw.dtos.ClienteDto;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.Cliente;
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
    public void execute() {
        try{
            DaoCliente dao = new DaoCliente();
            Cliente cliente= dao.getClienteId(_id);
            this.clienteDto= ClienteMapper.mapEntityToDto(cliente);

        }catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }
    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto = Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="id del cliente es: "+ _id;
        responseDto.estado="success";
        responseDto.objeto=this.clienteDto.getId();

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","id del cliente es: "+ _id)
                .add("cliente_id",clienteDto.getId()).build();

        return data;
    }
}