package ucab.dsw.logica.comando.tipo;


import ucab.dsw.accesodatos.DaoTipo;
import ucab.dsw.dtos.*;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.comando.BaseComando;

import javax.json.Json;
import javax.json.JsonObject;

public class GetTipoComando extends BaseComando {
    public long _id;
    public TipoDto tipoDto;
    public JsonObject tipoJson;

    public GetTipoComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() {
        DaoTipo dao = new DaoTipo();
        Tipo tipo = dao.find(_id,Tipo.class);

        tipoJson= Json.createObjectBuilder().add("id",tipo.get_id() )
                .add("nombre",tipo.get_nombre()).build();

        if (tipo.get_estado() != null){
            System.out.println("estado: " + tipo.get_estado());
        }


    }

    @Override
    public JsonObject getResult() {
        JsonObject data= Json.createObjectBuilder()
                .add("estado","success")
                .add("mensaje","Tipo consultada")
                .add("categoria",tipoJson).build();
        return data;
    }
}
