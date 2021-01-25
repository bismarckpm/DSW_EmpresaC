package ucab.dsw.logica.comando.marca;

import ucab.dsw.accesodatos.DaoMarca;
import ucab.dsw.accesodatos.DaoSubcategoria;
import ucab.dsw.dtos.MarcaDto;
import ucab.dsw.entidades.Marca;
import ucab.dsw.entidades.Subcategoria;
import ucab.dsw.logica.comando.BaseComando;
import ucab.dsw.logica.fabrica.Fabrica;

import javax.json.Json;
import javax.json.JsonObject;

public class GetMarcaComando extends BaseComando {

    public long _id;
    public MarcaDto marcaDto;
    public JsonObject marcaJson;

    public GetMarcaComando(long _id) {
        this._id = _id;
    }

    @Override
    public void execute() {
        DaoMarca dao = Fabrica.crear(DaoMarca.class);
        DaoSubcategoria daoSubcategoria=Fabrica.crear(DaoSubcategoria.class);
        Marca marca = dao.find(_id,Marca.class);
        Subcategoria subcategoria=daoSubcategoria.find(marca.get_subcategoria().get_id(),Subcategoria.class);

        marcaJson= Json.createObjectBuilder()
                        .add("id",marca.get_id())
                        .add("nombre",marca.get_nombre())
                        .add("subcategoria",subcategoria.get_nombre())
                        .add("subcategoria_id",subcategoria.get_id())
                        .add("estado",marca.get_estado()).build();

    }

    @Override
    public JsonObject getResult() {
        JsonObject data= Json.createObjectBuilder()
                    .add("estado","success")
                    .add("mensaje","Marca consultada")
                    .add("marca",marcaJson).build();

        return data;
    }
}
