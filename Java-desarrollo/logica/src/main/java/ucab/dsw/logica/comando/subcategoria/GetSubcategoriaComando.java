package ucab.dsw.logica.comando.subcategoria;

import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.dtos.SubcategoriaDto;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.logica.comando.BaseComando;

import javax.json.Json;
import javax.json.JsonObject;

public class GetSubcategoriaComando extends BaseComando {

    public long _id;
    public SubcategoriaDto subcategoriaDto;
    public JsonObject subcategoriaJson;

    public GetSubcategoriaComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() {
        DaoSubcategoria dao = new DaoSubcategoria();
        Subcategoria subcategoria = dao.find(_id,Subcategoria.class);

        subcategoriaJson= Json.createObjectBuilder()
                                .add("nombre",subcategoria.get_nombre())
                                .add("categoria_id",subcategoria.get_categoria().get_id())
                                .add("categoria",subcategoria.get_categoria().get_nombre())
                                .add("estado",subcategoria.get_estado()).build();


    }

    @Override
    public JsonObject getResult() {
       JsonObject data= Json.createObjectBuilder()
                            .add("estado","success")
                            .add("mensaje","Subcategoria consultada")
                            .add("subcategoria",subcategoriaJson).build();
        return data;
    }
}
