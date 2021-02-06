package ucab.dsw.logica.comando.pais;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class AllPaisComando extends BaseComando {

    public JsonArrayBuilder paises= Json.createArrayBuilder();

    @Override
    public void execute() {
        DaoPais dao= Fabrica.crear(DaoPais.class);
        List<Pais> resultado= dao.findAll(Pais.class);

        for(Pais obj: resultado){

            JsonObject pais = Json.createObjectBuilder().add("id",obj.get_id())
                    .add("nombre",obj.get_nombre()).build();

            paises.add(pais);

        }

    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Todos los paises";
        responseDto.estado="success";
        responseDto.objeto=this.paises;


        JsonObject data= Json.createObjectBuilder().add("mensaje","Todos los paises")
                .add("estado","success")
                .add("paises",paises).build();

        return data;
    }
}
