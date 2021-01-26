package ucab.dsw.logica.comando.estado;

import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.entidades.*;
import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class AllEstadosComando extends BaseComando {

    public JsonArrayBuilder estados= Json.createArrayBuilder();

    @Override
    public void execute() {

        DaoEstado dao= Fabrica.crear(DaoEstado.class);
        List<Estado> resultado= dao.findAll(Estado.class);

        for(Estado obj: resultado){

            JsonObject estado = Json.createObjectBuilder().add("id",obj.get_id())
                    .add("nombre",obj.get_nombre())
                    .add("pais_id",obj.get_pais().get_id()).build();

            estados.add(estado);

        }

    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Todos los estados";
        responseDto.estado="success";
        responseDto.objeto=this.estados;


        JsonObject data= Json.createObjectBuilder().add("mensaje","Todas las categorias")
                .add("estado","success")
                .add("estados",estados).build();

        return data;
    }
}
