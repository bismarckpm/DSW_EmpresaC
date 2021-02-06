package ucab.dsw.logica.comando.parroquia;

import ucab.dsw.accesodatos.*;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.*;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class AllParroquiaComando extends BaseComando {
    public JsonArrayBuilder parroquias= Json.createArrayBuilder();

    @Override
    public void execute() {

        DaoParroquia dao= Fabrica.crear(DaoParroquia.class);
        List<Parroquia> resultado= dao.findAll(Parroquia.class);

        for(Parroquia obj: resultado){

            JsonObject parroquia = Json.createObjectBuilder().add("id",obj.get_id())
                    .add("nombre",obj.get_nombre())
                    .add("ciudad_id",obj.get_ciudad().get_id())
                    .add("estado_id",obj.get_ciudad().get_estado().get_id())
                    .add("pais_id",obj.get_ciudad().get_estado().get_pais().get_id()).build();

            parroquias.add(parroquia);

        }



    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Todas las parroquias";
        responseDto.estado="success";
        responseDto.objeto=this.parroquias;


        JsonObject data= Json.createObjectBuilder().add("mensaje","Todas las parroquias")
                .add("estado","success")
                .add("parroquias",parroquias).build();

        return data;
    }
}
