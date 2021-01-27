package ucab.dsw.logica.comando.encuestado;


import ucab.dsw.accesodatos.DaoEncuestado;
import ucab.dsw.dtos.EncuestadoDto;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.Encuestado;
import ucab.dsw.excepciones.PruebaExcepcion;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;
import ucab.dsw.mappers.EncuestadoMapper;

import javax.json.Json;
import javax.json.JsonObject;

public class GetEncuestadoIdComando extends BaseComando {
    public EncuestadoDto encuestadoDto;
    public long _id;

    public GetEncuestadoIdComando(long _id){
        this._id=_id;
    }

    @Override
    public void execute() {
        try{
            DaoEncuestado dao = new DaoEncuestado();
            Encuestado encuestado= dao.getEncuestadoId(_id);
            this.encuestadoDto= EncuestadoMapper.mapEntityToDto(encuestado);

        }catch (PruebaExcepcion pruebaExcepcion) {
            pruebaExcepcion.printStackTrace();
        }
    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto = Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="id del encuestado es: "+ _id;
        responseDto.estado="success";
        responseDto.objeto=this.encuestadoDto.getId();

        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","id del cliente es: "+ _id)
                .add("encuestado",encuestadoDto.getId()).build();

        return data;
    }
}
