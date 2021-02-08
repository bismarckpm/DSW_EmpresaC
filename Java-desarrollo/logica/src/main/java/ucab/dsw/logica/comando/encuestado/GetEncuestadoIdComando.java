package ucab.dsw.logica.comando.encuestado;


import ucab.dsw.accesodatos.DaoEncuestado;
import ucab.dsw.dtos.EncuestadoDto;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.Encuestado;
import ucab.dsw.excepciones.EmpresaException;
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
    public void execute() throws EmpresaException{
        try{
            DaoEncuestado dao = new DaoEncuestado();
            Encuestado encuestado= dao.getEncuestadoId(_id);
            this.encuestadoDto= EncuestadoMapper.mapEntityToDto(encuestado);

        } catch (PruebaExcepcion ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-ENC06-ZERO-ID", ex.getMessage(), "Intento asignar el valor de 0 a un ID");
        }
    }

    @Override
    public JsonObject getResult() throws EmpresaException{
        try{
            JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","id del cliente es: "+ _id)
                    .add("encuestado_id",encuestadoDto.getId()).build();

            return data;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new EmpresaException("C-ENC06-G-NULL", "Ha ocurrido un error en los JsonObject - Cause: Null key/pair", "Error. Intente mas tarde.");
        }
    }
}