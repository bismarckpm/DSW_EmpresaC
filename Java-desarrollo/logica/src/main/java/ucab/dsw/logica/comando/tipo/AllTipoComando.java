package ucab.dsw.logica.comando.tipo;

import ucab.dsw.accesodatos.DaoCategoria;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.accesodatos.DaoTipo;
import ucab.dsw.dtos.ResponseDto;
import ucab.dsw.entidades.Categoria;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.entidades.Tipo;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class AllTipoComando extends BaseComando {

    public JsonArrayBuilder tipoArrayJson= Json.createArrayBuilder();

    @Override
    public void execute() {

        DaoTipo dao= Fabrica.crear(DaoTipo.class);
        List<Tipo> resultado= dao.findAll(Tipo.class);

        for(Tipo obj: resultado) {

            JsonObject tipo = Json.createObjectBuilder().add("id",obj.get_id())
                                                        .add("nombre",obj.get_nombre())
                                                        .add("estado",obj.get_estado()).build();

            tipoArrayJson.add(tipo);
        }
    }

    @Override
    public JsonObject getResult() {
        ResponseDto responseDto =Fabrica.crear(ResponseDto.class);
        responseDto.mensaje="Todas las presentaciones";
        responseDto.estado="success";
        responseDto.objeto=this.tipoArrayJson;


        JsonObject data= Json.createObjectBuilder().add("mensaje","Todos los tipos")
                .add("estado","success")
                .add("tipos",tipoArrayJson).build();

        return data;
    }
}
